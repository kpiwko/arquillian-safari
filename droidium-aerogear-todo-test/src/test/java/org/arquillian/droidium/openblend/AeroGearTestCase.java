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
package org.arquillian.droidium.openblend;

import static org.arquillian.droidium.openblend.utils.Utils.*;
import java.net.URL;

import org.arquillian.droidium.container.api.AndroidDevice;
import org.arquillian.droidium.openblend.drones.Browser;
import org.arquillian.droidium.openblend.drones.Mobile;
import org.arquillian.droidium.openblend.fragment.mobile.LoginMobileFragment;
import org.arquillian.droidium.openblend.fragment.mobile.TaskMobileFragment;
import org.arquillian.droidium.openblend.fragment.web.LoginWebFragment;
import org.arquillian.droidium.openblend.fragment.web.ProjectFragment;
import org.arquillian.droidium.openblend.fragment.web.TaskWebFragment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class AeroGearTestCase extends AbstractAeroGearTestCase {

    @Browser
    @FindBy(id = "login-box")
    private LoginWebFragment loginFragment;

    @Browser
    @FindBy(id = "project-list")
    private ProjectFragment projectFragment;

    @Browser
    @FindBy(id = "task-container")
    private TaskWebFragment taskFragment;

    @Mobile
    @FindBy(id = "content")
    private LoginMobileFragment loginMobileFragment;

    @Mobile
    @FindBy(id = "todo")
    private TaskMobileFragment taskMobileFragment;

    @Test
    @InSequence(1)
    @OperateOnDeployment("todo-ear-app")
    public void loginUserInWebClient(@ArquillianResource URL context) {
        openWebPageUrl(browser, context);

        loginFragment.writeUsername("john");
        loginFragment.writePassword("123");

        waitUtil(NORMAL);

        loginFragment.submitForm();
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment("todo-ear-app")
    public void addProject() {

        projectFragment.click();
        projectFragment.addProject("groceries");

        Assert.assertEquals(projectFragment.getAddedProject().getText(), "groceries");
    }

    @Test
    @InSequence(3)
    @OperateOnDeployment("todo-ear-app")
    public void addTask() {
        taskFragment.click();
        taskFragment.addTitle("buy some milk");
        taskFragment.addDate("2020", "10", "20");
        taskFragment.addDescription("buy some fresh milk around the corner");
        taskFragment.addToProject("groceries");
        taskFragment.add();

        Assert.assertEquals(taskFragment.getAddedTask().getTitle(), "buy some milk");
        Assert.assertEquals(taskFragment.getAddedTask().getDate(), "2020-10-21");
        Assert.assertEquals(taskFragment.getAddedTask().getDescription(), "buy some fresh milk around the corner");

        waitUtil(NORMAL);
    }

    @Test
    @InSequence(4)
    @OperateOnDeployment("todo-mobile-app")
    public void loginUserInMobile(@ArquillianResource AndroidDevice device) {
        device.getActivityManagerProvider()
            .getActivityManager().startActivity("org.jboss.aerogear.todo.activities.LoginActivity");

        loginMobileFragment.writeUsername("john");
        loginMobileFragment.writePassword("123");
        loginMobileFragment.login();

        waitUtil(NORMAL);

    }

    @Test
    @InSequence(5)
    @OperateOnDeployment("todo-mobile-app")
    public void addMobileTask() {
        taskMobileFragment.addTask();

        taskMobileFragment.addName("mobile task");
        taskMobileFragment.addDate("2014-10-20");
        taskMobileFragment.addDescription("task from mobile phone!");
        taskMobileFragment.submitTask();

        waitUtil(NORMAL);
    }

    @Test
    @InSequence(6)
    @OperateOnDeployment("todo-ear-app")
    public void seeMobileTaskInWebClient() {
        browser.navigate().refresh();
        waitUtil(SLOW);
    }

    @Test
    @InSequence(7)
    @OperateOnDeployment("todo-mobile-app")
    public void logoutFromMobileClient() {
        taskMobileFragment.logout();
        waitUtil(FAST);
    }

    @Test
    @InSequence(8)
    @OperateOnDeployment("todo-ear-app")
    public void logoutFromWebClient() {
        browser.findElement(By.id("logout-btn")).click();
        waitUtil(SLOW);
    }
}
