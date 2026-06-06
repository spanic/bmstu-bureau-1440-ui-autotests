package e2e.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public class WebkitConfig extends BrowserConfig {

    @Override
    public Browser initBrowser(Playwright playwright) {
        return playwright.webkit().launch();
    }

}
