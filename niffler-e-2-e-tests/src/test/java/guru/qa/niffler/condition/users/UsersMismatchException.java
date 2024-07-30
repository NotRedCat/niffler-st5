package guru.qa.niffler.condition.users;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;

import javax.annotation.Nullable;

import static java.lang.System.lineSeparator;

public class UsersMismatchException extends UIAssertionError {

    public UsersMismatchException(String message, CollectionSource collection,
                                  String expected, String actualElementText,
                                  @Nullable String explanation, long timeoutMs,
                                  @Nullable Throwable cause) {
        super(
                collection.driver(),
                message +
                        lineSeparator() + "Actual: " + lineSeparator() + actualElementText +
                        lineSeparator() + "Expected: " + lineSeparator() + expected +
                        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
                        lineSeparator() + "Collection: " + collection.description(),
                expected, actualElementText,
                cause,
                timeoutMs);
    }

}
