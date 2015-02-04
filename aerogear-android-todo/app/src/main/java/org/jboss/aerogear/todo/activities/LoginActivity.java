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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import org.jboss.aerogear.todo.callback.LoginCallback;

public class LoginActivity extends FragmentActivity {

	protected static final String TAG = LoginActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE);
		setContentView(R.layout.login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.menu_register) {
			startActivity(new Intent(getApplicationContext(),
					RegisterActivity.class));
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void login(View loginButtonButton) {
		String username = text(R.id.username_field);
		String password = text(R.id.password_field);
		ToDoApplication app = (ToDoApplication) getApplication();
		app.login(this, username, password, new LoginCallback());
	}

	private String text(int field_id) {
		EditText field = (EditText) findViewById(field_id);
		return field.getText().toString();
	}

}
