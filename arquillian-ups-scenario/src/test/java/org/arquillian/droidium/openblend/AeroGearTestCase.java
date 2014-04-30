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

import java.io.File;

import org.arquillian.droidium.native_.api.Instrumentable;
import org.arquillian.droidium.native_.webdriver.AndroidDriver;
import org.arquillian.droidium.openblend.drones.Mobile;
import org.arquillian.droidium.openblend.drones.Tablet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@RunWith(Arquillian.class)
@RunAsClient
public class AeroGearTestCase {

    @Drone
    @Mobile
    AndroidDriver mobile;

    @Drone
    @Tablet
    AndroidDriver tablet;

    @Deployment(name = "mobile-app")
    @Instrumentable(viaPort = 8081)
    @TargetsContainer("mobile")
    public static JavaArchive getMobileDeployment() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("android-devconf2014.apk"));
    }

    @Deployment(name = "tablet-app")
    @Instrumentable(viaPort = 8082)
    @TargetsContainer("tablet")
    public static JavaArchive getTabletDeployment() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("android-devconf2014.apk"));
    }

    @Page
    @Mobile
    TodoItemPage todolistMobile;

    @Page
    @Tablet
    TodoItemPage todolistTablet;

    @Test
    @InSequence(1)
    @OperateOnDeployment("mobile-app")
    public void todolistMobileApp() {

        // Graphene does not yet support @InitialPage for non http/https locations
        // https://issues.jboss.org/browse/ARQGRA-408
        mobile.startActivity("com.tadeaskriz.devconf2014.MainActivity_");
        todolistMobile.addItem("Automatize mobile tests!");
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment("tablet-app")
    public void todolistTabletApp() {

        // Graphene does not yet support @InitialPage for non http/https locations
        // https://issues.jboss.org/browse/ARQGRA-408
        tablet.startActivity("com.tadeaskriz.devconf2014.MainActivity_");
        todolistTablet.addItem("Automatize tablet tests!");
    }

    @Test
    @InSequence(3)
    @OperateOnDeployment("mobile-app")
    public void checkMobileTodoList() throws Exception {
        Assert.assertThat("App running on mobile contains task added from tablet app",
            todolistMobile.isUnresolvedTaskSynced("Automatize tablet tests!"),
            is(true));
    }

}
