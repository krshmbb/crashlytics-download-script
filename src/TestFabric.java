import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Logs you into fabric and downloads all the .txt and .log files for a given crashlytics issue.
 */
public class TestFabric {
	
	private static final String LOGIN_EMAIL_ELEMENT_ID = "email";
	private static final String LOGIN_PASSWORD_ELEMENT_ID = "password";
	
	private static final String CRASHLYTICS_VIEW_ALL_SESSIONS_LINK = "View all sessions";
	private static final String CRASHLYTICS_DOWNLOAD_TXT_LINK = "Download .txt";
	private static final String CRASHLYTICS_DOWNLOAD_LOG_LINK = "Download .log";
	private static final String CRASHLYTICS_LOG_KEY_TOGGLE_ID = "log-key-toggle";
	private static final String CRASHLYTICS_BACK_ARROW_XPATH = "//a[@data-hint='Tip: Use left arrow!']";
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", FabricConstants.PATH_TO_CHROME_WEBDRIVER);
		WebDriver driver = new ChromeDriver();
		driver.get(FabricConstants.FABRIC_CRASH_URL);
		
		// Login
		WebElement email = driver.findElement(By.id(LOGIN_EMAIL_ELEMENT_ID));
		email.sendKeys(new CharSequence[] {FabricConstants.FABRIC_EMAIL});
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement password = driver.findElement(By.id(LOGIN_PASSWORD_ELEMENT_ID));
		password.sendKeys(new CharSequence[] {FabricConstants.FABRIC_PASSWORD});
		email.submit();
		
		// Scroll down to where you can see the "View all sessions" button
		WebElement viewAllSessions = driver.findElement(By.linkText(CRASHLYTICS_VIEW_ALL_SESSIONS_LINK));
		Point p = viewAllSessions.getLocation();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(" + p.getX() + "," + (p.getY()+150) + ");", new Object[]{});
		viewAllSessions.click();
		
		while (true) {
			// Download Download.txt
			driver.findElement(By.linkText(CRASHLYTICS_DOWNLOAD_TXT_LINK)).click();
			
			// Flip to Log			
			WebElement toggle = driver.findElement(By.id(CRASHLYTICS_LOG_KEY_TOGGLE_ID));
			js.executeScript("arguments[0].click();", new Object[] {toggle});
			
			// Download Download .log
			try {
			driver.findElement(By.linkText(CRASHLYTICS_DOWNLOAD_LOG_LINK)).click();
			} catch (Exception e) {}
			
			// Switch back to 'Key'
			js.executeScript("arguments[0].click();", new Object[] {toggle});
			
			// Select previous if available or break
			driver.findElement(By.xpath(CRASHLYTICS_BACK_ARROW_XPATH)).click();
			
			// The lookup fails if I don't give the page enough time to load 
			Thread.sleep(1000);
		}
	}
}
