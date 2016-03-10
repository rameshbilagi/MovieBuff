package com.example.movieapp;

import android.os.Bundle;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button search;
	EditText title;
	//public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String Title = "title_movie";
	//SharedPreferences sharedpreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		search = (Button) findViewById(R.id.btnSearch);
		//sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		title = (EditText) findViewById(R.id.movieTitle);
		
		
		
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/*String titleShare = title.getText().toString();
				SharedPreferences.Editor editor = sharedpreferences.edit();
				editor.putString("Title", titleShare);
				editor.commit();
				String val = sharedpreferences.getString("titleShare", null);
				//Retrieving sharedpref data
				//((SharedPreferences) editor).getString("Title", null);*/
				
				if (title.getText().toString().length() == 0){
					Toast.makeText(getApplicationContext(), "Enter the movie name", Toast.LENGTH_SHORT).show();
				}
				else
				{
				Intent i = new Intent(MainActivity.this, MovieActivity.class);
				i.putExtra("title", title.getText().toString());
				startActivity(i);
			}
			}
			});
		}	
	}

