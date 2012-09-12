package com.acme.example.test;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

@RunWith(Arquillian.class)
public class DroneTest {

    @Drone
    WebDriver driver;

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> deployHtml5DemoApp() {
        return Deployments.createDeployment();
    }

    @Test
    public void openHomePage() {
        driver.get(contextPath.toString());
    }

    @Test
    public void addUser() throws Exception {
        driver.get(contextPath.toString());

        if (driver instanceof AndroidDriver) {
            driver.findElement(By.id("addMember")).click();
            checkElementPresence(driver, By.id("name"), "Registration screen did not occur within 10 seconds.");
        }

        driver.findElement(By.id("name")).sendKeys("Samuel");
        driver.findElement(By.id("email")).sendKeys("samuel@vimes.dw");
        driver.findElement(By.id("phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("register")).submit();

    }

    // check is element is presence on page, fails otherwise
    protected void checkElementPresence(final WebDriver driver, final By by, final String errorMsg) {
        new WebDriverWaitWithMessage(driver, 10).failWith(errorMsg).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    return driver.findElement(by).isDisplayed();
                } catch (NoSuchElementException ignored) {
                    return false;
                } catch (StaleElementReferenceException ignored) {
                    return false;
                }
            }
        });
    }

    protected static class WebDriverWaitWithMessage extends WebDriverWait {

        private String message;

        public WebDriverWaitWithMessage(WebDriver driver, long timeOutInSeconds) {
            super(driver, timeOutInSeconds);
        }

        public WebDriverWait failWith(String message) {
            if (message == null || message.length() == 0) {
                throw new IllegalArgumentException("Error message must not be null nor empty");
            }
            this.message = message;
            return this;
        }

        @Override
        public <V> V until(Function<? super WebDriver, V> isTrue) {
            if (message == null) {
                return super.until(isTrue);
            } else {
                try {
                    return super.until(isTrue);
                } catch (TimeoutException e) {
                    throw new TimeoutException(message, e);
                }
            }
        }
    }

}
