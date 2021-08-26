package sam.utils.pom;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

// TODO: Auto-generated Javadoc
/**
 * Esta clase ejecuta el WebDriver de chrome
 */
public class ChromeDriverCreator extends BrowserManager {

	/**
	 * Chrome driver connection (Inicializaci�n del webdriver de chrome con todas
	 * las capabilities que deshabilitan muchas funciones para que no influya al
	 * momento de hacer los request
	 *
	 * @return the web driver
	 * @throws Exception the exception
	 */

	private String version = null;

	// Description: Creo la conexion chrome driver
	public WebDriver chromeDriverLocal(String version) throws Exception {
		this.version = version;
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options.addArguments("--aggressive-cache-discard", "--allow-insecure-localhost",
				"--allow-running-insecure-content", "--disable-application-cache", "--disable-browser-side-navigation",
				"--disable-cache", "--disable-client-side-phishing-detection", "--disable-default-apps",
				"--disable-extensions", "disable-infobars", "--disable-notifications",
				"--disable-offline-load-stale-cache", "--disable-popup-blocking", "--disable-web-security",
				"--ignore-certificate-errors", "--no-sandbox", "start-maximized", "test-type=browser");
		// Description: solve timeout exception issue
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		// Description: Disabled save pass prefs
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		// options.setCapability("version", this.version); // TODO ES NECESARIO?
		WebDriver driver = new ChromeDriver(options);
		// System.out.println("Version driver----------------:" + this.version);
		return driver;
	}
	
	public WebDriver chromeDriverGrid(String version, String urlSelenium) throws Exception {
		try {
			this.version = version;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("--incognito");
			options.addArguments("--ignore-certificate-errors");
			options.setCapability("version", this.version);
			capabilities.setCapability("platform", "LINUX");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			return new RemoteWebDriver(new URL(urlSelenium), capabilities);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		 
	}
}