package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.GenerateSpends;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(AbstractSpendExtension.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson category = extensionContext.getStore(AbstractCategoryExtension.NAMESPACE)
                .get(extensionContext.getUniqueId(), CategoryJson.class);

        List<GenerateSpend> potentialSpends = new ArrayList<>();
        AnnotationSupport.findAnnotation(
                        extensionContext.getRequiredTestMethod(),
                        GenerateSpends.class)
                .ifPresent(
                        spends ->
                                potentialSpends.addAll(Arrays.stream(spends.value()).toList())
                );

        AnnotationSupport.findAnnotation(
                        extensionContext.getRequiredTestMethod(),
                        GenerateSpend.class)
                .ifPresent(
                        potentialSpends::add
                );

        if (!potentialSpends.isEmpty()) {
            List<SpendJson> created = new ArrayList<>();
            for (GenerateSpend spend : potentialSpends) {
                SpendJson spendJson = new SpendJson(
                        null,
                        new Date(),
                        category.category(),
                        spend.currency(),
                        spend.amount(),
                        spend.description(),
                        category.username()
                );
                created.add(createSpend(spendJson));
            }
            extensionContext.getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), created);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        List<SpendJson> jsons = context.getStore(NAMESPACE).get(context.getUniqueId(), List.class);
        for (SpendJson json : jsons) {
            removeSpend(json);
        }

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return type.isAssignableFrom(SpendJson.class) || type.isAssignableFrom(SpendJson[].class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext
                .getParameter()
                .getType();

        List<SpendJson> createdSpends = extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class);

        return type.isAssignableFrom(SpendJson.class)
                ? createdSpends.getFirst()
                : createdSpends.toArray(SpendJson[]::new);
    }

    protected abstract SpendJson createSpend(SpendJson spend) throws Exception;


    protected abstract void removeSpend(SpendJson json);
}
