package com.acme.example.test;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.waitGui;

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
        if (isAndroidDriver(GrapheneContext.lastContext().getWebDriver(WebDriver.class))) {
            // add checks that elements are available on Android
            waitGui().withMessage("Add member button is not yet present.")/* .ignoring(WebDriverException.class) */.until()
                .element(addMemberBtn).is().visible();
            // ARQGRA-385
            // Graphene.guardNoRequest(addMemberBtn).click();
            addMemberBtn.click();
            waitGui().withMessage("Add new member form is not yet present.")/* .ignoring(WebDriverException.class) */.until()
                .element(phoneNumberField).is().visible();
        }

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        // ARQGRA-385
        // Graphene.guardAjax(registerBtn).submit();

        registerBtn.submit();

        waitGui().withMessage("Registration screen did not occur within 10 seconds.")
            /* .ignoring(WebDriverException.class) */.until().element(successLabel).is().visible();
    }

    private static boolean isAndroidDriver(Object instance) {

        if (instance instanceof GrapheneProxyInstance) {
            return isAndroidDriver(((GrapheneProxyInstance) instance).unwrap());
        }

        // AndroidDriver is RemoteWebDriver
        if (instance instanceof RemoteWebDriver) {
            Capabilities capabilities = ((RemoteWebDriver) instance).getCapabilities();
            return "android".equals(capabilities.getBrowserName());

        }

        return false;

    }
}
