package e2e.config.browsers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

public abstract class BrowserConfig {

    public abstract Browser initBrowser(Playwright playwright);

    public void updateContext(BrowserContext context) {
    }

}
