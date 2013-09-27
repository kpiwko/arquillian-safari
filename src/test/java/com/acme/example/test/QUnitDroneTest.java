package com.acme.example.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.qunit.junit.QUnitRunner;
import org.jboss.arquillian.qunit.junit.annotations.QUnitResources;
import org.jboss.arquillian.qunit.junit.annotations.QUnitTest;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;

@RunWith(QUnitRunner.class)
@QUnitResources("src/")
public class QUnitDroneTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return Deployments.createDeployment();
    }

    @QUnitTest("test/qunit/index.html")
    public void qunitTest() {
        // empty body - only the annotations are used
    }

}
