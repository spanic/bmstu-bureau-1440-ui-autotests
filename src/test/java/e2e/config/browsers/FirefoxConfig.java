package e2e.config.browsers;

import java.util.Map;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

public class FirefoxConfig extends BrowserConfig {

    public Map<String, Object> userPrefs;

    @Override
    public Browser initBrowser(Playwright playwright) {
        return playwright.firefox().launch(new LaunchOptions().setFirefoxUserPrefs(userPrefs));
    }

}
