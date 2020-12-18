package dataProviders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import
net.minidev.json.parser.ParseException;

public class TroconTask {

	String dataFilePath;
	String testExceutionConfigSheetname;
	public static TreeMap<String, HashMap<String, String>> data;
	 XSSFWorkbook workbook=null;

	public TroconTask(String filePath, String exeConfigSheetName) {
		dataFilePath = "C:\\Users\\REKHA\\Downloads\\TestData.xlsx";
		testExceutionConfigSheetname = "TestExecutionConfig";

	}
	

	public static void main(String[] args) throws IOException, ParseException {

		TroconTask obj = new TroconTask("C:/Users/REKHA/Downloads/TestData.xlsx", "TestExecutionConfig");
		obj.validate();
	}

	public void validate() throws IOException, ParseException {
		TreeMap<String, String> executionConfigData = getDataFromExcelSheetwhichHasTwoColumns(
				testExceutionConfigSheetname);
		TreeMap<String, HashMap<String, String>> data = getTestData(executionConfigData.get("TestDataSheetName"));
		String requestTemplate = getRequestBodyTemplate(executionConfigData.get("Template"));
		List<List<String>> requestBodayColumnData = getReuestBodyColumnsAndRawData(data,
				executionConfigData.get("MappingSheetName"));
		TreeMap<String, String> outputReportColumns = getDataFromExcelSheetwhichHasTwoColumns(
				executionConfigData.get("OutputReportColumns"));
		RestAssured.baseURI = executionConfigData.get("Endpoint");
		List<String> loanIds = new ArrayList<String>(data.keySet());
		List<String> resultArr=new ArrayList<String>();
		for (String loan : loanIds) {
			HashMap<String, String> rowData = data.get(loan);
			System.out.println(getRequestBody(rowData, requestTemplate, requestBodayColumnData).toString());
			String resp = RestAssured.given().contentType(ContentType.JSON)
					.body(getRequestBody(rowData, requestTemplate, requestBodayColumnData).toString()).when().post()
					.asString();
			resultArr.add(resp);
			}
		writeData(resultArr, outputReportColumns, loanIds,executionConfigData.get("OutputReportFilepath"));

	}
	
	private  void writeData(List<String>respArr, TreeMap<String, String> reportColumns, List<String> loanIds,String fileName)
			throws IOException {
			 XSSFWorkbook writeWorkbook;
		 XSSFSheet spreadsheet;
		 FileOutputStream out;
			int cellId = 0;
			writeWorkbook = new XSSFWorkbook();
			spreadsheet = writeWorkbook.createSheet("Result");
			XSSFRow row = spreadsheet.createRow(0);
			List<String> colmns = new ArrayList<String>(reportColumns.keySet());
			for (String cellValue : colmns) {
				Cell cell = row.createCell(cellId++);
				cell.setCellValue(cellValue);
			}

		for(int i=0;i<respArr.size();i++) {
		// Create blank workbook
			String resp=respArr.get(i);
		cellId = 0;
		XSSFRow row1 = spreadsheet.createRow(i+1);
		List<String> colmns1 = new ArrayList<String>(reportColumns.keySet());
		//System.out.println(resp.get("$"));
		System.out.println(resp);
		for (String colName : colmns1) {
			try {
			Cell cell = row1.createCell(cellId++);
			  JSONArray jsonArr = (JSONArray)com.jayway.jsonpath.JsonPath.read(resp, "$.."+reportColumns.get(colName));
				System.out.println(jsonArr.get(0));
				  String value=  jsonArr.get(0)+"";
				System.out.println(value);

			//String cellValue = resp.getString("$.loanResult." + loanIds.get(i) + "." + reportColumns.get(colName));
			cell.setCellValue(value);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		}
		out = new FileOutputStream(new File(fileName));
		
		writeWorkbook.write(out);
		writeWorkbook.close();
		out.close();
		System.out.println("outputReport.xlsx written successfully");

	}

//	public static HashMap<String,String> getExcutionDetails(String sheetname)

	public TreeMap<String, String> getDataFromExcelSheetwhichHasTwoColumns(String sheetName) {
		XSSFSheet sheet = getsheet(sheetName);
		int rowCount = sheet.getPhysicalNumberOfRows();
		// int columnCount=sheet.getRow(0).getPhysicalNumberOfCells();

		DataFormatter dataFormatter = new DataFormatter();
		TreeMap<String, String> rowdata = new TreeMap<String, String>();

		for (int i = 1; i < rowCount; i++) {
			rowdata.put(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)),
					dataFormatter.formatCellValue(sheet.getRow(i).getCell(1)));
		}
		return rowdata;
	}

