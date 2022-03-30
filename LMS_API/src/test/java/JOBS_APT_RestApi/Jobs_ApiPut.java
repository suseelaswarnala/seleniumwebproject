package JOBS_APT_RestApi;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import LMS_API_Utilities.Excelreader;
import LMS_API_Utilities.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Jobs_ApiPut {
	String sheetPut;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public Jobs_ApiPut() throws Exception 
	{
		PropertiesReader propUtil = new PropertiesReader();
	    properties = propUtil.loadProperties();
	}
	//reading data from excel
@ DataProvider(name="putdata")
	
	public Object[][] postjobData() throws Exception
    {
		  
	     String path="./src/test/resources/ProgramAPIdata/Jobsdata.xlsx";
		 sheetPut="Put";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetPut);
		 int totalcols=excelreader.getcellcount(sheetPut,1);
		 Object[][] putjobData=new String[totalrows-1][totalcols];
		 for(int i=1;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 putjobData[i-1][j]=excelreader.getcelldata(sheetPut, i, j);
			 }
				 
		 }
		return putjobData;
     }
//updating jobtitle by giving valid data

@Test(dataProvider="putdata")

public void putjobdata(String JobId,String JobTitle) throws Exception
    {
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	
     response=RestAssured.given().queryParam("Job Id", JobId).queryParam("Job Title",JobTitle).when().put();
     String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  Assert.assertEquals(actual_statuscode,200);
	  //validating response body
	  Assert.assertEquals(responsebody.contains("Job Id"),true);
	  Assert.assertEquals(responsebody.contains("Job Location"),true);
	  Assert.assertEquals(responsebody.contains("Job Company Name"),true);
	  Assert.assertEquals(responsebody.contains("Job Type"),true);
	  Assert.assertEquals(responsebody.contains("Job Posted time"),true);
	  Assert.assertEquals(responsebody.contains("Job Description"),true);
	  //schema validation
	  assertThat("Json Schema",responsebody.replaceAll("NaN", "null"), matchesJsonSchemaInClasspath("JOBSschema.json"));
      }

//updating without data
@Test

public void putjobnodata() throws Exception
{
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	
     response=RestAssured.given().when().put();
     //String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  //asserting status code
	  Assert.assertEquals(actual_statuscode,400);
//	  Assert.assertEquals(responsebody.contains("Job Id"),true);
//	  Assert.assertEquals(responsebody.contains("Job Location"),true);
//	  Assert.assertEquals(responsebody.contains("Job Company Name"),true);
//	  Assert.assertEquals(responsebody.contains("Job Type"),true);
//	  Assert.assertEquals(responsebody.contains("Job Posted time"),true);
//	  Assert.assertEquals(responsebody.contains("Job Description"),true);
}
}
