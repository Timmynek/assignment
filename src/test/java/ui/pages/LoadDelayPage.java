package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoadDelayPage {

    private final Page page;
    public final Locator delayedButton;

    public LoadDelayPage(Page page) {
        this.page = page;
        this.delayedButton = page.locator(".btn-primary").getByText("Button Appearing After Delay");
    }

    public void clickButton() {
        delayedButton.click();
    }
}
