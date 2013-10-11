/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.droidium.openblend.fragment.mobile;

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class LoginMobileFragment {

    @Root
    private WebElement root;

    @FindBy(id = "username_field")
    private WebElement usernameField;

    @FindBy(id = "password_field")
    private WebElement passwordField;

    @FindBy(id = "login_button")
    private WebElement loginButton;

    public void writeUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void writePassword(String password) {
        passwordField.sendKeys(password);
    }

    public void login() {
        loginButton.click();
    }

}
