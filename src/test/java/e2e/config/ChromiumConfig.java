package e2e.config;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

public class ChromiumConfig extends BrowserConfig {

    public String channel;
    public List<String> args;
    public List<String> permissions;

    @Override
    public Browser initBrowser(Playwright playwright) {
        return playwright.chromium().launch(new LaunchOptions().setChannel(channel).setArgs(args));
    }

    @Override
    public void applyContext(BrowserContext context) {
        if (permissions != null) {
            context.grantPermissions(permissions);
        }
    }

}
