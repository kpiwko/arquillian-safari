package org.arquillian.droidium.openblend.fragment.mobile;

import org.openqa.selenium.support.FindBy;

public class TodoPageMobile {

    @FindBy(id = "todo")
    private TaskMobileFragment taskMobileFragment;

    public void addTask(String taskName, String date, String description) {
        taskMobileFragment.addTask(taskName, date, description);
    }
}
