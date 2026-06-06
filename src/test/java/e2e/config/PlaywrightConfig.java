package e2e.config;

public class PlaywrightConfig {

    public String browser;

    public ChromiumConfig chromium;
    public FirefoxConfig firefox;
    public WebkitConfig webkit;

    public BrowserConfig getActiveBrowserConfig() {
        return switch (browser.toLowerCase()) {
            case "chromium", "chrome" -> chromium;
            case "firefox" -> firefox;
            case "webkit" -> webkit;
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

}
