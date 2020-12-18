package dataProviders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
	
	 String dataFilePath="F:\\All\\training\\src\\test\\resources\\testData\\SampleTestData.xlsx";
	 String sheetName="Sheet2";
	 HashMap<Integer,HashMap<String,String>> data;
	 public ExcelFileReader() throws IOException{
		 this.readExcelData();
		 System.out.println(this.data);
	 }
	
	public static void main(String[] args) throws IOException {
		new ExcelFileReader();
	}
	public  void readExcelData() throws IOException {
	 
	 XSSFWorkbook workbook = null;
	try { 
       
		DataFormatter dataFormatter = new DataFormatter();
        // Create Workbook instance holding reference to .xlsx file 
         workbook =   new XSSFWorkbook(new File(dataFilePath));

        // Get first/desired sheet from the workbook 
       XSSFSheet sheet = workbook.getSheet(sheetName);

        // Iterate through each rows one by one 
       int rowCount=sheet.getPhysicalNumberOfRows();
       int colCount=sheet.getRow(0).getPhysicalNumberOfCells();
       
       for(int i=0;i<rowCount;i++) {
    	   
    	   HashMap<String,String> rowData=new HashMap<String, String>();
    	   
    	   
    	   for(int j=0;j<colCount;j++) {
    		   
    		   String colmName=dataFormatter.formatCellValue(sheet.getRow(0).getCell(j));
    		   String cellValue=dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
    		   rowData.put(colmName, cellValue);
    	   }
    	   
    	   data.put(i, rowData);
       }
       
       
       
       
       
       
       /*
       data=new HashMap<Integer, HashMap<String,String>>();
       
       for(int rowIndex=1;rowIndex<rowCount;rowIndex++) {
    	   
    	   int rowKey=rowIndex;
    	   HashMap<String,String> rowData=new HashMap<String, String>();
    	   for(int colIndex=0;colIndex<colCount;colIndex++) {
    		   
    		   Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
    		   Cell colName = sheet.getRow(0).getCell(colIndex);
               String cellValue=dataFormatter.formatCellValue(cell);
               String colNameValue=dataFormatter.formatCellValue(colName);
               rowData.put(colNameValue,cellValue);
              
       }
    	  data.put(rowIndex, rowData);
                
            } 
           */ 
       
    } 
    catch (Exception e) { 
        e.printStackTrace(); 
    } finally {
    	
    	 workbook.close();
    }
	}
}
