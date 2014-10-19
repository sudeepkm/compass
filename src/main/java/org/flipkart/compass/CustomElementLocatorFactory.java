package org.flipkart.compass;

import org.flipkart.compass.annotations.ChildOf;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA. User: sudeep.km Date: 11/10/14 Time: 4:20 PM To change this template
 * use File | Settings | File Templates.
 */
public class CustomElementLocatorFactory implements ElementLocatorFactory {

  private final SearchContext searchContext;

  public CustomElementLocatorFactory(final SearchContext searchContext) {
    this.searchContext = searchContext;
  }

  @Override
  public ElementLocator createLocator(final Field field) {
    if (!field.isAnnotationPresent(ChildOf.class)) {
      return new CustomElementLocator(searchContext, field);
    }
    return null;
  }

}
