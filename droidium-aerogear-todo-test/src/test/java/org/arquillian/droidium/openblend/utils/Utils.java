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
package org.arquillian.droidium.openblend.utils;

import java.net.URL;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class Utils {

    // in milliseconds

    public static final int SLOW = 10000;

    public static final int NORMAL = 3000;

    public static final int FAST = 1000;

    public static final String WEB_CONTEXT = "/todo";

    public static void openWebPageUrl(WebDriver browser, URL context) {
        try {
            browser.get(context.toExternalForm() + WEB_CONTEXT);
        } catch (final Exception ignore) {
            ignore.printStackTrace();
        }
    }

    // for presentation purposes so people have chance to follow what is happening

    public static void waitUtil(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
