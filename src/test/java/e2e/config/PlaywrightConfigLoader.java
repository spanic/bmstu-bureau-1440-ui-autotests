package e2e.config;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class PlaywrightConfigLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    public static PlaywrightConfig load() {
        try (InputStream input = PlaywrightConfigLoader.class.getClassLoader().getResourceAsStream("playwright.yml")) {
            return MAPPER.readValue(input, PlaywrightConfig.class);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load playwright.yml", e);
        }
    }
}
