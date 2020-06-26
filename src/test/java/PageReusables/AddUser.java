package PageReusables;


import java.util.HashMap;
import org.openqa.selenium.By;

import utilities.CommonMethods;
import utilities.ExtentTestNGReportBuilder;
import utilities.ReadTestData;

public class AddUser extends ExtentTestNGReportBuilder {

	CommonMethods cm=new CommonMethods();
	//Locators
	By addUser=By.xpath("//button[contains(text(),'Add User')]");
	By addUserPageHeader=By.xpath("//h3[text()='Add User']");
	By companyAAA=By.xpath("//label[text()='Company AAA']");
	By companyBBB=By.xpath("//label[text()='Company BBB']");
	By role=By.name("RoleId");
	By userName=By.xpath("//td[text()='User Name']/parent::*//input");
	By save=By.xpath("//button[text()='Save']");
	By placeholder=By.xpath("//input[@placeholder='Search']");
	public void addUser() {
		try {
			ReadTestData readTestData=new ReadTestData();
			HashMap<Integer, HashMap<String, String>> testdata=readTestData.readTestData("UI");
			for(int i=1;i<=testdata.size();i++) {
				extentTest.info("Add user -"+i);
				cm.click(addUser, "Add user");
				if(cm.isElementDisplayed(addUserPageHeader)) {
					extentTest.pass("Add user page is displayed");
					HashMap<String, String> temp=testdata.get(i);
					String uniqueUserName=cm.getUniqueUserName();
					cm.type(userName, "User name", uniqueUserName);
					String [] fields= {"First Name","Last Name","Password","E-mail","Cell Phone"};
					for(String field:fields) {
						cm.type(By.xpath("//td[text()='"+field+"']/parent::*//input"), field, temp.get(field));
					}
					if(temp.get("Customer").equals("Company AAA")) {
						cm.click(companyAAA, "customer - company AAA");
					}else if(temp.get("Customer").equals("Company BBB")){
						cm.click(companyBBB, "customer - company BBB");
					}
					cm.select(role, temp.get("Role"), "Role");
					cm.click(save, "Save");
					extentTest.info("Verfifying added user-"+i);
					verifyAddedUser(uniqueUserName,temp);
				}
			else {
				sFile = CommonMethods.takeAndSaveScreenshot("Add user page");
				extentTest.fail("Add user page is not displayed"+sFile);
			}
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}

public void verifyAddedUser(String uniqueUserName,HashMap<String, String> expTestdata) {
	try {
		cm.type(placeholder, "Search", uniqueUserName);
		if(cm.getTableRowSize()>0) {
			extentTest.pass("User added");
			String [] fields= {"First Name","Last Name","User Name","Customer","Role","E-mail","Cell Phone"};
			for(String temp:fields) {
				String expValue;
				if(temp.equals("User Name")) {
					expValue=uniqueUserName;
				}
				else {
					expValue=expTestdata.get(temp);	
				}
				
				int headerPosition=cm.getTableHeaderPosition(temp);
				String actValue=cm.getText(By.xpath("//table[@table-title='Smart Table example']//tbody//tr//td["+headerPosition+"]"));
				if(expValue.equals(actValue)) {
					extentTest.pass("Entered value is dispalyed in table for- "+temp+"<br/>Expected:"+expValue+"<br/>Actual Value"+actValue);
				}
				else {
					sFile = CommonMethods.takeAndSaveScreenshot("validation of "+temp);
					extentTest.fail("Entered value is dispalyed in table for- "+temp+"<br/>Expected:"+expValue+"<br/>Actual Value"+actValue+" "+sFile);
				}
				
			}
			
			
		}
		else{
			extentTest.fail("User not added");
		}
		

	}catch(Exception e) {
		e.printStackTrace();
	}
}
}
