package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.element;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;

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
            waitModel().withMessage("Add button is not present.")
                    .until(element(By.id("addMember")).isVisible());

            driver.findElement(By.id("addMember")).click();
        }

        waitModel().withMessage("Registration screen is not present.")
                .until(element(By.id("name")).isVisible());

        driver.findElement(By.id("name")).sendKeys("Samuel");
        driver.findElement(By.id("email")).sendKeys("samuel@vimes.dw");
        driver.findElement(By.id("phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("register")).submit();

        waitModel().withMessage("Registration screen did not occur within 10 seconds.")
                .until(element(By.cssSelector("span.success")).isVisible());

    }
}
