package LMS_API_Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelreader {
	String path;
    FileInputStream fis;
    FileOutputStream fos;
    Workbook workbook;
    Sheet sheet;
    Row row;
	private Cell cell;

    public Excelreader(String path) {
        super();
        this.path = path;
    }
    public void readSheet(String sheetName) throws Exception {

        File file = new File(path);
        fis = new FileInputStream(file);

        //Below API only ready xlsx formats
        workbook = new XSSFWorkbook(fis);

        //Below API only ready xls formats
        
        sheet = workbook.getSheet(sheetName);

    }
    
   
    public int getrowcount(String sheetName) throws Exception {

        File file = new File(path);
        fis = new FileInputStream(file);
        //sheetName="./src/test/resources/ProgramAPIDATA/Testdata.xlsx";

        //Below API can ready both xls and xlsx formats
       //workbook = WorkbookFactory.create(fis);

        //Below API only ready xlsx formats
        workbook = new XSSFWorkbook(fis);

        //Below API only ready xls formats
        //workbook=new HSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        int rowcount=sheet.getLastRowNum();
        System.out.println(rowcount);
        workbook.close();
        return rowcount;
            }
    public int getcellcount(String sheetName, int rownum) throws Exception {

        File file = new File(path);
        fis = new FileInputStream(file);
        //sheetName="./src/test/resources/ProgramAPIDATA/Testdata.xlsx";

        //Below API can ready both xls and xlsx formats
       //workbook = WorkbookFactory.create(fis);

        //Below API only ready xlsx formats
        workbook = new XSSFWorkbook(fis);

        //Below API only ready xls formats
        //workbook=new HSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row=sheet.getRow(rownum);
        int cellcount=row.getLastCellNum();
        System.out.println(cellcount);
        workbook.close();
        return cellcount;
            }
    public String getcelldata(String sheetName, int rownum,int colnum) throws Exception {

        File file = new File(path);
        fis = new FileInputStream(file);
        
        workbook = new XSSFWorkbook(fis);

        //Below API only ready xls formats
        //workbook=new HSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row=sheet.getRow(rownum);
        cell=row.getCell(colnum);
        DataFormatter formatter=new DataFormatter();
        String data;
        try
        {
        	data=formatter.formatCellValue(cell);
            System.out.print("dataaaaaa "+data);
        }
        catch(Exception e)
        {
        	data="";
        }
        
        workbook.close();
        return data;
        }
    public String setcelldata(String sheetName, int rownum,int colnum,String data) throws Exception {

        File file = new File(path);
        fis = new FileInputStream(file);
        
        workbook = new XSSFWorkbook(fis);
      
        sheet = workbook.getSheet(sheetName);
        row=sheet.getRow(rownum);
        cell=row.createCell(colnum);
        cell.setCellValue(data);
        fos=new FileOutputStream(path);
        workbook.write(fos);
        workbook.close();
        return data;
        }
    
    
    

    public String getDataFromExcel(String rowName, String colName) throws IOException {
    	
        int dataRowNum = -1;
        int dataColNum = -1;
        int totalRows = sheet.getLastRowNum();
        int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i <= totalRows; i++) {
            if (sheet.getRow(i).getCell(0).getStringCellValue().equals(rowName)) {
                dataRowNum = i;
                break;
            }

        }
        for (int j = 0; j <= totalCols; j++) {
            if (sheet.getRow(0).getCell(j).getStringCellValue().equals(colName)) {
                dataColNum = j;
                break;
            }
        }

         String body = sheet.getRow(dataRowNum).getCell(dataColNum).getStringCellValue();
        System.out.println(body);
        
        fis.close();
        return body;
    }
    

}
