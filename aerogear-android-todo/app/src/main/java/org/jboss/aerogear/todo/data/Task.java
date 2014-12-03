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

package org.jboss.aerogear.todo.data;

import org.jboss.aerogear.android.RecordId;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Task implements Parcelable {

	@RecordId
	private String id;
	private String title;
	private String date;
	private String description;

	public Task() {
	}

	public Task(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		Task other = (Task) o;
		if (title == null)
			return other.getTitle() == null;
		return title.equals(other.getTitle());
	}

	@Override
	public int hashCode() {
		return (title == null) ? 0 : title.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(title);
		parcel.writeString(date);
		parcel.writeString(description);
	}

	public static final Creator<Task> CREATOR = new Creator<Task>() {
		@Override
		public Task createFromParcel(Parcel parcel) {
			Task task = new Task();
			task.id = parcel.readString();
			task.title = parcel.readString();
			task.date = parcel.readString();
			task.description = parcel.readString();
			return task;
		}

		@Override
		public Task[] newArray(int size) {
			return new Task[size];
		}
	};
}
