package org.flipkart.compass;

import org.flipkart.compass.annotations.ChildOf;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: sudeep.km Date: 11/10/14 Time: 4:14 PM To change this template
 * use File | Settings | File Templates.
 */
public class ChildElementLocatorFactory implements ElementLocatorFactory {

  final Map<String, List<WebElement>> parentCandidates;

  public ChildElementLocatorFactory(final Map<String, List<WebElement>> parentCandidates) {
    this.parentCandidates = parentCandidates;
  }

  @Override
  public ElementLocator createLocator(final Field field) {
    if (field.isAnnotationPresent(ChildOf.class)) {
      return new ChildElementLocator(parentCandidates, field);
    }
    return null;
  }

}
