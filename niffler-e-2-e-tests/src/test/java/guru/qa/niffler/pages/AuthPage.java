package guru.qa.niffler.pages;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage {

    private final SelenideElement formHeader = $(".form__header");
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement signInButton = $(".form__submit");
    private final SelenideElement viewPasswordButton = $(".form__password-button");

    public AuthPage setUsername(String username) {

        usernameInput.setValue(username);
        return this;
    }

    public AuthPage setPassword(String password) {

        passwordInput.setValue(password);
        return this;
    }

    public void clickSignIn() {

        signInButton.click();
    }

    public void login(String username, String password) {

        setUsername(username).setPassword(password).clickSignIn();
    }
}
