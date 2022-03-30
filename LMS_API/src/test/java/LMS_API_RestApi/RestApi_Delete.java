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





public class RestApi_Delete {
	
	String sheetDelete;
	String path;
	Excelreader excelreader;
	Properties properties;
	String username="admin";
	String password="password";
	
	RequestSpecification httprequest;
	Response response;
	Excelreader DataProvider;
	public RestApi_Delete() throws Exception {
		PropertiesReader propUtil = new PropertiesReader();
	properties = propUtil.loadProperties();
	
	}
	
@ DataProvider(name="deletedata")
	
	public  Object [][] deleteProgramData() throws Exception {
		  
	     String path="./src/test/resources/ProgramAPIdata/Testdata.xlsx";
		 sheetDelete="Delete";
		 Excelreader excelreader = new Excelreader(path);
		 int totalrows=excelreader.getrowcount(sheetDelete);
		 int totalcols=excelreader.getcellcount(sheetDelete,1);
		 Object[][] deleteprogramData=new Object[totalrows][totalcols];
		 for(int i=0;i<totalrows;i++)
		 {
			 for(int j=0;j<totalcols;j++)
			 {
				 deleteprogramData[i][j]=excelreader.getcelldata(sheetDelete, i, j);
			 }
				 
		 }
	
		return deleteprogramData;
		
	}
//@Test
//	
//	public void deleteProgramsnoAuth() throws Exception
//	{
//	String base_url=properties.getProperty("base_url");
//	System.out.println(base_url);
//	RestAssured.baseURI=base_url;
//	httprequest = RestAssured.given().auth().preemptive().basic("","");
//	int actual_statuscode=response.getStatusCode();
//	Assert.assertEquals(actual_statuscode,400);
//	System.out.println(response);
//	}
	@Test(dataProvider="deletedata")
	
	public void deletePrograms( String program,String Id) throws Exception
	{
		String base_url=properties.getProperty("base_url")+program;
		RestAssured.baseURI=base_url;
		httprequest = RestAssured.given().auth().preemptive().basic(username, password);
		System.out.println("connected");
		response=httprequest.request(Method.DELETE);
		System.out.println(response.getBody().asPrettyString());
		int actual_statuscode=response.getStatusCode();
		Assert.assertEquals(actual_statuscode,404);
	}
	
	
@Test(dataProvider="deletedata")
	
	public void deleteProgramsId(String program,String Id) throws IOException
	{
	String base_url=properties.getProperty("endpointGet(id)")+Id;
	RestAssured.baseURI=base_url;
	httprequest = RestAssured.given().auth().preemptive().basic(username, password);
	System.out.println("connected");
	response=httprequest.request(Method.DELETE);
	System.out.println(response.getBody().asPrettyString());
	int statuscode=response.getStatusCode();
	Assert.assertEquals(statuscode,200);
		
	}
	
	
	

}
