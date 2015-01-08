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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.By;
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

    @FindByJQuery("div.task div.task-title")
    private List<WebElement> tasks;

    @FindBy(id = "task-project-select")
    private Select dropDownSelect;

    public void addTask(String project, String title, String year, String month, String day, String description) {

        addTask.click();
        Graphene.waitGui().until().element(addTask).attribute("style").contains("display: none;");

        dropDownSelect.selectByVisibleText(project);

        taskTitle.sendKeys(title);

        taskDate.clear();
        taskDate.sendKeys(year);
        taskDate.sendKeys(month);
        taskDate.sendKeys(day);

        taskDescription.sendKeys(description);

        submitButton.click();
        Graphene.waitGui().until().element(addedTask).is().present();
        assertThat(currentTask().getTitle(), is(title));
        assertThat(currentTask().getDescription(), is(description));
    }

    public int totalTasks() {
        return tasks.size();
    }

    private AddedTask currentTask() {
        return new AddedTask(addedTask);
    }

    private static class AddedTask {

        private final WebElement addedTask;

        public AddedTask(WebElement addedTask) {
            this.addedTask = addedTask;
        }

        /*
         * public String getDate() {
         * return addedTask.findElement(By.cssSelector(".task-upper .task-date")).getText();
         * }
         */

        public String getTitle() {
            return addedTask.findElement(By.cssSelector(".task-upper .task-title")).getText();
        }

        public String getDescription() {
            return addedTask.findElement(By.cssSelector(".task-desc")).getText();
        }

    }

}
