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

import org.arquillian.droidium.openblend.drones.Browser;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Graphene page fragment for adding a project into todo list.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ProjectFragment {

    @Root
    private WebElement root;

    @Browser
    @Drone
    private WebDriver browser;

    @FindBy(className = "add-project")
    private WebElement addProject;

    @FindBy(id = "project-title")
    private WebElement projectTitle;

    @FindBy(className = "add-project-form")
    private WebElement addProjectForm;

    @FindBy(className = "submit-btn")
    private WebElement addProjectButton;

    @FindBy(css = "#project-container .project.project-255-255-255")
    private WebElement addedProject;

    public void click() {
        addProject.click();
        Graphene.waitGui(browser).until().element(addProject).attribute("style").contains("display: none;");
    }

    public void addProject(String projectTitle) {
        click();
        this.projectTitle.sendKeys(projectTitle);
        addProjectButton.click();
        Graphene.waitGui(browser).until().element(addedProject).is().present();
    }

    public WebElement getAddedProject() {
        return addedProject;
    }

}
