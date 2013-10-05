package com.acme.example.test;


import groovy.json.JsonBuilder

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.junit.runner.RunWith

import spock.lang.Specification

import com.jayway.restassured.RestAssured


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

    // curl -v -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"name" : "ddd", "description" :  "ddd" }' http://localhost:8080/ag-push/rest/
    def "Registering a push application"() {


        given: "Application ddd is about to be registered......"
        def request = RestAssured.given()
                .contentType("application/json")
                .header("Accept", "application/json")
                .cookies(authCookies)
                .body(json {
                    name "ddd"
                    description "ddd"
                })

        when: "Application is registered"
        def response = RestAssured.given().spec(request).post("${root}rest/applications")
        def body = response.body().jsonPath()
        pushAppId = body.get("pushApplicationID")

        then: "Response code is 201"
        response.statusCode() == 201

        and: "Push App Id is returned"
        pushAppId != null

        and: "Application Name is returned"
        body.get("name") == "ddd"
    }

    // enable direct invocation of json closure to all test writers
    // to construct Json objects in very simple DSL like way
    //
    // json {
    //   name "ddd"
    //   description "ddd"
    // }
    //
    def Closure json = new Closure(this, this) {
        def doCall(Object args) {
            new JsonBuilder().call(args)
        }
    }
}
