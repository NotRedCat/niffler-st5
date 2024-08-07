package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.pages.ProfilePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class ProfileTest extends BaseWebTest {

    @ApiLogin(
            username = "Nastiletko",
            password = "bB!123456"
    )
    @Test
    void updateProfileTest() {
//        open(ProfilePage.URL, ProfilePage.class)
//                .setName(Faker.instance().funnyName().name(), Faker.instance().funnyName().name())
//                .checkToasterText("Profile successfully updated")
//                .checkFields();
//    }
        open(ProfilePage.URL, ProfilePage.class)
                .setName("","")
                .checkToasterText("Profile successfully updated");
    }
}
