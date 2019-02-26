/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.Earthquake;
import adapters.EarthquakeAdapter;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
	private EarthquakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        Earthquake currentEarthquake = adapter.getItem(position);
		        Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
		        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
		        startActivity(websiteIntent);
	        }
        });

	    EarthquakeAsyncTask task = new EarthquakeAsyncTask();
	    task.execute(USGS_REQUEST_URL);

    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List> {

	    @Override
	    protected List<Earthquake> doInBackground(String... urls) {
		    if(urls.length<1 || urls[0]==null)
		    	return null;

		    List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
		    Log.d("Fetchovano je", Integer.toString(result.size()));
		    return result;
	    }

	    protected void onPostExecute(List data){
		adapter.clear();
		if (data!=null && !data.isEmpty())
			adapter.addAll(data);

	    }
    }
}