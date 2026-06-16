package ui.tests;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import ui.pages.LoadDelayPage;

public class LoadDelayTest extends BaseTest {

    private static final int REASONABLE_LOAD_TIME_MS = 3_000;

    @Test
    public void testPageLoadsInReasonableTime() {
        long startTime = System.currentTimeMillis();

        homePage.loadDelay.click();
        page.waitForLoadState();

        LoadDelayPage loadDelayPage = new LoadDelayPage(page);
        assertThat(loadDelayPage.delayedButton).isVisible();

        long elapsed = System.currentTimeMillis() - startTime;
        assertTrue(elapsed < REASONABLE_LOAD_TIME_MS, "Page should load within " + REASONABLE_LOAD_TIME_MS + "ms, took " + elapsed + "ms");
    }
}
