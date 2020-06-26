package uiTests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import PageReusables.AddUser;
import utilities.CommonMethods;
import utilities.ReadPropertiesFile;

public class Way2Automation extends CommonMethods {
	
	
	@Test
	public void way2AutomationTest() {
		AddUser addUser=new AddUser();
		ReadPropertiesFile getProperty=new ReadPropertiesFile();
		By userListTable=By.xpath("//table[@table-title='Smart Table example']");
		createTest("Way2Automation UI Test");
		launchBroswer();
		launchURL(getProperty.getValuFromPropertiesFile("URL"));
		if(verifyUserListTableDisplayed(userListTable)) {
			addUser.addUser();
		}
		closeBroswer();
		
		
	}
	@AfterTest
	public void flush() {
		extentReports.flush();
	}
	
	
}
