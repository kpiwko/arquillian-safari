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

package org.jboss.aerogear.todo.fragments;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.jboss.aerogear.android.pipeline.Pipe;
import org.jboss.aerogear.todo.R;
import org.jboss.aerogear.todo.ToDoApplication;
import org.jboss.aerogear.todo.activities.TodoActivity;
import org.jboss.aerogear.todo.activities.TodoActivity.Lists;
import org.jboss.aerogear.todo.callback.SaveCallback;
import org.jboss.aerogear.todo.data.Task;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class TaskFormFragment extends Fragment {

	private final Task task;

	private EditText name;
	private EditText date;
	private EditText description;

	private static Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

	public TaskFormFragment() {
		this.task = new Task();
	}

	public TaskFormFragment(Task task) {
		this.task = task;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.form_task, null);

		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(getResources().getString(R.string.tasks));

		name = (EditText) view.findViewById(R.id.name);
		date = (EditText) view.findViewById(R.id.date);
		description = (EditText) view.findViewById(R.id.description);

		if (task.getId() != null) {
			Button button = (Button) view.findViewById(R.id.buttonSave);
			button.setText(R.string.update);
		}

		name.setText(task.getTitle());
		date.setText(task.getDate());
		description.setText(task.getDescription());

		ImageView buttonCalendar = (ImageView) view
				.findViewById(R.id.buttonCalendar);
		buttonCalendar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				showTimePickerDialog(view);
			}
		});

		Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (!isValid()) {
					return;
				}

				task.setTitle(name.getText().toString());
				task.setDate(date.getText().toString());
				task.setDescription(description.getText().toString());

				Pipe<Task> pipe = ((ToDoApplication) getActivity()
						.getApplication()).getPipeline().get("tasks", TaskFormFragment.this, getActivity().getApplicationContext());
				pipe.save(task, new SaveCallback<Task>(Lists.TASK));
			}
		});

		Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((TodoActivity) getActivity()).showList(Lists.TASK);
			}
		});

		return view;

	}

	private boolean isValid() {
		boolean valid = true;
		if (name.getText().toString().length() < 1) {
			name.setError("Please enter a title");
			valid = false;
		}
		if (!pattern.matcher(date.getText().toString()).matches()) {
			date.setError("Please enter a valid date");
			valid = false;
		}
		return valid;
	}

	private void showTimePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getActivity().getSupportFragmentManager(),
				"datePicker");
	}

	private class DatePickerFragment extends SherlockDialogFragment
			implements
				DatePickerDialog.OnDateSetListener {

		private int day;
		private int month;
		private int year;

		public DatePickerFragment() {
			splitDate(date.getText().toString());
		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			this.year = year;
			this.month = month;
			this.day = day;
			setDate();
		}

		private void setDate() {
			date.setText(year + "-" + String.format("%02d", month + 1) + "-"
					+ String.format("%02d", day));
		}

		private void splitDate(String date) {
			if (pattern.matcher(date).matches()) {
				String[] data = date.split("-");
				year = Integer.valueOf(data[0]);
				month = Integer.valueOf(data[1]) - 1;
				day = Integer.valueOf(data[2]);
			} else {
				final Calendar c = Calendar.getInstance();
				day = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);
				year = c.get(Calendar.YEAR);
			}
		}

	}

}
