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

import java.util.List;

import org.jboss.aerogear.android.pipeline.support.AbstractSupportFragmentCallback;

import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ReadCallback<T> extends AbstractSupportFragmentCallback<List<T>> {

	private static final long serialVersionUID = 1L;
	private static final String TAG = ReadCallback.class.getSimpleName();

	public ReadCallback() {
		super(TAG);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(List<T> data) {
		if (getFragment() instanceof ListFragmentCallbackHelper<?>) {
			ListFragmentCallbackHelper<T> helper = (ListFragmentCallbackHelper<T>) getFragment();
			List<T> list = helper.getList();
			ArrayAdapter<T> adapter = helper.getAdapter();
			list.clear();
			list.addAll(data);
			adapter.notifyDataSetChanged();
			
		} else {
			throw new IllegalStateException("An incorrect Fragment was passed.");
		}
	}

	@Override
	public void onFailure(Exception e) {
		Toast.makeText(getFragment().getActivity(),
				"Error refreshing data: " + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

}
