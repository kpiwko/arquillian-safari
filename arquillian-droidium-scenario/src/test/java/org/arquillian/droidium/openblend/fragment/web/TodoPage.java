package org.arquillian.droidium.openblend.fragment.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.support.FindBy;

public class TodoPage {

    @FindBy(id = "project-list")
    private ProjectFragment projectFragment;

    @FindBy(id = "task-container")
    private TaskWebFragment taskFragment;

    public void addProject(String projectTitle) {
        projectFragment.addProject(projectTitle);
        assertThat(projectFragment.currentProjectName(), is("groceries"));
    }

    public void addTask(String name, String date, String description) {

        final Pattern datePattern = Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
        Matcher m = datePattern.matcher(date);
        if (m.matches()) {
            taskFragment.addTask(projectFragment.currentProjectName(), name, m.group(1), m.group(2), m.group(3), description);
        }
        else {
            throw new IllegalArgumentException("Date was specified in invalid form " + date);
        }
    }

    public int totalTasks() {
        return taskFragment.totalTasks();
    }
}
