package e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.bmstu_bureau_1440.pages.SampleAppPage;
import com.bmstu_bureau_1440.pages.SampleAppPage.LoginState;
import com.microsoft.playwright.Locator;

import e2e.fixtures.BasePlaywrightTestFixture;

public class SampleAppTest extends BasePlaywrightTestFixture {

    private SampleAppPage sampleAppPage;

    @BeforeEach
    void openApp() {
        sampleAppPage = new SampleAppPage(page);
    }

    @Test
    @DisplayName("Validating initial state")
    public void validatingInitialState() {
        validateLoginStatusState(LoginState.LOGGED_OUT, null);
        validateBaseInputState(sampleAppPage.getUsernameInput(), sampleAppPage.getPasswordInput());
        validateButtonState(sampleAppPage.getLoginButton(), LoginState.LOGGED_OUT);
    }

    @ParameterizedTest(name = "Validating login for username: {0} and password: {1} and expected result: {2}")
    @MethodSource("loginDataAndResultProvider")
    public void validatingLogin(String username, String password, boolean expectedResult) {
        performLogin(username, password, expectedResult);
    }

    @Test
    @DisplayName("Validating logout")
    public void validatingLogout() {
        performLogin("John Doe", "pwd", true);

        sampleAppPage.getLoginButton().click();

        validateLoginStatusState(LoginState.LOGGED_OUT, null);
        validateButtonState(sampleAppPage.getLoginButton(), LoginState.LOGGED_OUT);
    }

    private void performLogin(String username, String password, boolean expectedResult) {
        sampleAppPage.getUsernameInput().fill(username);
        sampleAppPage.getPasswordInput().fill(password);

        sampleAppPage.getLoginButton().click();

        if (expectedResult) {
            validateSuccessfulLogin(username, password);
        } else {
            validateFailedLogin();
        }
    }

    private void validateSuccessfulLogin(String username, String password) {
        assertAll(
                () -> validateLoginStatusState(LoginState.LOGGED_IN, username),
                () -> validateBaseInputState(sampleAppPage.getUsernameInput(), sampleAppPage.getPasswordInput()),
                () -> assertThat(sampleAppPage.getUsernameInput()).hasValue(username),
                () -> assertThat(sampleAppPage.getPasswordInput()).hasValue(password),
                () -> validateButtonState(sampleAppPage.getLoginButton(), LoginState.LOGGED_IN));
    }

    private void validateFailedLogin() {
        assertAll(
                () -> validateLoginStatusState(LoginState.INVALID_USERNAME_PASSWORD, null),
                () -> validateBaseInputState(sampleAppPage.getUsernameInput(), sampleAppPage.getPasswordInput()),
                () -> assertThat(sampleAppPage.getUsernameInput()).isEmpty(),
                () -> assertThat(sampleAppPage.getPasswordInput()).isEmpty(),
                () -> validateButtonState(sampleAppPage.getLoginButton(), LoginState.INVALID_USERNAME_PASSWORD));
    }

    private void validateBaseInputState(Locator... locators) {
        assertAll(
                () -> Stream.of(locators).forEach(locator -> assertThat(locator).isVisible()),
                () -> Stream.of(locators).forEach(locator -> assertThat(locator).isEditable()));
    }

    private void validateButtonState(Locator locator, LoginState loginState) {
        assertAll(
                () -> assertThat(locator).isVisible(),
                () -> assertThat(locator).isEnabled(),
                () -> assertThat(locator).hasText(
                        loginState == LoginState.LOGGED_IN ? SampleAppPage.LOG_OUT_BUTTON_TEXT
                                : SampleAppPage.LOG_IN_BUTTON_TEXT));
    }

    private void validateLoginStatusState(LoginState loginState, String username) {
        final String expectedStatus;

        switch (loginState) {
            case LOGGED_IN:
                expectedStatus = String.format(SampleAppPage.LOGGED_IN_STATUS, username);
                break;
            case INVALID_USERNAME_PASSWORD:
                expectedStatus = SampleAppPage.INVALID_USERNAME_PASSWORD_STATUS;
                break;
            default:
                expectedStatus = SampleAppPage.LOGGED_OUT_STATUS;
                break;
        }

        assertThat(sampleAppPage.getLoginStatus()).containsText(expectedStatus);
    }

    private static Stream<Arguments> loginDataAndResultProvider() {
        return Stream.of(
                Arguments.of("John Doe", "pwd", true),
                Arguments.of("Arnold Schwarzenegger", "pwd", true),
                Arguments.of("Bruce Willis", "wrong", false),
                Arguments.of("", "", false));
    }
}