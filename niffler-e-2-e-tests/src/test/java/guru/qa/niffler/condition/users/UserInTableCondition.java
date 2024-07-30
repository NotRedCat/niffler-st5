package guru.qa.niffler.condition.users;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class UserInTableCondition extends WebElementsCondition {

    private final UserJson[] expectedUsers;


    public UserInTableCondition(UserJson[] expectedUsers) {
        this.expectedUsers = expectedUsers;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {

        if (elements.size() != expectedUsers.length) {
            return CheckResult.rejected(
                    "Users table size mismatch",
                    elements.size()
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            UserJson expectedUserForRow = expectedUsers[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean userNameResult = td.get(1).getText().contains(
                    expectedUserForRow.username()
            );

            if (!userNameResult) {
                return CheckResult.rejected(
                        "Users table: username mismatch",
                        td.get(1).getText()
                );
            }
            boolean fullNameResult = false;
            String fullName = td.get(2).getText();
            if (fullName.isBlank()) {
                if (expectedUserForRow.firstname() == null && expectedUserForRow.surname() == null) {
                    fullNameResult = true;
                }
            } else {
                fullNameResult = fullName.contains(
                        expectedUserForRow.firstname() + " " + expectedUserForRow.surname()+"ff"
                );
            }
            if (!fullNameResult) {
                return CheckResult.rejected(
                        "Users table: fullname mismatch",
                        td.get(2).getText()
                );
            }
        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualText = lastCheckResult.getActualValue();

        List<WebElement> elements = collection.getElements();

        StringBuffer actualUserRows = new StringBuffer();

        for (int j = 0; j < collection.getElements().size(); j++) {
            WebElement row = elements.get(j);
            List<WebElement> td = row.findElements(By.cssSelector("td"));

            for (int i = 1; i < td.size() - 1; i++)
                actualUserRows.append(td.get(i).getText() + " | ");
            actualUserRows.append("\n");
        }

        StringBuffer expectedUserRows = new StringBuffer();
        for (int i = 0; i < expectedUsers.length; i++) {
            UserJson expectedUser = expectedUsers[i];

            expectedUserRows.append(String.format("%s | %s | \n",
                    expectedUser.username(),
                    (expectedUser.firstname() + " " + expectedUser.surname())));
        }

        String message = lastCheckResult.getMessageOrElse(() -> "Users table content mismatch");
        throw new UsersMismatchException(message, collection, expectedUserRows.toString(), actualUserRows.toString(), explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }

}
