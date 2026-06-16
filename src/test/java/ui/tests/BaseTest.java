package ui.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import ui.pages.HomePage;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected HomePage homePage;

    @BeforeClass
    public void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void openHomePage() {
        context = browser.newContext();
        page = context.newPage();
        homePage = new HomePage(page);
        homePage.navigate();
        page.waitForLoadState();
    }

    @AfterMethod
    public void closePage() {
        context.close();
    }

    @AfterClass
    public void tearDownBrowser() {
        browser.close();
        playwright.close();
    }

}
