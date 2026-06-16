package api;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.restassured.RestAssured;

public class RosterScrapingTest {

    private static final String TEAMS_URL = "https://qa-assignment.dev1.whalebone.io/api/teams";

    private Playwright playwright;
    private Browser browser;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    private String getOldestTeamRosterUrl() {
        List<Map<String, Object>> teams = RestAssured.get(TEAMS_URL).jsonPath().getList("teams");

        Map<String, Object> oldestTeam = teams.stream()
                .min((a, b) -> Integer.compare((int) a.get("founded"), (int) b.get("founded")))
                .orElseThrow(() -> new AssertionError("No teams found"));

        String siteUrl = (String) oldestTeam.get("officialSiteUrl");
        if (!siteUrl.endsWith("/")) {
            siteUrl += "/";
        }
        return siteUrl + "roster";
    }

    @Test
    public void testMoreCanadiansThanAmericans() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        try {
            String rosterUrl = getOldestTeamRosterUrl();
            page.navigate(rosterUrl);
            page.waitForLoadState();

            // Dismiss cookie consent banner if present
            Locator acceptButton = page.locator("button:has-text('I Accept')");
            if (acceptButton.count() > 0) {
                acceptButton.first().click();
            }

            page.waitForSelector("table tbody tr");

            List<String> birthplaces = page.locator("table tbody tr td:last-child").allTextContents();

            long canadianCount = birthplaces.stream()
                    .filter(b -> b.trim().endsWith("CAN"))
                    .count();

            long americanCount = birthplaces.stream()
                    .filter(b -> b.trim().endsWith("USA"))
                    .count();

            assertTrue(canadianCount > americanCount,
                    "Expected more Canadian players than American players on the Montreal Canadiens roster. "
                    + "CAN: " + canadianCount + ", USA: " + americanCount);
        } finally {
            context.close();
        }
    }
}
