package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {

        String path = System.getProperty("user.dir") + "/testdata/LoginData.xlsx";
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet("Sheet1");

        int totalRows = sheet.getPhysicalNumberOfRows();
        int totalCols = sheet.getRow(0).getLastCellNum();

        Object[][] loginData = new Object[totalRows - 1][totalCols]; // Skip header
        int dataIndex = 0;

        for (int i = 1; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            boolean emptyRow = true;
            for (int j = 0; j < totalCols; j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = cell.toString().trim();
                loginData[dataIndex][j] = cellValue;
                if (!cellValue.isEmpty()) emptyRow = false;
            }

            if (emptyRow) {
                System.out.println("Skipping empty row: " + i);
                continue;
            } else {
                System.out.println("Reading row " + i + ": " 
                    + loginData[dataIndex][0] + " | " 
                    + loginData[dataIndex][1] + " | " 
                    + loginData[dataIndex][2]);
            }

            dataIndex++;
        }

        workbook.close();
        fis.close();

        return loginData;
    }
}






