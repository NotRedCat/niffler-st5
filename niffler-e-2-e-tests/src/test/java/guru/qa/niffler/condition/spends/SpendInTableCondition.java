package guru.qa.niffler.condition.spends;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SpendInTableCondition extends WebElementsCondition {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
    private final SpendJson[] expectedSpends;


    public SpendInTableCondition(SpendJson[] expectedSpends) {
        this.expectedSpends = expectedSpends;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {

        if (elements.size() != expectedSpends.length) {
            return CheckResult.rejected(
                    "Spending table size mismatch",
                    elements.size()
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            SpendJson expectedSpendForRow = expectedSpends[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean dateResult = td.get(1).getText().contains(
                    DATE_FORMAT.format(
                            expectedSpendForRow.spendDate()
                    )
            );

            if (!dateResult) {
                return CheckResult.rejected(
                        "Spending table: date mismatch",
                        td.get(1).getText()
                );
            }

            boolean amountResult = Double.valueOf(td.get(2).getText()).equals(expectedSpendForRow.amount());

            if (!amountResult) {
                return CheckResult.rejected(
                        "Spending table: amount mismatch",
                        td.get(2).getText()
                );
            }

            boolean currencyResult = td.get(3).getText().contains(
                    expectedSpendForRow.currency().name()
            );

            if (!currencyResult) {
                return CheckResult.rejected(
                        "Spending table: currency mismatch",
                        td.get(3).getText()
                );
            }

            boolean categoryResult = td.get(4).getText().contains(
                    expectedSpendForRow.category()
            );

            if (!categoryResult) {
                return CheckResult.rejected(
                        "Spending table: category mismatch",
                        td.get(4).getText()
                );
            }

            boolean descriptionResult = td.get(5).getText().contains(
                    expectedSpendForRow.description()
            );

            if (!descriptionResult) {
                return CheckResult.rejected(
                        "Spending table: description mismatch",
                        td.get(5).getText()
                );
            }
        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualText = lastCheckResult.getActualValue();

        List<WebElement> elements = collection.getElements();

        StringBuffer actualSpendRows = new StringBuffer();

        for (int j = 0; j < collection.getElements().size(); j++) {
            WebElement row = elements.get(j);
            List<WebElement> td = row.findElements(By.cssSelector("td"));

            for (int i = 1; i < td.size() - 1; i++)
                actualSpendRows.append(td.get(i).getText() + " | ");
            actualSpendRows.append("\n");
        }

        StringBuffer expectedSpenRows = new StringBuffer();
        for (int i = 0; i < expectedSpends.length; i++) {
            SpendJson expectedSpend = expectedSpends[i];

            expectedSpenRows.append(String.format("%s | %s | %s | %s | %s | \n",
                    DATE_FORMAT.format(
                            expectedSpend.spendDate()
                    ),
                    expectedSpend.amount(),
                    expectedSpend.currency(),
                    expectedSpend.category(),
                    expectedSpend.description()));
        }

        String message = lastCheckResult.getMessageOrElse(() -> "Spending table content mismatch");
        throw new SpendMismatchException(message, collection, expectedSpenRows.toString(), actualSpendRows.toString(), explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }

}
