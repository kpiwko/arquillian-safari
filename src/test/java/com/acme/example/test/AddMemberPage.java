package com.acme.example.test;

import static org.jboss.arquillian.graphene.Graphene.waitGui;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
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
        // and this call will likely not work
        if (GrapheneContext.lastContext().getWebDriver(WebDriver.class) instanceof AndroidDriver) {
            waitGui().withMessage("Add member button is not yet present.").until().element(addMemberBtn).is().visible();
            // standard waitModel approach
            addMemberBtn.click();
            waitGui().withMessage("Add new member form is not yet present.").until().element(nameField).is().visible();
            // if experimental Graphene is enabled
            // Graphene.guardNoRequest(addMemberBtn).click();
        }

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        // Guard?
        registerBtn.submit();

        waitGui().withMessage("Registration screen did not occur within 10 seconds.")
                .until().element(successLabel).is().visible();
    }
}
