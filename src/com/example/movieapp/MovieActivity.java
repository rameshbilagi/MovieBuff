package com.example.movieapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

public class MovieActivity extends Activity {
	// Log tag
	private static final String TAG = MovieActivity.class.getSimpleName();

	private ProgressDialog pDialog;
	TextView title_txt, year, genere, rating, plot;
	NetworkImageView thumbNail;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_list);

		title_txt = (TextView) findViewById(R.id.title);
		year = (TextView) findViewById(R.id.releaseYear);
		genere = (TextView) findViewById(R.id.genre);
		rating = (TextView) findViewById(R.id.rating);
		plot = (TextView) findViewById(R.id.plot);
		thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);

		Intent i = getIntent();
		final String title = i.getStringExtra("title");
		String title_final = title.replaceAll(" ", "+");
		final String url = "http://www.omdbapi.com/?t=" + title_final
				+ "&y=&plot=short&r=json";

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));

		// Creating volley request obj
		JsonObjectRequest movieReq = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						try {
							String response_str = response
									.getString("Response");
							if (response_str.equalsIgnoreCase("False")) {
								Toast.makeText(getApplicationContext(),
										"Movie Not Found", Toast.LENGTH_SHORT)
										.show();
								finish();
							}
						} catch (Exception e) {

						}

						// Parsing json
						try {

							// JSONObject obj = response;
							title_txt.setText(response.getString("Title"));
							thumbNail.setImageUrl(response.getString("Poster"),
									imageLoader);
							rating.setText(response.getString("Rated"));
							genere.setText(response.getString("Genre"));
							year.setText(response.getString("Released"));
							plot.setText(response.getString("Plot"));

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}