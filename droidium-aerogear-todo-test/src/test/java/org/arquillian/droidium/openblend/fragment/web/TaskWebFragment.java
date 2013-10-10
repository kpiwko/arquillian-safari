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
package org.arquillian.droidium.openblend.fragment.web;

import static org.arquillian.droidium.openblend.utils.Utils.FAST;
import static org.arquillian.droidium.openblend.utils.Utils.NORMAL;
import static org.arquillian.droidium.openblend.utils.Utils.waitUtil;

import java.util.List;

import org.arquillian.droidium.openblend.drones.Browser;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class TaskWebFragment {

    @Root
    private WebElement root;

    @Drone
    @Browser
    private WebDriver browser;

    @FindBy(css = ".add-task.task")
    private WebElement addTask;

    @FindBy(id = "task-title")
    private WebElement taskTitle;

    @FindBy(id = "task-date")
    private WebElement taskDate;

    @FindBy(id = "task-desc")
    private WebElement taskDescription;

    @FindBy(className = "submit-btn")
    private WebElement submitButton;

    @FindByJQuery("div.task:nth-child(1)")
    private WebElement addedTask;

    @FindBy(id = "task-project-select")
    private Select dropDownSelect;

    @FindBy(css = "#task-tag-column .tag-select-column")
    private List<WebElement> checkBoxes;

    public void click() {
        addTask.click();
        Graphene.waitGui(browser).until().element(addTask).attribute("style").contains("display: none;");
    }

    public void addTitle(String title) {
        taskTitle.sendKeys(title);
    }

    public void addDate(String year, String month, String day) {
        taskDate.clear();

        taskDate.sendKeys(year);

        waitUtil(FAST);

        taskDate.sendKeys(month);

        waitUtil(FAST);

        taskDate.sendKeys(day);
    }

    public void addDescription(String description) {
        taskDescription.sendKeys(description);
    }

    public void addToProject(String toProject) {
        dropDownSelect.selectByValue("1");
    }

    public void addTag(String tagName) {
        for (WebElement element : checkBoxes) {
            if (element.getText().equals(tagName)) {
                element.findElement(ByJQuery.selector("input[type=checkbox]")).click();
            }
        }
    }

    public void add() {
        waitUtil(NORMAL);

        submitButton.click();
        Graphene.waitGui(browser).until().element(addedTask).is().present();

        waitUtil(NORMAL);
    }

    public AddedTask getAddedTask() {
        return new AddedTask(addedTask);
    }

}
