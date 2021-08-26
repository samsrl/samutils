package sam.utils;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriversUtils {
	// ---------------EJECUTADOR DE DRIVER CONTRA LA GRID---------------\\
	// Metodo que se encarga de la ejecución correcta del driver
	public static WebDriver EjecutaDriverIE(String urlSelenium) throws IOException {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.addCommandSwitches("-private");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setBrowserName("internet explorer");
		capabilities.setPlatform(Platform.WINDOWS);
		return new RemoteWebDriver(new URL(urlSelenium), capabilities);
	}

	public static WebDriver EjecutaDriverChrome(String urlSelenium) throws IOException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--incognito");
		options.addArguments("--ignore-certificate-errors");
		capabilities.setCapability("platform", "LINUX");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return new RemoteWebDriver(new URL(urlSelenium), capabilities);
	}

	public static WebDriver EjecutaDriverFirefox(String urlSelenium,  FirefoxProfile profile) throws IOException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.merge(capabilities);
		capabilities.setPlatform(Platform.LINUX);
		capabilities.setBrowserName("firefox");
		FirefoxOptions firefoxOptionHelper = new FirefoxOptions();
		firefoxOptionHelper.merge(capabilities);
		firefoxOptionHelper.setProfile(profile);

		return new RemoteWebDriver(new URL(urlSelenium), capabilities);
	}

	// ---------------EJECUTADOR DE DRIVER LOCAL---------------\\
	public static WebDriver EjecutaDriverLocalIE(String rutaDriver) throws IOException {
		// Metodo que se encarga de la ejecución correcta del driver
		System.setProperty("webdriver.ie.driver", rutaDriver);
		return new InternetExplorerDriver();

	}

	public static WebDriver EjecutaDriverLocalChrome(String rutaDriver) throws IOException {
		System.setProperty("webdriver.chrome.driver", rutaDriver);
		return new ChromeDriver();
	}

	public static WebDriver EjecutaDriverLocalChrome(String rutaDriver, Boolean headless) throws IOException {
		System.setProperty("webdriver.chrome.driver", rutaDriver);

		if (headless) {
			ChromeOptions options = new ChromeOptions().setHeadless(true);
			return new ChromeDriver(options);
		} else {
			return new ChromeDriver();
		}
	}

	public static WebDriver EjecutaDriverLocalFirefox(String rutaDriver, Boolean headless) throws IOException {
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		firefoxBinary.addCommandLineOptions("--headless");
		System.setProperty("webdriver.gecko.driver", rutaDriver);

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(firefoxBinary);

		return new FirefoxDriver(firefoxOptions);
	}

	public static WebDriver EjecutaDriverLocalFirefox(String rutaDriver, Boolean headless, FirefoxProfile profile) throws IOException {
		FirefoxBinary firefoxBinary = new FirefoxBinary();

		if(headless){
			firefoxBinary.addCommandLineOptions("--headless");
		}

		System.setProperty("webdriver.gecko.driver", rutaDriver);

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setProfile(profile);
		firefoxOptions.setBinary(firefoxBinary);

		return new FirefoxDriver(firefoxOptions);
	}

	public static WebDriver EjecutaFirefoxParaDescargas(String rutaDriver, String descargaPath) throws IOException{
		descargaPath = ArchivosUtils.crearRutaRelativa(descargaPath);

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList",2); //Use for the default download directory the last folder specified for a download
		profile.setPreference("browser.download.dir", descargaPath); //Set the last directory used for saving a file from the "What should (browser) do with this file?" dialog.
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msexcel;application/octet-stream;text/csv;application/csv;text/plan;text/comma-separated-values;application/pdf;text/plain;application/text;text/xml;application/xml;application/json"); //list of MIME types to save to disk without asking what to use to open the file
		profile.setPreference("pdfjs.disabled", true);  // disable the built-in PDF viewer

		return EjecutaDriverLocalFirefox(rutaDriver, true, profile);
	}

	// ---------------CIERRE DE DRIVER---------------\\
	public static void CerraDriver(WebDriver driver) {
		// Metodo que se encarga de cerrar las ventanas que se abrieron
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			System.out.println("No se pudo cerrar el driver");
		}
	}

}