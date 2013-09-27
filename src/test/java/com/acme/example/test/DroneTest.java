package com.acme.example.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class DroneTest {

    @Drone
    WebDriver driver;

    @Deployment(testable = false)
    public static Archive<?> deployHtml5DemoApp() {
        return Deployments.createDeployment();
    }

    @Test
    public void addUser(@InitialPage AddMemberPage addMemberPage) throws Exception {
        addMemberPage.addNewMember("Samuel", "samuel@vimes.dw", "1234567890");
    }
}
