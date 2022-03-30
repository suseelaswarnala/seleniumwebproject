package JOBS_APT_RestApi;
import org.testng.annotations.Test;

import LMS_API_Utilities.Excelreader;
import LMS_API_Utilities.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class Jobs_ApiGet {
	String sheetGet;
	String path;
	Excelreader excelreader;
	Properties properties;
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public Jobs_ApiGet() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	
	}
@ DataProvider(name="getdata")
	
	public  Object [][] getProgramData() throws Exception {
		  
	     String path="./src/test/resources/ProgramAPIdata/Jobsdata.xlsx";
		 sheetGet="Get";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetGet);
		 int totalcols=excelreader.getcellcount(sheetGet,1);
		 Object[][] getprogramData=new Object[totalrows][totalcols];
		 for(int i=0;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 getprogramData[i][j]=excelreader.getcelldata(sheetGet, i, j);
			 }
				 
		 }
	
		return getprogramData;
		
	}
@Test
	
	public void getAllProgramsnoAuth() throws Exception
	{
	String base_url=properties.getProperty("base_url1");
	System.out.println(base_url);
	RestAssured.baseURI=base_url;
	httprequest = RestAssured.given().auth().preemptive().basic("","");
	int actual_statuscode=response.getStatusCode();
	
	Assert.assertEquals(actual_statuscode,200);
	System.out.println(response);
	}
	@Test(dataProvider="getdata")
	
	public void getAllJobs( String username,String password,String statuscode) throws Exception
	{
		String base_url=properties.getProperty("base_url1");
		RestAssured.baseURI=base_url;
		httprequest = RestAssured.given().auth().preemptive().basic(username, password);
		System.out.println("connected");
		response=httprequest.request(Method.GET);
		System.out.println(response.getBody().asPrettyString());
		int actual_statuscode=response.getStatusCode();
		Assert.assertEquals(actual_statuscode,200);
		String responsebody=response.getBody().asPrettyString();
	      System.out.println(response.getBody().asPrettyString());
		  Assert.assertEquals(actual_statuscode,200);
		  //response body validation
		  Assert.assertEquals(responsebody.contains("Job Id"),true);
		  Assert.assertEquals(responsebody.contains("Job Location"),true);
		  Assert.assertEquals(responsebody.contains("Job Company Name"),true);
		  Assert.assertEquals(responsebody.contains("Job Type"),true);
		  Assert.assertEquals(responsebody.contains("Job Posted time"),true);
		  Assert.assertEquals(responsebody.contains("Job Description"),true);
		  //schema validation
		  assertThat("Json Schema",responsebody.replaceAll("NaN", "null"), matchesJsonSchemaInClasspath("JOBSschema.json"));
	}
	
	
//@Test(dataProvider="getdata")
//	
//	public void getAllJobsId(String username,String password,String statuscode) throws IOException
//	{
//	String base_url=properties.getProperty("endpointjobGet")+1035;
//	RestAssured.baseURI=base_url;
//	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
//	System.out.println("connected");
//	response=httprequest.request(Method.GET);
//	System.out.println(response.getBody().asPrettyString());
//	int actual_statuscode=response.getStatusCode();
//	Assert.assertEquals(actual_statuscode,200);
//		
//	}
	

}
