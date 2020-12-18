package managers;

import java.io.IOException;

import dataProviders.ConfigFileReader;
import dataProviders.ExcelFileReader;

public class FileReaderManager {

	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private static ExcelFileReader excelFileReader;
    
	private FileReaderManager() {
	}

	 public static FileReaderManager getInstance( ) {
	      return fileReaderManager;
	 }

	 public ConfigFileReader getConfigReader() {
		 return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	 }
	 
	 public ExcelFileReader getExcelFileReader() throws IOException {
		 return (excelFileReader == null) ? new ExcelFileReader() : excelFileReader;
	 }
}