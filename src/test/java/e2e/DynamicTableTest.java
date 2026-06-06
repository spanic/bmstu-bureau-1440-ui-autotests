package e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.DynamicTablePage;

import e2e.fixtures.BasePlaywrightTestFixture;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("UI Testing Playground")
@Feature("Dynamic Table")
@DisplayName("Dynamic Table")
public class DynamicTableTest extends BasePlaywrightTestFixture {

    private DynamicTablePage dynamicTablePage;

    @BeforeEach
    void openPage() {
        dynamicTablePage = new DynamicTablePage(page);
    }

    @Test
    @Story("Cell value lookup")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Chrome CPU cell matches the highlighted validation value")
    public void validateDynamicTable() {
        String chromeCellText = dynamicTablePage.getCellText("CPU", "Chrome");
        String validationValue = dynamicTablePage.getValidationValue();

        assertEquals(validationValue, chromeCellText);
        assertNotEquals(chromeCellText, "Some random value just to validate the test case");
    }

}
