package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.element;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage {

    @FindBy(id = "name")
    WebElement nameField;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "phoneNumber")
    WebElement phoneNumberField;

    @FindBy(id = "register")
    WebElement registerButton;

    @FindBy(id = "addMember")
    WebElement addMemberButton;

    public void addMember(String name, String email, String phoneNumber) {

        if (GrapheneContext.holdsInstanceOf(AndroidDriver.class)) {
            waitModel().withMessage("Add button is not present.")
                    .until(element(addMemberButton).isVisible());

            addMemberButton.click();
        }

        waitModel().withMessage("Registration screen is not present.")
                .until(element(nameField).isVisible());

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);
        registerButton.submit();

        waitModel().withMessage("Registration screen did not occur within 10 seconds.")
                .until(element(By.cssSelector("span.success")).isVisible());

    }

}
