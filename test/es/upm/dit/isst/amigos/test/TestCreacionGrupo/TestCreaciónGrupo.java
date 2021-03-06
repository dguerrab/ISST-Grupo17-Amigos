package es.upm.dit.isst.amigos.test.TestCreacionGrupo;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCreacionGrupo {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    FirefoxProfile firefoxProfile = new FirefoxProfile();    
    firefoxProfile.setPreference("browser.privatebrowsing.autostart",true);
	driver = new FirefoxDriver(firefoxProfile);
    baseUrl = "http://1-dot-isst-grupo17-amigos-1287.appspot.com/";
    driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
  }

  @Test
  public void testCreacionGrupo() throws Exception {
	driver.get(baseUrl); // + "Grupos"
	driver.findElement(By.linkText("Iniciar Sesión")).click();
	driver.findElement(By.id("Email")).clear();
    driver.findElement(By.id("Email")).sendKeys("dguerrab");
    driver.findElement(By.id("next")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("bs-example-navbar-collapse-1")));
    assertEquals("Por si eres un despistado aquí tienes los grupos a los que estás apuntado\n¡No olvides tampoco comprar el regalo antes de la fecha de entrega de regalos!", driver.findElement(By.cssSelector("p")).getText());
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id("exampleInputName2")));
    driver.findElement(By.id("exampleInputName2")).clear();
    driver.findElement(By.id("exampleInputName2")).sendKeys("shockerstrike");
    driver.findElement(By.id("exampleInputName3")).clear();
    driver.findElement(By.id("exampleInputName3")).sendKeys("inkunknoown");
    driver.findElement(By.name("groupname")).clear();
    driver.findElement(By.name("groupname")).sendKeys("Test");
    driver.findElement(By.name("maxprice")).clear();
    driver.findElement(By.name("maxprice")).sendKeys("10");
    driver.findElement(By.name("date")).clear();
    driver.findElement(By.name("date")).sendKeys("10-2-2017");
    driver.findElement(By.id("exampleInputMessage")).clear();
    driver.findElement(By.id("exampleInputMessage")).sendKeys("Holi");
    driver.findElement(By.cssSelector("div.col-md-6 > button.btn.btn-default")).click();
    driver.findElement(By.name("item")).clear();
    driver.findElement(By.name("item")).sendKeys("nachoperegrino94");
    driver.findElement(By.cssSelector("input.btn.btn-default")).click();
    driver.findElement(By.name("item")).clear();
    driver.findElement(By.name("item")).sendKeys("erodvarg");
    driver.findElement(By.cssSelector("input.btn.btn-default")).click();
    driver.findElement(By.linkText("Cerrar Sesión")).click();
    driver.findElement(By.linkText("Iniciar Sesión")).click();
    driver.findElement(By.id("account-chooser-link")).click();
    driver.findElement(By.id("account-chooser-add-account")).click();
    driver.findElement(By.id("Email")).clear();
    driver.findElement(By.id("Email")).sendKeys("inkunknoown");
    driver.findElement(By.id("next")).click();
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id("bs-example-navbar-collapse-1")));
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    driver.findElement(By.linkText("Cerrar Sesión")).click();
    driver.findElement(By.linkText("Iniciar Sesión")).click();
    driver.findElement(By.id("choose-account-0")).click();
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id("bs-example-navbar-collapse-1")));
    assertEquals("Por si eres un despistado aquí tienes los grupos a los que estás apuntado\n¡No olvides tampoco comprar el regalo antes de la fecha de entrega de regalos!", driver.findElement(By.cssSelector("p")).getText());    
    driver.findElement(By.cssSelector("li > form > button.btn.btn-default")).click();
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    assertEquals("Test (10-2-2017) Precio Max: (10)", driver.findElement(By.cssSelector("div.col-md-6 > p")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
