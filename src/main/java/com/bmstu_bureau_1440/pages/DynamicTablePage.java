package com.bmstu_bureau_1440.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DynamicTablePage {

    private final Page page;

    public DynamicTablePage(Page page) {
        this.page = page;
        page.navigate("http://www.uitestingplayground.com/dynamictable");
    }

    public Locator getTable() {
        return page.getByRole(AriaRole.TABLE, new Page.GetByRoleOptions().setName("Tasks"));
    }

    public int getColumnIdxByHeaderText(String headerText) {
        Locator headers = getTable().getByRole(AriaRole.COLUMNHEADER);

        for (int i = 0; i < headers.count(); i++) {
            var header = headers.nth(i);
            if (headerText.equals(header.innerText().trim())) {
                return i;
            }
        }

        throw new IllegalArgumentException("Column header not found: " + headerText);
    }

    public int getRowIdxByCellText(String cellText) {
        Locator rows = getTable().getByRole(AriaRole.ROW);

        for (int i = 0; i < rows.count(); i++) {
            var row = rows.nth(i);
            if (row.getByRole(AriaRole.CELL, new Locator.GetByRoleOptions().setName(cellText)).isVisible()) {
                return i;
            }
        }

        throw new IllegalArgumentException("Row not found: " + cellText);
    }

    public String getCellText(String columnName, String rowName) {
        int colIdx = getColumnIdxByHeaderText(columnName);
        int rowIdx = getRowIdxByCellText(rowName);

        return getTable()
                .getByRole(AriaRole.ROW).nth(rowIdx)
                .getByRole(AriaRole.CELL).nth(colIdx)
                .innerText();
    }

    public String getValidationValue() {
        var validationText = page.locator("//*[@class='bg-warning']").innerText();
        return validationText.split(":")[1].trim();
    }

}
