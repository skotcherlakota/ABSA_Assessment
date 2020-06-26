package apiTests;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;

public class APITest {
	@Test
	public void getResponseBody() throws JsonProcessingException, IOException {
		
		RestAssured.baseURI="https://dog.ceo";
		RestAssured.basePath="/api/breeds/list/all";
		Response response=given().contentType(ContentType.JSON).log().all().get();
		String json=response.prettyPrint();
		
		
		JsonPath extractor=response.jsonPath();
		String temp=extractor.get("status");
		System.out.println("----------");
		System.out.println(temp);
		
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(json);
        JsonNode ob = jn.get("message");
    	System.out.println(ob.findValue("retriever"));
//        for(JsonNode o : ob) {
//        	System.out.println(o.findValue("retriever"));
//        		System.out.print(o);
//        }
        
        
//        ObjectMapper om2 = new ObjectMapper();
//		JsonNode jn2 = om.readTree(ob.toString());
//        JsonNode ob2 = jn.get("retriever");
        
        System.out.println("-------------");
//        System.out.println(ob2.toString());
		
		
	}
	
	
//	public void JsonNode readJsonNode(String object_key, String json) throws IOException {
//        ObjectMapper om = new ObjectMapper();
//        JsonNode jn = om.readTree(json);
//        JsonNode ob = jn.get(object_key);
////        System.out.println(object_key + " content  " + ob);
//        return ob;
//    }
}
