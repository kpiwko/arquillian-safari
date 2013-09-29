package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.waitGui;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.support.FindBy;

@Location("")
public class AddMemberPage {

    @FindBy(id = "addMember")
    private WebElement addMemberBtn;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phoneNumber")
    private WebElement phoneNumberField;

    @FindBy(id = "register")
    private WebElement registerBtn;

    @FindBy(css = "span.success")
    private WebElement successLabel;

    public void addNewMember(String name, String email, String phoneNumber) {
        // Android has a different layout
        if (GrapheneContext.lastContext().getWebDriver(WebDriver.class) instanceof AndroidDriver) {
            // add checks that elements are available on Android
            waitGui().withMessage("Add member button is not yet present.").ignoring(WebDriverException.class).until()
                    .element(addMemberBtn).is().visible();
            addMemberBtn.click();
            waitGui().withMessage("Add new member form is not yet present.").ignoring(WebDriverException.class).until()
                    .element(phoneNumberField).is().visible();
        }

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        registerBtn.submit();

        waitGui().withMessage("Registration screen did not occur within 10 seconds.")
                .ignoring(WebDriverException.class).until().element(successLabel).is().visible();
    }
}
