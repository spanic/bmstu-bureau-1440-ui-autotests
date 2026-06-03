package com.bmstu_bureau_1440.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;

public class SampleAppPage {

    public static final String LOGGED_OUT_STATUS = "User logged out";
    public static final String LOGGED_IN_STATUS = "Welcome, %s!";
    public static final String INVALID_USERNAME_PASSWORD_STATUS = "Invalid username/password";
    public static final String LOG_IN_BUTTON_TEXT = "Log In";
    public static final String LOG_OUT_BUTTON_TEXT = "Log Out";

    public enum LoginState {
        LOGGED_OUT,
        LOGGED_IN,
        INVALID_USERNAME_PASSWORD;
    }

    @Getter
    private final Page page;

    public SampleAppPage(Page page) {
        page.navigate("http://www.uitestingplayground.com/sampleapp");
        this.page = page;
    }

    public Locator getLoginStatus() {
        return page.locator("//*[@id='loginstatus']");
    }

    public Locator getUsernameInput() {
        return page.getByPlaceholder("User name");
    }

    public Locator getPasswordInput() {
        return page.locator("//input[@name='Password']");
    }

    public Locator getLoginButton() {
        return page.locator("//button[@id='login']");
    }

}
