package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;
    public final Locator sampleApp;
    public final Locator loadDelay;
    public final Locator progressBar;

    public HomePage(Page page) {
        this.page = page;
        this.sampleApp = page.getByText("Sample App");
        this.loadDelay = page.getByText("Load Delay");
        this.progressBar = page.getByText("Progress Bar");
    }

    public void navigate() {
        page.navigate("http://uitestingplayground.com/");
    }
}
