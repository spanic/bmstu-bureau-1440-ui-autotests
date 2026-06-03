package com.bmstu_bureau_1440.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import lombok.Getter;

public class FramesPage {

    @FunctionalInterface
    public interface FrameLocatorFn {
        Locator locate(FrameLocator frame);
    }

    @Getter
    private final Page page;

    public FramesPage(Page page) {
        this.page = page;
        page.navigate("http://www.uitestingplayground.com/frames");
    }

    public FrameLocator getOuterFrame() {
        return page.frameLocator("//iframe[@id='frame-outer']");
    }

    public FrameLocator getInnerFrame() {
        return getOuterFrame().frameLocator("//iframe[@id='frame-inner']");
    }

    public FrameLocatorFn getEditButton() {
        return frame -> frame.locator("//button[@data-action='edit']");
    }

    public FrameLocatorFn getSubmitButton() {
        return frame -> frame.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("Submit"));
    }

    public FrameLocatorFn getClickMeButton() {
        return frame -> frame.locator(String.format("//button[@name='my-button']"));
    }

    public FrameLocatorFn getPrimaryButton() {
        return frame -> frame.locator(String.format("//button[@class='btn-class']"));
    }

    public FrameLocatorFn getResultText() {
        return frame -> frame.locator(String.format("//*[@id='result']"));
    }

}