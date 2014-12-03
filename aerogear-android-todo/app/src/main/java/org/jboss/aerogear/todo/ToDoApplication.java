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

package org.jboss.aerogear.todo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.authentication.AuthenticationConfig;
import org.jboss.aerogear.android.authentication.AuthenticationModule;
import org.jboss.aerogear.android.authentication.impl.Authenticator;
import org.jboss.aerogear.android.http.HeaderAndBody;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.todo.data.Project;
import org.jboss.aerogear.todo.data.Tag;
import org.jboss.aerogear.todo.data.Task;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

public class ToDoApplication extends Application {
	private Pipeline pipeline;
	private Authenticator auth;


	@Override
	public void onCreate() {
		super.onCreate();


		try {

            URL baseURL = new URL("http://todo-aerogear.rhcloud.com/todo-server");

            // Set up Authenticator
			auth = new Authenticator(baseURL);
			AuthenticationConfig config = new AuthenticationConfig();
			config.setEnrollEndpoint("/auth/register");

			AuthenticationModule authModule = auth.auth("login", config);

			// Set up Pipeline
			pipeline = new Pipeline(baseURL);

			PipeConfig pipeConfigTask = new PipeConfig(baseURL, Task.class);
			pipeConfigTask.setName("tasks");
			pipeConfigTask.setEndpoint("tasks");
			pipeConfigTask.setAuthModule(authModule);
			pipeline.pipe(Task.class, pipeConfigTask);

			PipeConfig pipeConfigTag = new PipeConfig(baseURL, Tag.class);
			pipeConfigTag.setName("tags");
			pipeConfigTag.setEndpoint("tags");
			pipeConfigTag.setAuthModule(authModule);
			pipeline.pipe(Tag.class, pipeConfigTag);

			PipeConfig pipeConfigProject = new PipeConfig(baseURL, Project.class);
			pipeConfigProject.setName("projects");
			pipeConfigProject.setEndpoint("projects");
			pipeConfigProject.setAuthModule(authModule);
			pipeline.pipe(Project.class, pipeConfigProject);

		} catch (MalformedURLException e) {
			// TODO Logger?
		}

	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void login(FragmentActivity activity, String username, String password,
			Callback<HeaderAndBody> callback) {
		auth.get("login", activity).login(username, password, callback);

	}

	public void logout(FragmentActivity activity, Callback<Void> callback) {
		auth.get("login", activity).logout(callback);
	}

	public void enroll(FragmentActivity activity, String firstName, String lastName, String emailAddress,
			String username, String password, String role,
			Callback<HeaderAndBody> callback) {

		HashMap<String, String> userData = new HashMap<String, String>();
		userData.put("firstname", firstName);
		userData.put("lastname", lastName);
		userData.put("email", emailAddress);
		userData.put("username", username);
		userData.put("password", password);
		userData.put("role", role);

		auth.get("login", activity).enroll(userData, callback);
	}

}
