/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
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
package org.jboss.aerogear.todo.activities;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.todo.R;
import org.jboss.aerogear.todo.ToDoApplication;
import org.jboss.aerogear.todo.callback.LogoutCallback;
import org.jboss.aerogear.todo.data.Project;
import org.jboss.aerogear.todo.data.Tag;
import org.jboss.aerogear.todo.data.Task;
import org.jboss.aerogear.todo.fragments.ProjectFormFragment;
import org.jboss.aerogear.todo.fragments.ProjectListFragment;
import org.jboss.aerogear.todo.fragments.TagFormFragment;
import org.jboss.aerogear.todo.fragments.TagListFragment;
import org.jboss.aerogear.todo.fragments.TaskFormFragment;
import org.jboss.aerogear.todo.fragments.TaskListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class TodoActivity extends SherlockFragmentActivity {
	private FragmentTransaction fragmentTransaction;

	public enum Lists {TASK, TAG, PROJECT};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo);

		if (savedInstanceState == null) {

			fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();

			if (isTablet()) {
				fragmentTransaction.replace(R.id.project,
						new ProjectListFragment()).replace(R.id.tag,
						new TagListFragment()).replace(R.id.task,
						new TaskListFragment());
			} else {
				fragmentTransaction.replace(R.id.todo, new TaskListFragment());
			}

			fragmentTransaction.commit();

		}

	}

	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if (!isTablet()) {
			getSupportMenuInflater().inflate(R.menu.todo, menu);
		} else {
			getSupportMenuInflater().inflate(R.menu.logout, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_project :
				showList(Lists.PROJECT);
				break;
			case R.id.menu_item_tag :
				showList(Lists.TAG);
				break;
			case R.id.menu_item_task :
				showList(Lists.TASK);
				break;
			case R.id.menu_logout :
				((ToDoApplication) getApplication())
						.logout(this, new LogoutCallback());
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void showProjectForm() {
		transaction(R.id.project, new ProjectFormFragment());
	}

	public void showProjectForm(Project project) {
		transaction(R.id.project, new ProjectFormFragment(project));
	}

	public void showList(Lists list) {
		switch (list) {
		case PROJECT:
			transaction(R.id.project, new ProjectListFragment());
			break;
		case TASK:
			transaction(R.id.task, new TaskListFragment());
			break;
		case TAG:
			transaction(R.id.tag, new TagListFragment());
			break;

		default:
			break;
		}
	}

	public void showTagForm() {
		transaction(R.id.tag, new TagFormFragment());
	}

	public void showTagForm(Tag tag) {
		transaction(R.id.tag, new TagFormFragment(tag));
	}

	public void showTaskForm() {
		transaction(R.id.task, new TaskFormFragment());
	}

	public void showTaskForm(Task task) {
		transaction(R.id.task, new TaskFormFragment(task));
	}


	private void transaction(int tabletFragmentId, Fragment fragment) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right, android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		if (isTablet()) {
			fragmentTransaction.replace(tabletFragmentId, fragment);
		} else {
			fragmentTransaction.replace(R.id.todo, fragment);
		}
		fragmentTransaction.commit();
	}
}
