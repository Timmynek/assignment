package ui.tests;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import ui.pages.ProgressBarPage;

public class ProgressBarTest extends BaseTest {

    @Test
    public void testStopProgressBarAt75Percent() {
        homePage.progressBar.click();

        ProgressBarPage progressBarPage = new ProgressBarPage(page);
        progressBarPage.clickStart();

        // Poll until progress bar reaches >= 75%
        page.waitForFunction("() => { "
                + "const bar = document.querySelector('#progressBar'); "
                + "return bar && parseInt(bar.getAttribute('aria-valuenow')) >= 75; "
                + "}");

        progressBarPage.clickStop();

        int finalValue = progressBarPage.getProgressValue();
        assertTrue(finalValue == 75,
                "Progress bar should be stopped at 75%, was " + finalValue + "%");

        String resultText = progressBarPage.getResultText();
        assertTrue(resultText.contains("Result: 0"), "Result should be 0, was: " + resultText);
    }
}
