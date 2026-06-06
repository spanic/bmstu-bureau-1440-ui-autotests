package com.bmstu_bureau_1440.pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;

public class ShadowDomPage {

    @Getter
    private final Page page;

    public ShadowDomPage(BrowserContext context, Page page) {
        this.page = page;
        this.page.navigate("http://www.uitestingplayground.com/shadowdom");
    }

    public Locator getGuidGeneratorComponent() {
        return page.locator("guid-generator");
    }

    public Locator getGenerateButton() {
        return getGuidGeneratorComponent().locator("button#buttonGenerate");
    }

    public Locator getCopyButton() {
        return getGuidGeneratorComponent().locator("button#buttonCopy");
    }

    public String getGeneratedGuid() {
        return getGuidGeneratorComponent().locator("input#editField").inputValue();
    }
}
