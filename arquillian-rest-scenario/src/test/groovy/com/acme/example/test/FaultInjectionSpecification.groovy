package com.acme.example.test;


import groovy.json.JsonBuilder

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.extension.byteman.api.BMRule
import org.jboss.arquillian.extension.byteman.api.BMRules
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith

import spock.lang.Specification
import spock.lang.Unroll

import com.jayway.restassured.RestAssured


@RunWith(ArquillianSputnik)
@BMRules([
    @BMRule(
    name = "Fail to persist Member Bob Smith",
    targetClass = 'javax.persistence.EntityManager',
    isInterface = true,
    targetMethod = 'persist(Object)',
    condition = '$1.toString().contains("bob.smith")',
    action = 'throw new java.lang.RuntimeException("Failed to persist member (simulated)")')
])
public class FaultInjectionSpecification extends Specification {


    @Deployment(testable=false)
    static WebArchive deployHtml5DemoApp() {
        Deployments.createDeployment()
    }

    def setupSpec() {
        //RestAssured.filters(new RequestLoggingFilter(System.err), new ResponseLoggingFilter(System.err))
    }

    @ArquillianResource URL root

    @Unroll
    def "Add New Member"() {

        given: "New Member ${memberName}"
        def request = RestAssured.given()
                .contentType("application/json")
                .header("Accept", "application/json")
                .body(json {
                    name memberName
                    email memberEmail
                    phoneNumber memberPhoneNumber
                })
        def shouldFail = !invalidFields.isEmpty()


        when: "Member ${memberName} is registered"
        def response = RestAssured.given().spec(request).post("${root}rest/members")
        def body = !invalidFields.isEmpty() ? response.body().jsonPath() : new JsonBuilder("{}")

        then: "Response code is 200 - passed or 400 - failed"
        shouldFail ? response.statusCode == 400 : response.statusCode == 200

        and: "Invalid fields are reported in the respones"
        shouldFail ? invalidFields.each {
            body.get(it.key) != it.value
        } : true

        where:
        memberName | memberEmail | memberPhoneNumber | invalidFields
        "Jane Smith" | "jane.smith@example.org" | "5551212345" | [:]
        "Bob Smith" | "bob.smith@example.org" | "5551234567" | ["error" : "Failed to persist member (simulated)"]
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
