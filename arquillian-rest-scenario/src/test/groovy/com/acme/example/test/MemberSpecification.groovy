package com.acme.example.test;


import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.junit.runner.RunWith

import spock.lang.Specification

@RunWith(ArquillianSputnik)
public class RestSpecifaction extends Specification {


    @Deployment(testable = false)
    def static deployHtml5DemoApp() {
        Deployments.createDeployment()
    }

    def "Add New Member"() {
        // this is really mock
        given:
        assert 1==1

        expect:
        assert 1==1
    }
}
