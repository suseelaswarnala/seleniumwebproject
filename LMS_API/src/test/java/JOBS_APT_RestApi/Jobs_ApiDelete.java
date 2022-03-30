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

public class Jobs_ApiDelete {
	String sheetDelete;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public Jobs_ApiDelete() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	}
@ DataProvider(name="deletedata")
	
	public Object[][] DeletejobData() throws Exception
{
		  
	     String path="./src/test/resources/ProgramAPIdata/Jobsdata.xlsx";
		 sheetDelete="Delete";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetDelete);
		 int totalcols=excelreader.getcellcount(sheetDelete,2);
		 Object[][] deletejobData=new String[totalrows-1][totalcols];
		 for(int i=1;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 deletejobData[i-1][j]=excelreader.getcelldata(sheetDelete, i, j);
			 }
				 
		 }
		return deletejobData;
}
@Test(dataProvider="deletedata")

public void deletejobdata(String JobId) throws Exception
{
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	
     response=RestAssured.given().queryParam("Job Id", JobId).when().delete();
     String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  //response body validation
	  Assert.assertEquals(actual_statuscode,200);
	  Assert.assertEquals(responsebody.contains("Job Id"),true);
	  Assert.assertEquals(responsebody.contains("Job Location"),true);
	  Assert.assertEquals(responsebody.contains("Job Company Name"),true);
	  Assert.assertEquals(responsebody.contains("Job Type"),true);
	  Assert.assertEquals(responsebody.contains("Job Posted time"),true);
	  Assert.assertEquals(responsebody.contains("Job Description"),true);
	  //schema validation
	  assertThat("Json Schema",responsebody.replaceAll("NaN", "null"), matchesJsonSchemaInClasspath("JOBSschema.json"));
}
@Test

public void deletejobnodata() throws Exception
{
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	String JobId="0";
     response=RestAssured.given().queryParam("Job Id", JobId).when().delete();
     String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  Assert.assertEquals(actual_statuscode,404);
	  Assert.assertEquals(responsebody.contains("Job Id"),false);
	  Assert.assertEquals(responsebody.contains("Job Location"),false);
	  Assert.assertEquals(responsebody.contains("Job Company Name"),false);
	  Assert.assertEquals(responsebody.contains("Job Type"),false);
	  Assert.assertEquals(responsebody.contains("Job Posted time"),false);
	  Assert.assertEquals(responsebody.contains("Job Description"),false);
}

}
