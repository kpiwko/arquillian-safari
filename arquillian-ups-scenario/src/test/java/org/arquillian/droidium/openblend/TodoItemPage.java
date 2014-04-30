package org.arquillian.droidium.openblend;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("")
public class TodoItemPage {

    @FindBy(id = "action_add")
    private WebElement addItemButton;

    @FindBy(id = "text")
    private WebElement inputField;

    @FindBy(id = "add")
    private WebElement itemAdd;

    @FindBy(id = "unfinishedTasks")
    private WebElement unfinishedTasksTable;

    @FindBy(id = "finishedTasks")
    private WebElement finishedTasksTable;

    public void addItem(final String task) {

        addItemButton.click();

        Graphene.waitGui().until().element(inputField).is().visible();
        inputField.sendKeys(task);
        itemAdd.click();

        isUnresolvedTaskSynced(task);
    }

    // hacking way how to wait until this is synced
    public boolean isUnresolvedTaskSynced(final String task) {
        try {
            Graphene.waitGui().until().element(unfinishedTasksTable, By.partialLinkText(task)).is().visible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
