package apiTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.CommonMethods;
import utilities.ReadPropertiesFile;

public class API_Test extends CommonMethods
{
	ReadPropertiesFile getProperty=new ReadPropertiesFile();
	String baseURL = getProperty.getValuFromPropertiesFile("API_URL");
	String listOfAllDogBreeds= getProperty.getValuFromPropertiesFile("listOfAllDogBreeds");
	String subbreed= getProperty.getValuFromPropertiesFile("subbreed");
	String randomImage= getProperty.getValuFromPropertiesFile("randomImage");
	
	@Test
	public void listOfAllDogBreeds() {
		
		try {
			createTest("Get list of all dog breeds and verify retriever breed");
			RestAssured.baseURI=baseURL;
			RestAssured.basePath=listOfAllDogBreeds;
			Response response=given().contentType(ContentType.JSON).log().all().get();
			String json=response.prettyPrint();
			if(response.getStatusCode()==200) {
				sFile=CommonMethods.writeResponse(json, "listOfAllDogBreeds");	
				extentTest.pass("Status code is 200 for API ::"+listOfAllDogBreeds+"<br/> Response body::"+sFile);
				
			}else {
				extentTest.fail("Status code is not expected<br/>Actual : 200<br/>Expected::"+response.getStatusCode());
			}
			
			ObjectMapper om = new ObjectMapper();
			JsonNode jn = om.readTree(json);
	        JsonNode ob = jn.get("message");
	        if(ob.findValue("retriever")!=null) {
	        	sFile=CommonMethods.writeResponse(ob.findValue("retriever").toString(), "retriverBreed");
	        	extentTest.pass("Retriever breed with in the list"+sFile);
	        }else {
	        	extentTest.fail("Retriever breed is not in the list");
	        }
	    	
	    	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void subBread() {
		RestAssured.baseURI=baseURL;
		RestAssured.basePath=subbreed;
		createTest("API request to produce a list of sub-breeds for retriever");
		Response response=given().contentType(ContentType.JSON).log().all().get();
		String json=response.prettyPrint();
		if(response.getStatusCode()==200) {
			sFile=CommonMethods.writeResponse(json, "list of sub-breeds for retriever");	
			extentTest.pass("Status code is 200 for API ::"+subbreed+"<br/> Response body::"+sFile);
			
		}else {
			extentTest.fail("Status code is not expected<br/>Actual : 200<br/>Expected::"+response.getStatusCode());
		}
		
	}
	
	@Test
	public void randomImage() {
		RestAssured.baseURI=baseURL;
		RestAssured.basePath=randomImage;
		createTest("API request to produce a random image or link for the sub-breed golden");
		Response response=given().contentType(ContentType.JSON).log().all().get();
		String json=response.prettyPrint();
		if(response.getStatusCode()==200) {
			sFile=CommonMethods.writeResponse(json, "random image or link for the sub-breed golden");	
			extentTest.pass("Status code is 200 for API ::"+randomImage+"<br/> Response body::"+sFile);
			
		}else {
			extentTest.fail("Status code is not expected<br/>Actual : 200<br/>Expected::"+response.getStatusCode());
		}
	}
	
	@AfterTest
	public void flush() {
		extentReports.flush();
	}
}
