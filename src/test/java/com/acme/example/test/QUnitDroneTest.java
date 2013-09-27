package com.acme.example.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.qunit.junit.QUnitRunner;
import org.jboss.arquillian.qunit.junit.annotations.QUnitResources;
import org.jboss.arquillian.qunit.junit.annotations.QUnitTest;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;

@RunWith(QUnitRunner.class)
@QUnitResources("src/test/qunit")
public class QUnitDroneTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return Deployments.createDeployment();
    }

    @QUnitTest("index.html")
    public void qunitAjaxTest() {
        // empty body - only the annotations are used
        System.err.println("ERRER");
    }

}
