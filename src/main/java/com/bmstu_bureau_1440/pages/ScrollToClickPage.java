package com.bmstu_bureau_1440.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;

public class ScrollToClickPage {

    public static final String PROGRESS_TEXT_PATTERN = "Buttons clicked: %d / 4";
    public static final String ALL_BUTTONS_CLICKED_TEXT = "All buttons clicked!";

    @Getter
    private final Page page;

    public ScrollToClickPage(Page page) {
        this.page = page;
        page.navigate("http://www.uitestingplayground.com/scrolltoclick");
    }

    public Locator findFirstButton() {
        var button = page.locator("//button[@id='scrollTarget1']");
        button.scrollIntoViewIfNeeded();

        return button;
    }

    public Locator findSecondButton() {
        var button = page.locator("//*[@id='scrollContainer2'] //button[@id='scrollTarget2']");
        button.scrollIntoViewIfNeeded();

        return button;
    }

    public Locator findThirdButton() {

        var outerScrollContainer = page.locator("//*[@id='outerScroll3']");
        outerScrollContainer.scrollIntoViewIfNeeded();

        var innerScrollContainer = outerScrollContainer.locator("//*[@id='innerScroll3']");
        innerScrollContainer.scrollIntoViewIfNeeded();

        var button = innerScrollContainer.locator("//button[@id='scrollTarget3']");
        button.scrollIntoViewIfNeeded();

        return button;
    }

    public Locator findFourthButton() {
        var hoverElementsContainer = page.locator("//*[@id='hoverList']");
        hoverElementsContainer.scrollIntoViewIfNeeded();

        Locator childElements = hoverElementsContainer.locator("//*[@class='hover-row']");

        for (int i = 0; i < childElements.count(); i++) {
            var childElement = childElements.nth(i);

            childElement.hover();

            var button = childElement.locator("//button[@id='scrollTarget4']");
            if (button.isVisible()) {
                return button;
            }
        }

        throw new IllegalStateException("Button not found in the hover elements container");
    }

    public String getProgressText() {
        return page.locator("//*[@id='progressText']").innerText();
    }

}
