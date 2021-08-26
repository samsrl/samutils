package sam.utils.pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BrowserManager {

	protected WebDriver driver;
	public static String drv;
	public static Boolean gridEnabled;
	public static String gridUrl;

	@BeforeClass
	@Parameters({ "BrowserType", "Version", "GridEnabled", "GridUrl"})
	public void beforeClass(String BrowserType, String version, String GridEnabled, String GridUrl) throws Exception {
		drv = BrowserType;
		gridEnabled = Boolean.parseBoolean(GridEnabled);
		gridUrl = GridUrl;
		if (BrowserType.equalsIgnoreCase("Chrome")) {
			ChromeDriverCreator driverCreator = new ChromeDriverCreator();
			if(gridEnabled) {
				driver = driverCreator.chromeDriverGrid(version, gridUrl);
			}else {
				driver = driverCreator.chromeDriverLocal(version);
			}
		} else {
			throw new Exception("Browser is not correct");
		}

		driver.get("about:blank");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@AfterClass(alwaysRun = true)
	public void AfterClass() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
			}
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
}
