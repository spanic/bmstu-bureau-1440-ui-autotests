package e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bmstu_bureau_1440.pages.DynamicTablePage;

import e2e.fixtures.BasePlaywrightTestFixture;

public class DynamicTableTest extends BasePlaywrightTestFixture {

    private DynamicTablePage dynamicTablePage;

    @BeforeEach
    void openPage() {
        dynamicTablePage = new DynamicTablePage(page);
    }

    @Test
    @DisplayName("Validating dynamic table")
    public void validateDynamicTable() {
        String chromeCellText = dynamicTablePage.getCellText("CPU", "Chrome");
        String validationValue = dynamicTablePage.getValidationValue();

        assertEquals(validationValue, chromeCellText);
        assertNotEquals(chromeCellText, "Some random value just to validate the test case");
    }

}
