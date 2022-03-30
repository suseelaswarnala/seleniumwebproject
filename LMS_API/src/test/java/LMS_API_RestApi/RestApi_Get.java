package LMS_API_RestApi;
import org.testng.annotations.Test;

import LMS_API_Utilities.Excelreader;
import LMS_API_Utilities.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;







public class RestApi_Get {

	String sheetGet;
	String path;
	Excelreader excelreader;
	Properties properties;
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	
	
	
	
	public RestApi_Get() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	
	}
	
	@ DataProvider(name="getdata")
	
	public  Object [][] getProgramData() throws Exception {
		  
	     String path="./src/test/resources/ProgramAPIdata/Testdata.xlsx";
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
	String base_url=properties.getProperty("base_url");
	System.out.println(base_url);
	RestAssured.baseURI=base_url;
	httprequest = RestAssured.given().auth().preemptive().basic("","");
	response=httprequest.request(Method.GET);
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,401);
	System.out.println(response);
	}
	@Test(dataProvider="getdata")
	
	public void getAllPrograms( String username,String password,String statuscode) throws Exception
	{
		String base_url=properties.getProperty("base_url");
		RestAssured.baseURI=base_url;
		httprequest = RestAssured.given().auth().preemptive().basic(username, password);
		System.out.println("connected");
		response=httprequest.request(Method.GET);
		//System.out.println(response.getBody().asPrettyString());
		int actual_statuscode=response.getStatusCode();
		Assert.assertEquals(actual_statuscode,200);
		String responsebody=response.getBody().asPrettyString();
		Assert.assertEquals(responsebody.contains("programName"),true);
		Assert.assertEquals(responsebody.contains("programDescription"),true);
		Assert.assertEquals(responsebody.contains("online"),true);
	}
	
	
@Test(dataProvider="getdata")
	
	public void getAllProgramsId(String username,String password,String statuscode) throws IOException
	{
	String base_url=properties.getProperty("endpointGet(id)")+10211;
	RestAssured.baseURI=base_url;
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	System.out.println("connected");
	response=httprequest.request(Method.GET);
	System.out.println(response.getBody().asPrettyString());
	int actual_statuscode=response.getStatusCode();
	Assert.assertEquals(actual_statuscode,200);
	String responsebody=response.getBody().asPrettyString();
	Assert.assertEquals(responsebody.contains("programName"),true);
	Assert.assertEquals(responsebody.contains("programDescription"),true);
	Assert.assertEquals(responsebody.contains("online"),true);
		
	}
	

}
