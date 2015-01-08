package org.arquillian.droidium.openblend.fragment.mobile;

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ActionBar {

    @Root
    private WebElement root;

    @FindBy(id = "menu_logout")
    private WebElement logoutButton;

    public void logout() {
        logoutButton.click();
    }

}
