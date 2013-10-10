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

import org.arquillian.droidium.openblend.drones.Mobile;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Graphene page fragment for adding a task for mobile client.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class TaskMobileFragment {

    @Root
    private WebElement root;

    @Mobile
    @Drone
    private WebDriver mobile;

    @FindBy(id = "name")
    private WebElement mobileName;

    @FindBy(id = "date")
    private WebElement mobileDate;

    @FindBy(id = "description")
    private WebElement mobileDescription;

    @FindBy(id = "buttonSave")
    private WebElement mobileSaveButton;

    @FindBy(id = "add")
    private WebElement mobileAddButton;

    @FindBy(id = "menu_logout")
    private WebElement logoutButton;

    public void addTask(String taskName, String date, String description) {
        mobileAddButton.click();
        mobileName.sendKeys(taskName);
        mobileDate.sendKeys(date);
        mobileDescription.sendKeys(description);
        mobileSaveButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

}
