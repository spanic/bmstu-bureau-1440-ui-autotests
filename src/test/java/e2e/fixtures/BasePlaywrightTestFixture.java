package e2e.fixtures;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import e2e.config.PlaywrightConfig;
import e2e.config.browsers.BrowserConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasePlaywrightTestFixture {

    private PlaywrightConfig playwrightConfig;
    private BrowserConfig browserConfig;

    Playwright playwright;
    Browser browser;

    @BeforeAll
    protected void setup() {
        playwrightConfig = PlaywrightConfig.Loader.load();
        browserConfig = playwrightConfig.getActiveBrowserConfig();
        playwright = Playwright.create();
        browser = browserConfig.initBrowser(playwright);
    }

    @AfterAll
    void teardown() {
        browser.close();
        playwright.close();
    }

    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        browserConfig.updateContext(context);
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

}
