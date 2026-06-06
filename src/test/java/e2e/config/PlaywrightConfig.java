package e2e.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import e2e.config.browsers.BrowserConfig;
import e2e.config.browsers.ChromiumConfig;
import e2e.config.browsers.FirefoxConfig;
import e2e.config.browsers.WebkitConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PlaywrightConfig {

    public String browser;

    public ChromiumConfig chromiumConfig;
    public FirefoxConfig firefoxConfig;
    public WebkitConfig webkitConfig;

    public BrowserConfig getActiveBrowserConfig() {
        return switch (BrowserType.fromAlias(browser)) {
            case BrowserType.CHROMIUM -> chromiumConfig;
            case BrowserType.FIREFOX -> firefoxConfig;
            case BrowserType.WEBKIT -> webkitConfig;
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private enum BrowserType {
        CHROMIUM("chromium", "chrome"),
        FIREFOX("firefox"),
        WEBKIT("webkit");

        @Getter
        private final String[] aliases;

        BrowserType(String... aliases) {
            this.aliases = aliases;
        }

        static BrowserType fromAlias(String alias) {
            return Arrays.stream(values())
                    .filter(type -> Arrays.asList(type.aliases).contains(alias.toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported browser: " + alias));
        }
    }

    @NoArgsConstructor
    public final class Loader {

        private static final String PLAYWRIGHT_CONFIG_FILE = "playwright.yml";
        private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

        public static PlaywrightConfig load() {
            try (InputStream input = Loader.class.getClassLoader().getResourceAsStream(PLAYWRIGHT_CONFIG_FILE)) {
                return MAPPER.readValue(input, PlaywrightConfig.class);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load playwright.yml", e);
            }
        }
    }

}
