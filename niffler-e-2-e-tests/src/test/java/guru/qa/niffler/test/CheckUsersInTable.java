package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static guru.qa.niffler.condition.users.UsersCondition.usersInTable;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class CheckUsersInTable {

        static {
            Configuration.browserSize = "1920x1080";
        }

        StartPage startPage = new StartPage();
        AuthPage authPage = new AuthPage();
        MainPage mainPage = new MainPage();
        FriendsPage friendsPage = new FriendsPage();

    @Test
    void friendTaggedInPeopleTable(@User(User.UserType.WITH_FRIEND) UserJson testUser,
                                   @User(User.UserType.WITH_FRIEND) UserJson testFriend1,
                                   @User(User.UserType.WITH_FRIEND) UserJson testFriend2) {

        startPage.openPage()
                .clickLoginButton();
        authPage.login(testUser.username(), testUser.testData().password());
        mainPage.clickFriendsButton();

        UserJson[] users = new UserJson[]{testFriend1, testFriend2};
        friendsPage.getFriendsRow().shouldHave(usersInTable(users));
    }
}