	public List<List<String>> getReuestBodyColumnsAndRawData(TreeMap<String, HashMap<String, String>> data2,
			String sheetName) {

		XSSFSheet sheet = getsheet(sheetName);

		int rowCount = sheet.getPhysicalNumberOfRows();
		DataFormatter dataFormatter = new DataFormatter();
		List<List<String>> rowdata = new ArrayList<List<String>>();

		for (int i = 1; i < rowCount; i++) {
			List<String> colData = new ArrayList<String>();

			colData.add(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)));

			colData.add(dataFormatter.formatCellValue(sheet.getRow(i).getCell(1)));

			colData.add(dataFormatter.formatCellValue(sheet.getRow(i).getCell(2)));
			rowdata.add(colData);
		}
		return rowdata;
	}

	public static JSONObject getRequestBody(HashMap<String, String> testData, String template,
			List<List<String>> mappingColumnData) throws ParseException {

		@SuppressWarnings("deprecation")
		JSONParser parser = new JSONParser();
		JSONObject jsonDataObject = (JSONObject) parser.parse(template);

		for (List<String> item : mappingColumnData) {
			String colName = item.get(0);
			String dataType = item.get(2);
			String jsonPath = item.get(1);
			String fieldValue = "";
			if (testData.containsKey(colName)) {
				fieldValue = testData.get(colName);
			}
			String jsonKeys[] = jsonPath.split("\\.");

			JSONObject temp = jsonDataObject;
			for (int i = 0; i < jsonKeys.length - 1; i++) {

				temp = (JSONObject) temp.get(jsonKeys[i]);
			}

			if (dataType.contentEquals("Integer")) {
				Long num = Long.parseLong(fieldValue.replace(",", ""));
				temp.put(jsonKeys[jsonKeys.length - 1], num);
			} else if (dataType.contentEquals("Decimals")) {
				Double num = Double.parseDouble(fieldValue);
				temp.put(jsonKeys[jsonKeys.length - 1], num);
			} else {
				temp.put(jsonKeys[jsonKeys.length - 1], fieldValue);

			}

		}

		return jsonDataObject;

	}

	public TreeMap<String, HashMap<String, String>> getTestData(String sheetName) throws IOException {

		try {

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = getsheet(sheetName);

			DataFormatter dataFormatter = new DataFormatter();

			// Iterate through each rows one by one
			int rowCount = sheet.getPhysicalNumberOfRows();
			int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

			data = new TreeMap<String, HashMap<String, String>>();

			for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {

				String PrimaryLoanID = dataFormatter.formatCellValue(sheet.getRow(rowIndex).getCell(2));

				HashMap<String, String> rowData = new HashMap<String, String>();
				for (int colIndex = 0; colIndex < colCount; colIndex++) {

					Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
					Cell colName = sheet.getRow(0).getCell(colIndex);
					String cellValue = dataFormatter.formatCellValue(cell).trim().replace("\n", "");
					String colNameValue = dataFormatter.formatCellValue(colName);
					rowData.put(colNameValue, cellValue.replace(",", ""));

				}
				data.put(PrimaryLoanID, rowData);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// workbook.close();
		}

		return data;
	}

	public String getRequestBodyTemplate(String filepath) throws IOException {

		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
		String line = null;
		String ls = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append(ls);
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();

	}

	public XSSFSheet getsheet(String sheetname1) {

		XSSFSheet sheet = null;
		try {
			// Create Workbook instance holding reference to .xlsx file
			if (null == workbook)
				workbook = new XSSFWorkbook(new File(dataFilePath));
			sheet = workbook.getSheet(sheetname1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
}
