package e2e.fixtures;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import e2e.config.PlaywrightConfigLoader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasePlaywrightTestFixture {

    Playwright playwright;
    Browser browser;

    @BeforeAll
    protected void setup() {
        var playwrightConfig = PlaywrightConfigLoader.load();
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new LaunchOptions()
                        .setArgs(playwrightConfig.browser.args)
                        .setChannel("chromium"));
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
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

}
