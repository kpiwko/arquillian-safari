package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.element;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;

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

    public void addNewMember(String name, String email, String phoneNumber) {
        // Android has a different layout
        if (GrapheneContext.getProxy() instanceof AndroidDriver) {
            waitModel().withMessage("Add button is not present.").until(element(By.id("addMember")).isVisible());
        }

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        // Guard?
        registerBtn.submit();

        waitModel().withMessage("Registration screen did not occur within 10 seconds.").until(
                element(By.cssSelector("span.success")).isVisible());
    }
}
