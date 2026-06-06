package e2e.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

public abstract class BrowserConfig {

    public abstract Browser initBrowser(Playwright playwright);

    public void applyContext(BrowserContext context) {
    }

}
