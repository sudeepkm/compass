package org.flipkart.compass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created with IntelliJ IDEA. User: sudeep.km Date: 04/11/14 Time: 11:04 AM To change this template
 * use File | Settings | File Templates.
 */
public class HomePage {

  WebDriver driver;

  @FindBy(id = "")
  private WebElement element;

  public HomePage(WebDriver driver) {
    this.driver = driver;
    CompassPageFactory.initElements(this.driver, this);
  }

  public void openHomePage() {
    driver.get("https://www.google.co.in/");
  }

}
