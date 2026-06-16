package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProgressBarPage {

    private final Page page;
    private final Locator startButton;
    private final Locator stopButton;
    private final Locator progressBar;
    private final Locator result;

    public ProgressBarPage(Page page) {
        this.page = page;
        this.startButton = page.locator("#startButton");
        this.stopButton = page.locator("#stopButton");
        this.progressBar = page.locator("#progressBar");
        this.result = page.locator("#result");
    }

    public void clickStart() {
        startButton.click();
    }

    public void clickStop() {
        stopButton.click();
    }

    public int getProgressValue() {
        String value = progressBar.getAttribute("aria-valuenow");
        return Integer.parseInt(value);
    }

    public String getResultText() {
        return result.textContent();
    }
}
