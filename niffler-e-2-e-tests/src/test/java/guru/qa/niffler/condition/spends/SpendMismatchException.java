package guru.qa.niffler.condition.spends;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;

import javax.annotation.Nullable;

import static java.lang.System.lineSeparator;

public class SpendMismatchException extends UIAssertionError {

    public SpendMismatchException(String message, CollectionSource collection,
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
