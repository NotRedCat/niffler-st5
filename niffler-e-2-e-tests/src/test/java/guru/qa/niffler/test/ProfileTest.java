package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.pages.ProfilePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class ProfileTest extends BaseWebTest {

    @ApiLogin(
            user = @TestUser
    )
    @Test
    void updateProfileTestWithRandomUser() {

        open(ProfilePage.URL, ProfilePage.class)
                .setName("Whiskas")
                .acceptChanges()
                .checkToasterText("Profile successfully updated");
    }
    @ApiLogin(
            username = "kniffler",
            password = "12345",
            user = @TestUser
    )
    @Test
    void updateProfileTestWithUser() {

        open(ProfilePage.URL, ProfilePage.class)
                .setName("WowNiffler")
                .acceptChanges()
                .checkToasterText("Profile successfully updated");
    }
}
