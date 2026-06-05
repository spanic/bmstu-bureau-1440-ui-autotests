package e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.ShadowDomPage;

import e2e.fixtures.BasePlaywrightTestFixture;

public class ShadowDomTest extends BasePlaywrightTestFixture {

    private ShadowDomPage shadowDomPage;

    @BeforeEach
    void openPage() {
        shadowDomPage = new ShadowDomPage(context, page);
    }

    @Test
    @DisplayName("Validating shadow dom")
    public void validateShadowDom() {
        assertThat(shadowDomPage.getGuidGeneratorComponent()).isVisible();

        shadowDomPage.getGenerateButton().click();
        shadowDomPage.getCopyButton().click();

        var clipboardText = page.evaluate("async () => await navigator.clipboard.readText()").toString();
        var generatedGuid = shadowDomPage.getGeneratedGuid();

        assertEquals(clipboardText, generatedGuid);
    }
}
