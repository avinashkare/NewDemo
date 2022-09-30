package utils;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;

public class CommonUtility {
	WebDriver driver = null;
	
	public WebDriver initSetup(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		return driver;
	}
	
	public void launchURL(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	public static void takeSS(WebDriver driver) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String d = formatter.format(date).replaceAll("/", "-").replaceAll(":", "-");
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\src\\test\\resources\\Output\\tc-" +d +".jpg");
		FileHandler.copy(src, dest);
	}
	
	public String getDataFromExcel(String path, String shtname, int rw, int ce) throws EncryptedDocumentException, IOException {
		FileInputStream file = new FileInputStream(path);
		Workbook wb= WorkbookFactory.create(file);
		Sheet sh = wb.getSheet(shtname);
		Row r = sh.getRow(rw);
		Cell c = r.getCell(ce);
		CellType ct =c.getCellType();
		String data;
		if(ct.toString().equalsIgnoreCase("string")) {
			data = c.getStringCellValue();
			return data;
		}
		else {
			Double d = c.getNumericCellValue();
			data = d.toString();
			return data;
		}
	}

}
