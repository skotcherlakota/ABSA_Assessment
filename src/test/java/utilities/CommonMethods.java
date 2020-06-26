package utilities;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;





public class CommonMethods extends ExtentTestNGReportBuilder {
	public static WebDriver driver;

	public void createTest(String testName) {
		try {
			extentTest = extentReports.createTest(testName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void launchBroswer() {
		try {
			System. setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			extentTest.pass("Browser launched");
		}catch(Exception e) {
			extentTest.fail("Fail to launch broswer");
		}
	}

	public void launchURL(String url) {
		try {
			driver.get(url);
			extentTest.pass("Application launched");
		}catch(Exception e) {
			extentTest.fail("Failed to launch Application");
		}
	}

	public void click(By locator,String locatorName) {
		try {
			driver.findElement(locator).click();
			extentTest.pass("Clicked on "+locatorName);
		}catch(Exception e) {
			sFile = takeAndSaveScreenshot(locatorName);
			extentTest.fail("Failed to click on "+locatorName+" "+sFile);
		}
	}

	public void type(By locator,String locatorName,String input) {
		try {
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(input);
			extentTest.pass("Text entered for element "+locatorName+"=Entered Text "+input);
		}catch(Exception e) {
			sFile = takeAndSaveScreenshot(locatorName);
			extentTest.fail("Failed to enter text for "+locatorName+" "+sFile);
		}
	}
	
	public String getText(By locator) {
		try {
			return driver.findElement(locator).getText();
		}catch(Exception e) {
			return null;
		}
	}


	public String getUniqueUserName() {
		try {
			int length = 10;
			boolean useLetters = true;
			boolean useNumbers = false;
			String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

			return generatedString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isElementDisplayed(By locator) {
		try {
			if (driver.findElement(locator).isDisplayed())

			{

				return true;


			} else {
				return false;

			}
		}catch(Exception e) {
			return false;
		}
	}
	
	
	public boolean waitForElementVisible(By locator, long iMaxTimeout) {
		boolean bReturn = false;
		try {
			(new WebDriverWait(driver, iMaxTimeout))
			.until(ExpectedConditions.visibilityOfElementLocated(locator));
			bReturn = true;
		}catch (Exception e) {

			bReturn = false;
		}
		return bReturn;
	}
	
	public boolean verifyUserListTableDisplayed(By locator) {
		try {
			if(isElementDisplayed(locator)) {
				extentTest.pass("User list table is displayed");
				return true;
			}
			else {
				sFile = takeAndSaveScreenshot("user list table");
				extentTest.fail("User list table is not displayed"+sFile);
				
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	
	public void select(By by, String sText, String sName) throws Exception {
		
		try {
			Select sl = new Select(driver.findElement(by));
			sl.selectByVisibleText(sText);
			extentTest.pass("Select:" + sName + "<br/>Option :" + sText + "value is set");
		} catch (Exception e) {
			sFile = takeAndSaveScreenshot("Select");
			extentTest.fail("Select:" + sName + "<br/>Option :" + sText + "value is  not set "+sFile);
			e.printStackTrace();
		}
	}
	
	public int getTableRowSize() {
		try {
			return driver.findElements(By.xpath("//table[@table-title='Smart Table example']//tbody//tr")).size();
		}catch(Exception e) {
			return 0;
		}
	}
	
	public int getTableHeaderPosition(String header) {
		try {
			int size=driver.findElements(By.xpath("//table[@table-title='Smart Table example']//th")).size();
			for(int i=1;i<=size;i++) {
				
				if(isElementDisplayed(By.xpath("//table[@table-title='Smart Table example']//th["+i+"]//span"))) {
					String headerName=driver.findElement(By.xpath("//table[@table-title='Smart Table example']//th["+i+"]//span")).getText();
					if(headerName.equals(header)) {
						return i;
					}	
				}
				
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
			
		}
		return 0;
	}
	
	public static synchronized String takeAndSaveScreenshot(String sArg) {
		String sReturn = "";
		
		try {

			String sFormat = "yyyyMMddhhmmssSSS";
			String sHref = "";
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			String sTimeStamp = formatter.format(new Date());
			String sFileName = sArg + sTimeStamp.toString() + ".png";
			String sFullFilePath = System.getProperty("user.dir");
			String sDir=sFullFilePath+ "/screenshots/";
			sFullFilePath = sFullFilePath + "/screenshots/" + sFileName;
			File f = new File(sDir);
			if (!f.exists()) {
				new File(sFullFilePath).mkdir();
				System.out.println("Success:: created Directory:" + sFullFilePath);
			}
			if (sFullFilePath.contains(" ")) {
				sHref = sFullFilePath.replaceAll(" ", "%20");
			} else {
				sHref = sFullFilePath;
			}
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(sFullFilePath));
			sReturn = "<br><b><i>Please click on screenshot for more details : </i></b><a href='" + "file:///" + sHref+ "' color=#B22222>" + sFileName + "</a>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sReturn;
	}
	
	
	public static synchronized String writeResponse(String response,String sArg) {
		String sReturn = "";
		
		try {

			String sFormat = "yyyyMMddhhmmssSSS";
			String sHref = "";
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			String sTimeStamp = formatter.format(new Date());
			String sFileName = sArg + sTimeStamp.toString() + ".txt";
			String sFullFilePath = System.getProperty("user.dir");
			String sDir=sFullFilePath+ "/API_Response/";
			new File(sDir).mkdir();
			sFullFilePath = sFullFilePath + "/API_Response/" + sFileName;
			
			  Gson gson = new GsonBuilder().setPrettyPrinting().create();
		        JsonParser jp = new JsonParser();
		        
		        JsonElement je = jp.parse(response);
		        String prettyJsonString = gson.toJson(je);
			if (sFullFilePath.contains(" ")) {
				sHref = sFullFilePath.replaceAll(" ", "%20");
			} else {
				sHref = sFullFilePath;
			}
			
			FileWriter file = new FileWriter(sFullFilePath);
	        file.write(prettyJsonString);
	        file.close();
	        
			sReturn = "<br><b><i>Please click on screenshot for more details : </i></b><a href='" + "file:///" + sHref+ "' color=#B22222>" + sFileName + "</a>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sReturn;
	}
	
	
	
	public void closeBroswer() {
		driver.close();
		driver.quit();
	}
}
