import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    private static String[] columns = { "HMDB.No", "Common Name", "Formula", "Monoisotopic Weight",
            "Secondary Accessions", "CAS", "SMILES", "InChlKey", "Kingdom", "SuperClass", "Class", "SubClass",
            "Melting Point", "Boiling Point", "Water Solubility", "Solubility", "logP", "Cellular Locations",
            "Biospecimen Locations", "Tissue Locations", "Normal Concentrations" };
    static Sheet sheet;
    static Workbook workbook;
    static int rowNum = 1;
    static CellStyle styleLightBlue;
    static CellStyle styleLightGreen;
    static GUI g = GUI.getSharedApplication();

    private ExcelWriter() {
    } // make your constructor private, so the only war
      // to access "application" is through singleton pattern

    private static ExcelWriter _app;

    public static ExcelWriter getSharedApplication() {
        if (_app == null)
            _app = new ExcelWriter();
        return _app;
    }

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

        // Create
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public static void write(Metabolite m) {
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

        Cell cell4 = row.createCell(4);
        cell4.setCellStyle(styleLightGreen);
        cell4.setCellValue(m.getSecondaryAccessions());

        Cell cell5 = row.createCell(5);
        cell5.setCellStyle(styleLightGreen);
        cell5.setCellValue(m.getCasNumber());

        Cell cell6 = row.createCell(6);
        cell6.setCellStyle(styleLightGreen);
        cell6.setCellValue(m.getSmiles());

        Cell cell7 = row.createCell(7);
        cell7.setCellStyle(styleLightGreen);
        cell7.setCellValue(m.getInchikey());

        Cell cell8 = row.createCell(8);
        cell8.setCellStyle(styleLightGreen);
        cell8.setCellValue(m.getKingdom());

        Cell cell9 = row.createCell(9);
        cell9.setCellStyle(styleLightGreen);
        cell9.setCellValue(m.getMetaboliteSuperClass());

        Cell cell10 = row.createCell(10);
        cell10.setCellStyle(styleLightGreen);
        cell10.setCellValue(m.getMetaboliteClass());

        Cell cell11 = row.createCell(11);
        cell11.setCellStyle(styleLightGreen);
        cell11.setCellValue(m.getSubClass());

        Cell cell12 = row.createCell(12);
        cell12.setCellStyle(styleLightGreen);
        cell12.setCellValue(m.getMeltingPoint());

        Cell cell13 = row.createCell(13);
        cell13.setCellStyle(styleLightGreen);
        cell13.setCellValue(m.getBoilingPoint());

        Cell cell14 = row.createCell(14);
        cell14.setCellStyle(styleLightGreen);
        cell14.setCellValue(m.getWaterSolubility());

        Cell cell15 = row.createCell(15);
        cell15.setCellStyle(styleLightGreen);
        cell15.setCellValue(m.getSolubility());

        Cell cell16 = row.createCell(16);
        cell16.setCellStyle(styleLightGreen);
        cell16.setCellValue(m.getLogp());

        Cell cell17 = row.createCell(17);
        cell17.setCellStyle(styleLightGreen);
        cell17.setCellValue(m.getCellularLocations());

        Cell cell18 = row.createCell(18);
        cell18.setCellStyle(styleLightGreen);
        cell18.setCellValue(m.getBiospecimenLocations());

        Cell cell19 = row.createCell(19);
        cell19.setCellStyle(styleLightGreen);
        cell19.setCellValue(m.getTissueLocations());

        Cell cell20 = row.createCell(20);
        cell20.setCellStyle(styleLightGreen);
        try {
            cell20.setCellValue(m.getNormalConcentrations());
        } catch (IllegalArgumentException e) {
            if (e.equals("The maximum length of cell contents (text) is 32,767 characters")) {
                cell20.setCellValue("Value too long");
            }
        }

    }

    public static void finish() throws IOException {
        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        File file = new File("metabolites.xlsx");

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fileOut = new FileOutputStream(file, false);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

        rowNum = 1;

        // g.setStatusLbl("Successfully written to metabolites.xlsx");
    }
}
