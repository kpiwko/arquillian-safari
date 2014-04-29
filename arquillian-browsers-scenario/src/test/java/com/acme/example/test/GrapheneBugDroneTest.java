package com.acme.example.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.drone.api.annotation.Qualifier;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunWith(Arquillian.class)
public class GrapheneBugDroneTest {

    @Drone
    @BugReport
    WebDriver driver;

    @Deployment(testable = false)
    public static Archive<?> deployHtml5DemoApp() {
        return Deployments.createDeployment();
    }

    @ArquillianResource
    URL url;

    @FindBy(id = "name")
    @BugReport
    WebElement nameField;

    @Test
    public void addUser() throws Exception {
        driver.get(url.toString());
        Graphene.waitGui().until().element(nameField).is().present();
        Assert.assertTrue(true);
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface BugReport {
    }
}
