package org.flipkart.compass;

import org.flipkart.compass.annotations.AngularLocator;
import org.flipkart.compass.annotations.JQueryLocator;
import org.flipkart.compass.annotations.OptionalElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomElementLocator implements ElementLocator {

  private final SearchContext searchContext;
  private final AngularLocator angularLocator;
  private final JQueryLocator jql;
  private final boolean isOptionalElement;
  private final Annotations annotations;

  public CustomElementLocator(final SearchContext searchContext, final Field field) {
    this.searchContext = searchContext;
    if (field.isAnnotationPresent(AngularLocator.class)) {
      this.angularLocator = field.getAnnotation(AngularLocator.class);
      this.jql = null;
      this.annotations = null;
    } else if (field.isAnnotationPresent(JQueryLocator.class)) {
      this.jql = field.getAnnotation(JQueryLocator.class);
      this.angularLocator = null;
      this.annotations = null;
    } else {
      this.angularLocator = null;
      this.jql = null;
      this.annotations = new Annotations(field);
    }
    this.isOptionalElement = field.isAnnotationPresent(OptionalElement.class);
  }

  @Override
  public WebElement findElement() {
    WebElement element = null;
    if (angularLocator != null) {
      if (searchContext instanceof JavascriptExecutor) {
        final JavascriptExecutor jse = (JavascriptExecutor) searchContext;
        element =
            (WebElement) jse
                .executeScript("return document.querySelector(\""+ angularLocator.$() + "\")");
      } else {
        if (!isOptionalElement) {
          throw new IllegalArgumentException(
              "The SearchContext is not able to execute Javascript. Unable to locate element with Angular elements");
        }
      }
    } else if (jql != null) {
      if (searchContext instanceof JavascriptExecutor) {
        final JavascriptExecutor jse = (JavascriptExecutor) searchContext;
        element = (WebElement) jse.executeScript("return $" + jql.jQuery() + "[0]");
      } else {
        if (!isOptionalElement) {
          throw new IllegalArgumentException(
              "The SearchContext is not able to execute Javascript. Unable to locate element with jQuery");
        }
      }
    } else {
      element = searchContext.findElement(annotations.buildBy());
    }
    if (element == null && !isOptionalElement) {
      throw new NoSuchElementException(
          "Unable to locate element with Angular expression: " + angularLocator.$());
    }
    return element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<WebElement> findElements() {
    List<WebElement> elements = new ArrayList<>();
    if (angularLocator != null) {
      if (searchContext instanceof JavascriptExecutor) {
        final JavascriptExecutor jse = (JavascriptExecutor) searchContext;
        elements =
            (List<WebElement>) jse
                .executeScript("return document.querySelectorAll(" + angularLocator.$() + ")");
      } else {
        if (!isOptionalElement) {
          throw new IllegalArgumentException(
              "The SearchContext is not able to execute Javascript. Unable to locate elements with Angular");
        }
      }
    } else if (jql != null) {
      if (searchContext instanceof JavascriptExecutor) {
        final JavascriptExecutor jse = (JavascriptExecutor) searchContext;
        elements = (List<WebElement>) jse.executeScript("return $" + jql.jQuery());
      } else {
        if (!isOptionalElement) {
          throw new IllegalArgumentException(
              "The SearchContext is not able to execute Javascript. Unable to locate elements with jQuery");
        }
      }
    } else {
      elements = searchContext.findElements(annotations.buildBy());
    }
    return elements;
  }

  public boolean isOptionalElement() {
    return isOptionalElement;
  }

  @Override
  public String toString() {
    if (annotations != null) {
      return "Attempting to find element(s): " + annotations.buildBy() + "\tIs Optional Element: "
             + isOptionalElement;
    }
    return "Attempting to find element(s) with Angular: " + angularLocator.$()
           + "\tIs Optional Element: " + isOptionalElement;
  }
}
