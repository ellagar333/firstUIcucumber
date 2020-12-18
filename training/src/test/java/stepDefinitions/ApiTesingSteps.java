package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import cucumber.api.java.en.When;
import dataProviders.TroconTask;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.parser.ParseException;

public class ApiTesingSteps {
	 
	@When("^read testData into hashMap from location \"([^\"]*)\"$")
		public void fillFormDetails(String filePath) throws IOException, ParseException  {
		
		TroconTask obj = 
				new TroconTask(filePath, "TestExecutionConfig");
		TreeMap<String, String> executionConfigData = obj.getDataFromExcelSheetwhichHasTwoColumns(
				"TestExecutionConfig");
		
		String requestTemplate = obj.getRequestBodyTemplate(executionConfigData.get("Template"));
		List<List<String>> requestBodayColumnData = obj.getReuestBodyColumnsAndRawData(TroconTask.data,executionConfigData.get("MappingSheetName"));
		TreeMap<String, String> outputReportColumns = obj.getDataFromExcelSheetwhichHasTwoColumns(
				executionConfigData.get("OutputReportColumns"));
		RestAssured.baseURI = executionConfigData.get("Endpoint");
		List<String> loanIds = new ArrayList<String>(TroconTask.data.keySet());
		List<String> resultArr=new ArrayList<String>();
		
		
	}

}
