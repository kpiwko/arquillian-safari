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

import org.jboss.aerogear.todo.R;
import org.jboss.aerogear.todo.ToDoApplication;
import org.jboss.aerogear.todo.callback.RegisterCallback;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class RegisterActivity extends SherlockFragmentActivity {

	protected static final String TAG = RegisterActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void submit(View submitButton) {
		if (valid()) {
			String firstName = text(R.id.first_name_field);
			String lastName = text(R.id.last_name_field);
			String emailAddress = text(R.id.email_field);
			String username = text(R.id.username_field);
			String password = text(R.id.password_field);
			String role = ((Spinner) findViewById(R.id.role_spinner))
					.getSelectedItem().toString();

			((ToDoApplication) getApplication()).enroll(this, firstName, lastName,
					emailAddress, username, password, role,
					new RegisterCallback());

		}
	}

	private String text(int fieldId) {
		return ((EditText) findViewById(fieldId)).getText().toString();
	}

	private boolean valid() {
		String firstName = text(R.id.first_name_field);
		if (firstName.equals("")) {
			setError(R.id.first_name_field);
			return false;
		}

		String lastName = text(R.id.last_name_field);
		if (lastName.equals("")) {
			setError(R.id.last_name_field);
			return false;
		}

		String emailAddress = text(R.id.email_field);
		if (emailAddress.equals("")) {
			setError(R.id.email_field);
			return false;
		}

		String username = text(R.id.username_field);
		if (username.equals("")) {
			setError(R.id.username_field);
			return false;
		}

		String password = text(R.id.password_field);
		if (password.equals("")) {
			setError(R.id.password_field);
			return false;
		}

		return true;
	}

	private void setError(int fieldId) {
		((EditText) findViewById(fieldId)).setError("This is required");
	}

	public void cancel(View cancelButton) {
		finish();
	}

}
