package com.acme.example.test;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class DroneTest {

    @Drone
    WebDriver driver;

    @ArquillianResource
    URL contextPath;

    @Page
    AddMemberPage addMemberPage;

    @Deployment(testable = false)
    public static Archive<?> deployHtml5DemoApp() {
        return Deployments.createDeployment();
    }

    @Test
    public void addUser() throws Exception {
        driver.get(contextPath.toString());
        addMemberPage.addNewMember("Samuel", "samuel@vimes.dw", "1234567890");
    }
}
