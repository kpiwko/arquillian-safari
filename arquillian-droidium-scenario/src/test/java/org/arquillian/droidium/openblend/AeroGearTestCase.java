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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.arquillian.droidium.native_.api.Instrumentable;
import org.arquillian.droidium.openblend.drones.Browser;
import org.arquillian.droidium.openblend.drones.Mobile;
import org.arquillian.droidium.openblend.fragment.mobile.LoginLogoutPageMobile;
import org.arquillian.droidium.openblend.fragment.mobile.TodoPageMobile;
import org.arquillian.droidium.openblend.fragment.web.LoginLogoutPage;
import org.arquillian.droidium.openblend.fragment.web.TodoPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * A simple tests that shows interaction in between Java EE backend application installed on
 * application server and native Android application.
 *
 * @author <a href="mailto:smikloso@redhat.com">Karel Piwko</a>
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@RunWith(Arquillian.class)
@RunAsClient
public class AeroGearTestCase {

    // deployment of android application to a device
    @Deployment(name = "todo-mobile-app")
    @Instrumentable(viaPort = 8081)
    @TargetsContainer("android")
    public static JavaArchive androidApplication() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("android-todos.apk"));
    }

    // deployment of ear application to a server
    @Deployment(name = "todo-ear-app")
    @TargetsContainer("jbossas")
    public static EnterpriseArchive serverBackendApplication() {
        return ShrinkWrap.createFromZipFile(EnterpriseArchive.class, new File("todo-ear.ear"));
    }

    // handler for desktop browser automation
    @Drone
    @Browser
    WebDriver browser;

    // handler for Android application automation
    @Drone
    @Mobile
    WebDriver mobile;

    // FIXME Graphene does not support injection of @Page in parameters
    @Page
    @Browser
    TodoPage todoPage;

    @Page
    @Mobile
    TodoPageMobile taskPageMobile;

    @Page
    @Mobile
    LoginLogoutPageMobile logoutPageMobile;

    @Page
    @Browser
    LoginLogoutPage logoutPage;

    @Test
    @InSequence(1)
    @OperateOnDeployment("todo-ear-app")
    public void addProjectAndTaskViaWeb(@InitialPage @Browser LoginLogoutPage loginPage) {
        loginPage.login("john", "123");

        todoPage.addProject("groceries");
        todoPage.addTask("buy some milk", "2020-10-20", "buy some fresh milk around the corner");
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment("todo-mobile-app")
    public void addTaskViaMobileApp(@InitialPage @Mobile LoginLogoutPageMobile loginPage) {
        loginPage.login("john", "123");
        taskPageMobile.addTask("mobile task", "2014-10-20", "task from mobile phone!");
    }

    @Test
    @InSequence(3)
    @OperateOnDeployment("todo-ear-app")
    public void seeMobileTaskInWebClient() {
        browser.navigate().refresh();
        assertThat(todoPage.totalTasks(), is(2));
    }

    @Test
    @InSequence(4)
    @OperateOnDeployment("todo-mobile-app")
    public void logoutFromMobileClient() {
        logoutPageMobile.logout();
    }

    @Test
    @InSequence(5)
    @OperateOnDeployment("todo-ear-app")
    public void logoutFromWebClient() {
        logoutPage.logout();
    }
}
