package sam.utils.pom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
	// Description: Object declaration
		protected WebDriver driver;
		private WebDriverWait wait;

		// Description: Constructor de la clase
		public Base(WebDriver driver) {
			this.driver = driver;
			wait = new WebDriverWait(driver, 30);
		}

		/*********************************************************/
		/**************** Selenium POM Methods **************/
		/*********************************************************/

		// Description: Return webdriver
		public WebDriver getDriver() {
			return driver;
		}

		// Description: FindElement Method
		public WebElement findElement(By locator) {
			return driver.findElement(locator);
		}

		// Description: List WebElements Method
		public List<WebElement> findElements(By locator) {
			return driver.findElements(locator);
		}

		// Description: Gettext Method Overload receives locator
		public String getText(By locator) {
			return driver.findElement(locator).getText();
		}

		// Description: Gettext Method Overload receives locator by index
		public String getText(By locator, int index) {
			return driver.findElements(locator).get(index).getText();
		}

		// Description: GetAttribute value method InputText
		public String getAttribute(String value, By locator) {
			return driver.findElement(locator).getAttribute(value);
		}

		// Description: Select DropDownList By Index Locator Method
		public void selectUsingIndex(By locator, int index) {
			Select dDLCombo = new Select(findElement(locator));
			dDLCombo.selectByIndex(index);
		}

		// Description: Select DropDownList By Value Locator Method
		public void selectUsingValue(By locator, String value) {
			Select dDLCombo = new Select(findElement(locator));
			dDLCombo.selectByValue(value);
		}

		// Description: Select DropDownList By Text Locator Method
		public void selectUsingText(By locator, String text) {
			Select dDLCombo = new Select(findElement(locator));
			dDLCombo.selectByVisibleText(text);
		}

		// Description: Click Method catch excepcion
		public void click(By locator) {
			try {
				driver.findElement(locator).click();
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Sendkeys Method
		public void typeKeys(String inputText, By locator) {
			try {
				driver.findElement(locator).sendKeys(inputText);
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Sendkeys(tab,space,etc) Method
		public void typeKeys(Keys keys, By locator) {
			driver.findElement(locator).sendKeys(keys);
		}

		// Description: Sendkeys Method
		public void waitType(By Locator, String s) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
				driver.findElement(Locator).sendKeys(s);
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Clear Method
		public void clear(By locator) {
			try {
				driver.findElement(locator).clear();
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Explicit wait Element to be clickeable
		public void waitClick(By Locator) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(Locator));
				driver.findElement(Locator).click();
			} catch (Exception e) {
				// Log4j2.error(String.format("Couldn't click on element \"%S\"!", Locator));
			}
		}

		// Description: Explicit wait Element to be clickeable
		public void waitNoClick(By Locator) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(Locator));
			} catch (Exception e) {
				// Log4j2.error(String.format("Couldn't click on element \"%S\"!", Locator));
			}
		}

		// Description: Explicit wait Element to be visible and press Click
		public boolean waitLocatorEnabled(By Locator) {
			try {
				return wait.until(ExpectedConditions.visibilityOfElementLocated(Locator)).isEnabled();
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
				return false;
			}
		}

		// Description: Semd text amd clear Element
		protected void waitForElementToAppear(By locator) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Explicit wait Element to be invisible and press Click
		protected void waitForElementToDisappear(By locator) {
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Descripci�n: PopUp Alert send Enter
		public void alertSendKeyEnter() {
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		}

		public void sendKeyEnter(By Locator) {
			driver.findElement(Locator).sendKeys(Keys.ENTER);
		}

		// Description: Method to know if an element of the page loaded
		public boolean isDisplayed(By locator) throws Exception {
			try {
				return driver.findElement(locator).isDisplayed();
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
				return false;
			}
		}

		// Description: Implicit wait global
		public void implicitlyWait(int seconds) {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		}

		// Description: Window maximize
		public void maximizeWindow() {
			driver.manage().window().maximize();
		}

		// Description: Method to go the Url
		public void visit(String url) {
			driver.get(url);
		}

		// Description: Method to obtain the Url
		public String checkUrl() {
			return driver.getCurrentUrl();
		}

		// Description: Method to check if driver url is correct
		public boolean checkUrl(String url) throws Exception {
			try {
				return driver.getCurrentUrl().equals(url);
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
				throw new Exception("No se pudo obtener la Url");
			}
		}

		// Description: Get the current Page Title
		public String getTitlePage() throws Exception {
			try {
				return driver.getTitle();
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
				throw new Exception("No se pudo obtener el titulo de la pesta�a");
			}
		}
		
		// Description: Check if the page title is correct
				public boolean checkTitlePage(String title) throws Exception {
					try {
						return driver.getTitle().equals(title);
					} catch (Exception e) {
						// Log4j2.error(e.getMessage());
						throw new Exception("No se pudo obtener el titulo de la pesta�a");
					}
				}

		// Description: Move back a single "item" in the browser's history
		public void back() {
			driver.navigate().back();
		}
		
		// Description: Move forward a single "item" in the browser's history
		public void forward() {
			driver.navigate().forward();
		}

		// Description: Accept Alert
		public void switchToAlertAccept() {
			driver.switchTo().alert().accept();
		}

		// Description: Capture alerts messages
		public String switchToAlertText() {
			return driver.switchTo().alert().getText();
		}

		// Description: Get the current window handle
		public String getWindowHandle() {
			return driver.getWindowHandle();
		}

		// Description: Get multiple window handles
		public List<String> getWindowHandles() {
			Set<String> winHandles = driver.getWindowHandles();
			List<String> handles = new ArrayList<String>(winHandles);
			return handles;
		}

		// Description: To Perform a WebAction of MouseHover
		public void mouseHover(By Locator) {
			Actions actions = new Actions(getDriver());
			actions.moveToElement(findElement(Locator)).build().perform();
		}

		// Description: Refresh Webpage
		public void refresh() {
			driver.navigate().refresh();
		}

		// Description: Close WebDriver
		public void closeDriver() {
			if (driver != null) {
				try {
					driver.close();
				} catch (org.openqa.selenium.NoSuchSessionException e) {
					// Log4j2.error(e.getMessage());
				}
			}
		}

		// Description: Stop WebDriver
		public void quitDriver() {
			if (driver != null) {
				try {
					driver.quit();
				} catch (org.openqa.selenium.NoSuchSessionException e) {
					// Log4j2.error(e.getMessage());
				}
			}
		}

		// Description: iF the text is present on the website
		public boolean isTextPresent(String text) {
			return driver.getPageSource().contains(text);
		}

		// Description: ThreadSleep method
		public void sleepSeconds(int seconds) {
			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				// Log4j2.error(e.getMessage());
			}
		}

		// Description: Open new tab
		public WebDriver newTab(String url) {
			String a = "window.open('about:blank','_blank');";
			((JavascriptExecutor) driver).executeScript(a);
			this.goToLastTab();
			driver.get(url);
			return getDriver();
		}
		
		// Description: HighLightElements method
		public void highLightElement(WebElement element, String backgroundColor, String borderColor) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style','background: " + backgroundColor + "; border: 2px solid " + borderColor + ";');", element);
		}

		// Description: Go to las tab
		public void goToLastTab() {
			for (String subWindow : getDriver().getWindowHandles()) {
				getDriver().switchTo().window(subWindow);
			}
		}
		
		// Description: Go to tab by Title
				public void goToTabTitle(String title) {
					try {
						Set<String> winHandles = getDriver().getWindowHandles();
						int tabsCount = winHandles.size();
						int currentTabIndex = 1;
						for (String subWindow : winHandles) {
							if(getTitlePage().equals(title)) {
								getDriver().switchTo().window(subWindow);
							}else if (currentTabIndex == tabsCount) throw new Exception();
							currentTabIndex++;
						}
					}catch(Exception e) {
						System.out.println("No se encontr� la pesta�a buscada");
					}
				}
				
				// Description: Go to tab by url
				public void goToTabUrl(String url) {
					try {
						Set<String> winHandles = getDriver().getWindowHandles();
						int tabsCount = winHandles.size();
						int currentTabIndex = 1;
						for (String subWindow : winHandles) {
							if(driver.getCurrentUrl().equals(url)) {
								getDriver().switchTo().window(subWindow);
							}else if (currentTabIndex == tabsCount) throw new Exception();
							currentTabIndex++;
						}
					}catch(Exception e) {
						System.out.println("No se encontr� la pesta�a buscada");
					}
				}
/*
		// Descripci�n: Move focus to alert and getText
		public void aceptarAlerta(String windowHandle) {
			// String windowHandle = driver.getWindowHandle();
			try {
				Alert alert = getDriver().switchTo().alert();
				System.out.println("----------------");
				System.out.println(alert.getText());
				System.out.println("-----------------");
				alert.accept();
				getDriver().switchTo().window(windowHandle);
			} catch (Exception e) {
				// Log4j2.error(e.getMessage());
				System.out.println("No se visualiza alerta");
				getDriver().switchTo().window(windowHandle);
			}
		}
		
		*/
}
