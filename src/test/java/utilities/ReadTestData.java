package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadTestData {
	
	public static void main(String[] args) {
		ReadTestData ob=new ReadTestData();
				ob.readTestData("UI");
	}
 public HashMap<Integer, HashMap<String, String>> readTestData(String sheetName) {
	 try {
		FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\testdata\\TestData.xlsx"));
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int noOfRows=sheet.getLastRowNum();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		HashMap<Integer, HashMap<String, String>> testData=new HashMap<Integer, HashMap<String,String>>();
		
		for(int i=1;i<=noOfRows;i++) {
			HashMap<String , String> temp=new HashMap<String, String>();
			for(int j=0;j<noOfColumns;j++) {
				XSSFCell ColumnHeader = sheet.getRow(0).getCell(j);
				XSSFCell ColumnCell = sheet.getRow(i).getCell(j);
				String sCellValue = ColumnCell.getStringCellValue();
				String sCellHeader = ColumnHeader.getStringCellValue();
				temp.put(sCellHeader, sCellValue);
				testData.put(i, temp);
			}
		}
		return testData;
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
 }
}
