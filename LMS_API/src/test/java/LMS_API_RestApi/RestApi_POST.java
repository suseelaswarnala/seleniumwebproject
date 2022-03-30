package LMS_API_RestApi;
import org.testng.annotations.Test;

import LMS_API_Utilities.Excelreader;

import LMS_API_Utilities.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;


public class RestApi_POST {
	String sheetPost;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public RestApi_POST() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	
	}
	//reading data from excel 
	
@ DataProvider(name="postdata")
	
	public Object[][] postProgramData() throws Exception
{
		  
	     String path="./src/test/resources/ProgramAPIdata/Testdata.xlsx";
		 sheetPost="Post";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetPost);
		 int totalcols=excelreader.getcellcount(sheetPost,1);
		 Object[][] postprogramData=new String[totalrows][totalcols];
		 for(int i=0;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 postprogramData[i][j]=excelreader.getcelldata(sheetPost, i, j);
			 }
				 
		 }
		return postprogramData;
}
@Test(dataProvider="postdata")

public void postProgramsnodata(String program,String Data) throws Exception
{
	String base_url=properties.getProperty("endpointGet(id)");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointGet(id)");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	//response=httprequest.request(Method.POST);
	String bodyExcel=program;
	//System.out.println(bodyExcel);
	httprequest.body(bodyExcel).log().body();
	response = httprequest.when().post(path);
	System.out.println(response.getBody().asPrettyString());
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,400);
	String responsebody=response.getBody().asPrettyString();
	Assert.assertEquals(responsebody.contains("programName"),false);
	Assert.assertEquals(responsebody.contains("programDescription"),false);
	Assert.assertEquals(responsebody.contains("online"),false);
	

}
@Test(dataProvider="postdata")

public void postPrograms(String program,String Data) throws Exception
{
	String base_url=properties.getProperty("endpointGet(id)");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointGet(id)");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	String bodyExcel=Data;
	//System.out.println(bodyExcel);
	
	httprequest.body(bodyExcel).log().body();
	
	
	response = httprequest.when().post(path);
	String responsebody=response.getBody().asPrettyString();
	
	System.out.println("validated");
	System.out.println(response.getBody().asPrettyString());
	
    //setting programid to excel
	JsonPath jsonPathEvaluator=response.jsonPath();
	String ProgramId=jsonPathEvaluator.getString("programId");
	Excelreader excelreader = new Excelreader("./src/test/resources/ProgramAPIdata/Testdata.xlsx");
	excelreader.setcelldata("Delete", 0, 1, ProgramId);
	//validating status code
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,200);
	//validating response body
	Assert.assertEquals(responsebody.contains("programId"),true);
	Assert.assertEquals(responsebody.contains("programName"),true);
	Assert.assertEquals(responsebody.contains("programDescription"),true);
	Assert.assertEquals(responsebody.contains("online"),true);
	//response.then().assertThat().body(responsebody,matchesJsonSchemaInClasspath("LMSschema.json"));
	
	

	//schema validation
	assertThat(responsebody, matchesJsonSchemaInClasspath("LMSschema.json"));

}



}
