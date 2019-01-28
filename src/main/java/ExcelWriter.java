import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    private static String[] columns = {"HMDB.No", "Common Name", "Formula", "Monoisotopic Weight"};
    static Sheet sheet;
    static Workbook workbook;
    static int rowNum = 1;
    static CellStyle styleLightBlue;
    static CellStyle styleLightGreen;
    static GUI g = GUI.getSharedApplication();

    public static void generateFile() {
        workbook = new XSSFWorkbook();

        // Create a Sheet
        sheet = workbook.createSheet("Metabolites");

        // Coloring
        styleLightBlue = workbook.createCellStyle();
        styleLightGreen = workbook.createCellStyle();

        styleLightBlue.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        styleLightBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleLightGreen.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        styleLightGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public static void write(Metabolite m){
        Row row = sheet.createRow(rowNum++);

        Cell cell0 = row.createCell(0);
        cell0.setCellStyle(styleLightBlue);
        cell0.setCellValue(m.getAccession());

        Cell cell1 = row.createCell(1);
        cell1.setCellStyle(styleLightGreen);
        cell1.setCellValue(m.getName());

        Cell cell2 = row.createCell(2);
        cell2.setCellStyle(styleLightGreen);

        cell2.setCellValue(m.getFormula());

        Cell cell3 = row.createCell(3);
        cell3.setCellStyle(styleLightGreen);
        cell3.setCellValue(m.getMonoisotopicMass());
    }

    public static void finish() throws IOException {
        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("metabolites.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

        g.setStatusLbl("Successfully written to metabolites.xlsx");
    }
}
