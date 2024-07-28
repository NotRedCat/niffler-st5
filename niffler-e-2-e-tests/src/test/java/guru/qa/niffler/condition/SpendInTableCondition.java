package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.ex.TextsMismatch;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import org.apache.hc.client5.http.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SpendInTableCondition extends WebElementsCondition {


    private final SpendJson[] expectedSpends;


    public SpendInTableCondition(SpendJson[] expectedSpends) {
        this.expectedSpends = expectedSpends;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {

        if (elements.size() != expectedSpends.length) {
            return CheckResult.rejected(
                    "Spending table size mismatch ",
                    elements.size()
            );
        }

        boolean result = false;

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            SpendJson expected = expectedSpends[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            result = td.get(1).getText().contains(
                    DateUtils.formatDate(
                            expected.spendDate(),
                            "dd MMMM yy"));

            result = td.get(2).getText().contains(
                    String.valueOf(expected.amount()));

        }
        return result
                ? CheckResult.accepted()
                : CheckResult.rejected("Spending table content mismatch",
                "");
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualText = lastCheckResult.getActualValue();

        String message = lastCheckResult.getMessageOrElse(() -> "Spending table content mismatch");
        throw new SpendMismatchException(message, collection, Arrays.toString(expectedSpends), actualText, explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }

}
