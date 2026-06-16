package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SampleAppPage {

    private final Page page;
    private final Locator userNameInput;
    private final Locator passwordInput;
    private final Locator statusMessage;
    public final Locator loginButton;
    public final Locator logoutButton;

    public SampleAppPage(Page page) {
        this.page = page;
        this.userNameInput = page.locator("[name=\"UserName\"]");
        this.passwordInput = page.locator("[name=\"Password\"]");
        this.statusMessage = page.locator("#loginstatus");
        this.loginButton = page.locator("#login").getByText("Log In");
        this.logoutButton = page.locator("#login").getByText("Log Out");
    }

    public void login(String username, String password) {
        userNameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public String getStatusMessage() {
        return statusMessage.textContent();
    }
}
