package e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.ShadowDomPage;

import e2e.fixtures.BasePlaywrightTestFixture;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("UI Testing Playground")
@Feature("Shadow DOM")
@DisplayName("Shadow DOM")
public class ShadowDomTest extends BasePlaywrightTestFixture {

    private ShadowDomPage shadowDomPage;

    @BeforeEach
    void openPage() {
        shadowDomPage = new ShadowDomPage(context, page);
    }

    @Test
    @Story("GUID generator")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Generated GUID matches the value copied to the clipboard")
    public void validateShadowDom() {
        assertThat(shadowDomPage.getGuidGeneratorComponent()).isVisible();

        shadowDomPage.getGenerateButton().click();
        shadowDomPage.getCopyButton().click();

        var clipboardText = page.evaluate("async () => await navigator.clipboard.readText()").toString();
        var generatedGuid = shadowDomPage.getGeneratedGuid();

        assertEquals(clipboardText, generatedGuid);
    }
}
