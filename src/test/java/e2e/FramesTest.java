package e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.FramesPage;
import com.bmstu_bureau_1440.pages.FramesPage.FrameLocatorFn;
import com.microsoft.playwright.FrameLocator;

import e2e.fixtures.BasePlaywrightTestFixture;

public class FramesTest extends BasePlaywrightTestFixture {

    private FramesPage framesPage;

    @BeforeEach
    void openPage() {
        framesPage = new FramesPage(page);
    }

    @Test
    @DisplayName("Validating outer frame")
    public void validateOuterFrame() {
        validateFrame(framesPage.getOuterFrame());
    }

    @Test
    @DisplayName("Validating inner frame")
    public void validateInnerFrame() {
        validateFrame(framesPage.getInnerFrame());
    }

    private void validateFrame(FrameLocator frameLocator) {
        clickButtonAndValidateResult(frameLocator, framesPage.getEditButton());
        clickButtonAndValidateResult(frameLocator, framesPage.getSubmitButton());
        clickButtonAndValidateResult(frameLocator, framesPage.getClickMeButton());
        clickButtonAndValidateResult(frameLocator, framesPage.getPrimaryButton());
    }

    private void clickButtonAndValidateResult(FrameLocator frame, FrameLocatorFn buttonLocatorFn) {
        var button = buttonLocatorFn.locate(frame);
        button.click();
        assertThat(framesPage.getResultText().locate(frame)).containsText(button.textContent());
    }

}
