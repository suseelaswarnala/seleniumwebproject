package LMS_API_RestApi;
import org.testng.annotations.Test;

import LMS_API_Utilities.Excelreader;
import LMS_API_Utilities.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class RestApi_PUT {
	String sheetPost;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public RestApi_PUT() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	
	}
@ DataProvider(name="putdata")
	
	public Object[][] putProgramData() throws Exception
{
		  
	     String path="./src/test/resources/ProgramAPIdata/Testdata.xlsx";
		 sheetPost="Put";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetPost);
		 int totalcols=excelreader.getcellcount(sheetPost,1);
		 Object[][] putprogramData=new String[totalrows-1][totalcols];
		 for(int i=1;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 putprogramData[i-1][j]=excelreader.getcelldata(sheetPost, i, j);
			 }
				 
		 }
		return putprogramData;
}
@Test(dataProvider="putdata")

public void putProgramsnodata(String program,String Data) throws Exception
{
	String base_url=properties.getProperty("endpointput(id)");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointput(id)");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	//response=httprequest.request(Method.POST);
	String bodyExcel=program;
	//System.out.println(bodyExcel);
	httprequest.body(bodyExcel).log().body();
	response = httprequest.when().put(path);
	System.out.println(response.getBody().asPrettyString());
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,400);
	String responsebody=response.getBody().asPrettyString();
//response body validation
	Assert.assertEquals(responsebody.contains("programId"),false);
	Assert.assertEquals(responsebody.contains("programName"),false);
	Assert.assertEquals(responsebody.contains("programDescription"),false);
	Assert.assertEquals(responsebody.contains("online"),false);

}
@Test(dataProvider="putdata")

public void putProgramsid(String program,String Data) throws Exception
{
	String base_url=properties.getProperty("endpointput(id)");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointput(id)");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	String bodyExcel=Data;
	//System.out.println(bodyExcel);
	httprequest.body(bodyExcel).log().body();
	response = httprequest.when().put(path);
	System.out.println(response.getBody().asPrettyString());
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,200);
	String responsebody=response.getBody().asPrettyString();
	Assert.assertEquals(actual_statuscode,200);
	//validating response body
	Assert.assertEquals(responsebody.contains("programId"),true);
	Assert.assertEquals(responsebody.contains("programName"),true);
	Assert.assertEquals(responsebody.contains("programDescription"),true);
	Assert.assertEquals(responsebody.contains("online"),true);
	// shema validation
	assertThat(responsebody, matchesJsonSchemaInClasspath("LMSschema.json"));

}
	

}
