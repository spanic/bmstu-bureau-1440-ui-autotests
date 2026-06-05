package e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.ScrollToClickPage;
import com.microsoft.playwright.Locator;

import e2e.fixtures.BasePlaywrightTestFixture;

public class ScrollToClickTest extends BasePlaywrightTestFixture {

    private ScrollToClickPage scrollToClickPage;

    @BeforeEach
    void openPage() {
        scrollToClickPage = new ScrollToClickPage(page);
    }

    @Test
    @DisplayName("Validating first button")
    public void validateScrollToClick() {
        List<Supplier<Locator>> buttonFinders = List.of(
                scrollToClickPage::findFirstButton,
                scrollToClickPage::findSecondButton,
                scrollToClickPage::findThirdButton,
                scrollToClickPage::findFourthButton);

        int clickedCount = 0;

        var iterator = buttonFinders.iterator();

        while (iterator.hasNext()) {
            var button = iterator.next().get();

            assertThat(button).isInViewport();

            button.click();
            clickedCount++;

            if (iterator.hasNext()) {
                String expectedText = String.format(ScrollToClickPage.PROGRESS_TEXT_PATTERN, clickedCount);
                assertEquals(scrollToClickPage.getProgressText(), expectedText);
            }
        }

        assertEquals(scrollToClickPage.getProgressText(), ScrollToClickPage.ALL_BUTTONS_CLICKED_TEXT);
    }

}
