package e2e.fixtures;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;
import io.qameta.allure.util.PropertiesUtils;

/**
 * Attaches a Playwright screenshot to the Allure report after each test.
 *
 * <p>
 * Runs as an {@link AfterTestExecutionCallback} so it fires after the test body
 * but before the
 * {@code @AfterEach} that closes the
 * {@link com.microsoft.playwright.BrowserContext}, while the page
 * is still alive. By default only failing tests are captured; set the system
 * property
 * {@code allure.screenshot.always=true} (or env var
 * {@code ALLURE_SCREENSHOT_ALWAYS=true}) to attach
 * a screenshot for every test.
 */
public class AllureScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean isTestFailed = context.getExecutionException().isPresent();

        if (!isTestFailed && !alwaysCapture()) {
            return;
        }

        var testInstance = context.getTestInstance().orElse(null);

        if (!(testInstance instanceof BasePlaywrightTestFixture fixture)) {
            return;
        }

        Page page = fixture.page;

        if (page == null || page.isClosed()) {
            return;
        }

        String name = (isTestFailed ? "Failure screenshot - " : "Screenshot - ") + context.getDisplayName();
        attach(name, page.screenshot(new Page.ScreenshotOptions().setFullPage(true)));
    }

    private void attach(String name, byte[] screenshot) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
    }

    private boolean alwaysCapture() {
        return Boolean.parseBoolean(
                PropertiesUtils.loadAllureProperties()
                        .getProperty("allure.screenshot.always", "false"));
    }
}
