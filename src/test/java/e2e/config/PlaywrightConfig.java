package e2e.config;

import java.util.List;

public class PlaywrightConfig {

    public Browser browser = new Browser();

    public static class Browser {
        public List<String> args;
    }
}
