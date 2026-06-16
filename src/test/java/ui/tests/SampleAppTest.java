package ui.tests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import ui.enums.SampleAppStatus;
import ui.pages.SampleAppPage;

public class SampleAppTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        homePage.sampleApp.click();

        SampleAppPage sampleAppPage = new SampleAppPage(page);
        sampleAppPage.login("testuser", "pwd");

        assertEquals(sampleAppPage.getStatusMessage(), SampleAppStatus.WELCOME.getMessage("testuser"));
        assertThat(sampleAppPage.logoutButton).isVisible();
    }

    @Test
    public void testLoginWithInvalidPassword() {
        homePage.sampleApp.click();

        SampleAppPage sampleAppPage = new SampleAppPage(page);
        sampleAppPage.login("testuser", "wrongpassword");

        assertEquals(sampleAppPage.getStatusMessage(), SampleAppStatus.INVALID_CREDENTIALS.getMessage());
    }

    @Test
    public void testLoginWithEmptyUsername() {
        homePage.sampleApp.click();

        SampleAppPage sampleAppPage = new SampleAppPage(page);
        sampleAppPage.login("", "pwd");

        assertEquals(sampleAppPage.getStatusMessage(), SampleAppStatus.INVALID_CREDENTIALS.getMessage());
    }

    @Test
    public void testLogout() {
        homePage.sampleApp.click();

        SampleAppPage sampleAppPage = new SampleAppPage(page);
        sampleAppPage.login("testuser", "pwd");
        assertEquals(sampleAppPage.getStatusMessage(), SampleAppStatus.WELCOME.getMessage("testuser"));

        sampleAppPage.logout();

        assertEquals(sampleAppPage.getStatusMessage(), SampleAppStatus.LOGGED_OUT.getMessage());
        assertThat(sampleAppPage.loginButton).isVisible();
    }
}
