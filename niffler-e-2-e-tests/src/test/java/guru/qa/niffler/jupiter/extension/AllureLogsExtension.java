package guru.qa.niffler.jupiter.extension;


import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class AllureLogsExtension implements SuiteExtension {

    public static final String caseName = "logs";
    @SneakyThrows
    @Override
    public void afterSuite() {
        String caseId = UUID.randomUUID().toString();
        AllureLifecycle allureLifecycle = Allure.getLifecycle();
        allureLifecycle.scheduleTestCase(
                new TestResult()
                        .setUuid(caseId)
                        .setName(caseName)
                        .setStatus(Status.PASSED));
        allureLifecycle.startTestCase(caseId);


        allureLifecycle.addAttachment("niffler-auth.log", "text/html", ".logs",
                Files.newInputStream(Path.of("./auth.log")));
        allureLifecycle.addAttachment("niffler-currency.log", "text/html", ".logs",
                Files.newInputStream(Path.of("./currency.log")));
        allureLifecycle.addAttachment("niffler-gateway.log", "text/html", ".logs",
                Files.newInputStream(Path.of("./gateway.log")));
        allureLifecycle.addAttachment("niffler-spend.log", "text/html", ".logs",
                Files.newInputStream(Path.of("./spend.log")));
        allureLifecycle.addAttachment("niffler-userdata.log", "text/html", ".logs",
                Files.newInputStream(Path.of("./userdata.log")));

        allureLifecycle.stopTestCase(caseId);
        allureLifecycle.writeTestCase(caseId);
    }

    @Override
    public void beforeSuite(ExtensionContext extensionContext) {
        System.out.println("Before suite");
    }
}
