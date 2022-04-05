package seleniumwebproject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class webproject {

	public static void main(String[] args) {
		 System.setProperty("webdriver.chrome.driver", "C:\\Users\\ramba\\eclipse\\chromedriver.exe");  
		    WebDriver driver=new ChromeDriver();  
		      
		// Launch website  
		    driver.navigate().to("http://www.google.com/");  
		          
		     
		    driver.navigate().to("http://www.javatpoint.com/");  
		 // Click on the search text box and send value  
		    driver.findElement(By.id("lst-ib")).sendKeys("javatpoint tutorials");  
		          
		    // Click on the search button  
		    driver.findElement(By.name("btnK")).click(); 
	          
	         //Maximize the browser  
	          driver.manage().window().maximize();  
	          
	          //Scroll down the webpage by 5000 pixels  
	        JavascriptExecutor js = (JavascriptExecutor)driver;  
	        js.executeScript("scrollBy(0, 5000)");   
	          
	         // Click on the Search button  
	        driver.findElement(By.linkText("Core Java")).click();  
//	        System.setProperty("webdriver.gecko.driver","C:\\Users\\ramba\\eclipse\\geckodriver.exe" );  
//	    	FirefoxOptions options = new FirefoxOptions();  
//	        options.setLegacy(true); 
//	        
//	        // Initialize Gecko Driver using Desired Capabilities Class  
//	    //DesiredCapabilities capabilities = DesiredCapabilities.firefox();  
//	    //capabilities.setCapability("marionette",true);  
//	    WebDriver driver1= new FirefoxDriver();  
//	        
//	       // Launch Website  
//	    driver1.navigate().to("http://www.javatpoint.com/");  
//	        
//	      // Click on the Custom Search text box and send value  
//	    driver1.findElement(By.id("gsc-i-id1")).sendKeys("Java");  
//	        
//	      // Click on the Search button  
//	    driver1.findElement(By.className("gsc-search-button gsc-search-buttonv2")).click();

	}

}
