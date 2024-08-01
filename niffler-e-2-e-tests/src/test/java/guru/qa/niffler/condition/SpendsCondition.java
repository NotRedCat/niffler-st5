package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.SpendJson;

public class SpendsCondition {
    public static WebElementsCondition spendsInTable(SpendJson... extraSpends) {
        return new SpendInTableCondition(extraSpends);
    };
}