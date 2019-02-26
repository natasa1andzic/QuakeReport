package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapters.Earthquake;

public class QueryUtils {

	public static final String LOG_TAG = EarthquakeActivity.class.getName();

	private QueryUtils(){

	}

	private static URL createUrl(String stringURL) {
		URL url = null;
		try {
			url = new URL(stringURL);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Problem building the url", e);
		}
		return url;
	}

	private static String makeHttpRequest(URL url) throws IOException {
		String jsonResponse = "";

		if (url == null) {
			return jsonResponse;
		}
		HttpURLConnection urlConnection = null;
		InputStream inputStream = null;
		try {

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setReadTimeout(100000);
			urlConnection.setConnectTimeout(150000);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			if (urlConnection.getResponseCode() == 200) {
				inputStream = urlConnection.getInputStream();
				jsonResponse = readFromStream(inputStream);
			} else
				Log.e(LOG_TAG, "error response code: " + urlConnection.getResponseCode());
		} catch (IOException e) {
			Log.e(LOG_TAG, "Problem retrieving the json result", e);
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
		if (inputStream != null)
			inputStream.close();

		return jsonResponse;

	}

	private static String readFromStream(InputStream inputStream) throws IOException {
		StringBuilder output = new StringBuilder();
		if (inputStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = reader.readLine();
			while (line != null) {
				output.append(line);
				line = reader.readLine();
			}
		}
		return output.toString();
	}

	private static List<Earthquake> extractFromJson(String earthquakeJSON) {

		List<Earthquake> earthquakes = new ArrayList<>();
		try {
			JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
			JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
			for(int i=0;i<earthquakeArray.length();i++) {
				JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
				JSONObject properties = currentEarthquake.getJSONObject("properties");
				double magnitude = properties.getDouble("mag");
				String location = properties.getString("place");
				long time = properties.getLong("time");
				String url = properties.getString("url");
				Earthquake earthquake = new Earthquake(magnitude, location, time, url);
				earthquakes.add(earthquake);
				Log.d("Earthquake", Double.toString(magnitude) + location + Long.toString(time) + url);
			}

		} catch (JSONException e) {
			Log.e(LOG_TAG, "Problem parsing JSON results", e);
		}
		return earthquakes;
	}

	public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		URL url = createUrl(requestUrl);
		String jsonResponse=null;
		try {
			jsonResponse = makeHttpRequest(url);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Problem making the http request", e);
		}
		List<Earthquake> earthquakes = extractFromJson(jsonResponse);
		return earthquakes;
	}
}