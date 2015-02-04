package org.arquillian.droidium.openblend.fragment.mobile;

import org.arquillian.droidium.native_.spi.location.DroidiumScheme;
import org.arquillian.droidium.openblend.drones.Mobile;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.FindBy;

@Location(scheme = DroidiumScheme.class, value = "org.jboss.aerogear.todo.activities.LoginActivity")
public class LoginLogoutPageMobile {

    @Mobile
    @FindBy(id = "action_bar_container")
    private ActionBar actionBar;

    @FindBy(id = "content")
    private LoginMobileFragment loginMobileFragment;

    public void login(String username, String password) {
        loginMobileFragment.login(username, password);
    }

    public void logout() {
        actionBar.logout();
    }
}
