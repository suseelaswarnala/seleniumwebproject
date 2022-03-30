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

public class Jobs_ApiPost {
	String sheetPost;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public Jobs_ApiPost() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();

}
@ DataProvider(name="postdata")
	
	public Object[][] postjobData() throws Exception
{
		  
	     String path="./src/test/resources/ProgramAPIdata/Jobsdata.xlsx";
		 sheetPost="Post";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetPost);
		 int totalcols=excelreader.getcellcount(sheetPost,1);
		 Object[][] postjobData=new String[totalrows-1][totalcols];
		 for(int i=1;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 postjobData[i-1][j]=excelreader.getcelldata(sheetPost, i, j);
			 }
				 
		 }
		return postjobData;
}
@Test(dataProvider="postdata")

public void postjobdata(String JobId,String JobTitle,String JobLocation,String JobCompanyname,String JobType,String JobPostedtime,String JobDescription) throws Exception
{
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	
     response=RestAssured.given().queryParam("Job Id", JobId).queryParam("Job Title",JobTitle)
                         .queryParam("Job Location", JobLocation)
                         .queryParam("Job Company Name", JobCompanyname)
                         .queryParam("Job Type", JobType)
                         .queryParam("Job Posted time", JobPostedtime)
                         .queryParam("Job Description", JobDescription).when().post();
     String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  Assert.assertEquals(actual_statuscode,200);
	  Assert.assertEquals(responsebody.contains("Job Id"),true);
	  Assert.assertEquals(responsebody.contains("Job Location"),true);
	  Assert.assertEquals(responsebody.contains("Job Company Name"),true);
	  Assert.assertEquals(responsebody.contains("Job Type"),true);
	  Assert.assertEquals(responsebody.contains("Job Posted time"),true);
	  Assert.assertEquals(responsebody.contains("Job Description"),true);
	  
	  
	  assertThat("Json Schema",responsebody.replaceAll("NaN", "null"), matchesJsonSchemaInClasspath("JOBSschema.json"));

}
@Test

public void postjobnodata() throws Exception
{
	String base_url=properties.getProperty("endpointjobGet");
	RestAssured.baseURI=base_url;
	path=properties.getProperty("endpointjobGet");
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	httprequest.header("Content-Type", "application/json");
	
     response=RestAssured.given().when().post();
     //String responsebody=response.getBody().asPrettyString();
      System.out.println(response.getBody().asPrettyString());
	  int actual_statuscode=response.getStatusCode();
	  Assert.assertEquals(actual_statuscode,400);
//	  Assert.assertEquals(responsebody.contains("Job Id"),false);
//	  Assert.assertEquals(responsebody.contains("Job Location"),false);
//	  Assert.assertEquals(responsebody.contains("Job Company Name"),false);
//	  Assert.assertEquals(responsebody.contains("Job Type"),false);
//	  Assert.assertEquals(responsebody.contains("Job Posted time"),false);
//	  Assert.assertEquals(responsebody.contains("Job Description"),false);

}
}
