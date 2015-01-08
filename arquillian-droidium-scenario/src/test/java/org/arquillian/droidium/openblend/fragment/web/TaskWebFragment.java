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

import java.util.List;

import org.arquillian.droidium.openblend.drones.Browser;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Graphene page fragment for adding a task into todo list.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class TaskWebFragment {

    @Root
    private WebElement root;

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
        Graphene.waitGui().until().element(addTask).attribute("style").contains("display: none;");
    }

    public void addToProject(String toProject) {
        dropDownSelect.selectByValue("1");
    }

    public AddedTask getAddedTask() {
        return new AddedTask(addedTask);
    }

    public void addTask(String project, String title, String year, String month, String day, String description) {

        click();

        addToProject(project);

        taskTitle.sendKeys(title);

        taskDate.clear();
        taskDate.sendKeys(year);
        taskDate.sendKeys(month);
        taskDate.sendKeys(day);

        taskDescription.sendKeys(description);

        submitButton.click();
        Graphene.waitGui().until().element(addedTask).is().present();
    }

}
