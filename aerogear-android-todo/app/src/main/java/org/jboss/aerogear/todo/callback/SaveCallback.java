/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
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
package org.jboss.aerogear.todo.callback;

import org.jboss.aerogear.android.pipeline.support.AbstractSupportFragmentCallback;
import org.jboss.aerogear.todo.activities.TodoActivity;

import android.widget.Toast;

public class SaveCallback<T> extends AbstractSupportFragmentCallback<T> {

	private static final long serialVersionUID = 1L;
	private static final String TAG = SaveCallback.class.getSimpleName();
	private final TodoActivity.Lists type;
	
	public SaveCallback(TodoActivity.Lists type) {
		super(TAG, type);
		this.type = type;
	}
	
	@Override
	public void onSuccess(T data) {
		((TodoActivity) getFragment().getActivity()).showList(type);
	}

	@Override
	public void onFailure(Exception e) {
		Toast.makeText(getFragment().getActivity(),
				String.format("Error saving %s: %s",type, e.getMessage()),
				Toast.LENGTH_LONG).show();
	}


}
