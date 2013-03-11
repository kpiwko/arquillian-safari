package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.element;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;

import com.google.common.base.Predicate;

public class AddMemberPage {

    @FindBy(id = "addMember")
    WebElement addMemberBtn;

    @FindBy(id = "name")
    WebElement nameField;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "phoneNumber")
    WebElement phoneNumberField;

    @FindBy(id = "register")
    WebElement registerBtn;

    @FindBy(css = "span.success")
    WebElement successLabel;

    public void addNewMember(String name, String email, String phoneNumber) {
        // Android has a different layout
        if (GrapheneContext.holdsInstanceOf(AndroidDriver.class)) {
            waitModel().withMessage("Add member button is not yet present.").until(element(addMemberBtn).isVisible());
            // standard waitModel approach
            addMemberBtn.click();
            waitModel().withMessage("Add new member form is not yet present.").until(element(nameField).isVisible());
            // if experimental Graphene is enabled
            // Graphene.guardNoRequest(addMemberBtn).click();
        }

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        // Guard?
        registerBtn.submit();

        waitModel().withMessage("Registration screen did not occur within 10 seconds.")
                .until(element(successLabel).isVisible());
    }
}
