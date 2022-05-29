package es.uma.informatica.sii.proyecto;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.uma.informatica.sii.anotaciones.Requisitos;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
@Requisitos(value = { "RF3" })
public class ModificarClienteIT {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void modificarClienteIT() {
    driver.get("http://localhost:8080/proyecto-war/");
    driver.manage().window().setSize(new Dimension(976, 1040));
    driver.findElement(By.linkText("pulse aqui")).click();
    driver.findElement(By.id("loginAdmin:user")).click();
    driver.findElement(By.id("loginAdmin:user")).sendKeys("ponciano");
    driver.findElement(By.id("loginAdmin:pass")).click();
    driver.findElement(By.id("loginAdmin:pass")).sendKeys("ponciano");
    driver.findElement(By.id("loginAdmin:botonLogin")).click();
    driver.findElement(By.id("clientesIndividuales")).click();
    driver.findElement(By.id("formulario:j_idt16:2:botonMod")).click();
    driver.findElement(By.id("modIndividual")).click();
    driver.findElement(By.id("modIndividual:ciudad")).sendKeys("Almargen");
    driver.findElement(By.id("modIndividual:botonModificarIndividual")).click();
    driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(10)")).click();
    assertThat(driver.findElement(By.id("formulario:j_idt16:2:textoCiudad")).getText(), is("Almargen"));
  }
}
