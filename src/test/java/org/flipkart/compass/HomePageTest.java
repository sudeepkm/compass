package org.flipkart.compass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA. User: sudeep.km Date: 04/11/14 Time: 11:08 AM To change this template
 * use File | Settings | File Templates.
 */
public class HomePageTest {

  WebDriver driver;

  @BeforeClass
  public void setUp() {
    this.driver = new FirefoxDriver();
  }

  @Test
  public void testHomePage() {
    HomePage homePage = new HomePage(driver);
    homePage.openHomePage();
  }

}
