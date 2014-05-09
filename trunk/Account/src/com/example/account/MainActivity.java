package com.example.account;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	Button btnStart;
	Button btnHelp;
	Button btnAbout;
	Button btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findByIdComponent();
		dialogExit();
		
		
	}
	public OnClickListener btnStartClick = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this,MainApp.class);
			startActivity(i);
		}
	};
	public OnClickListener btnExitClick = new OnClickListener() {		
		@Override
		public void onClick(View arg0) {
			dialogExit().show();			
		}
	};
	private void findByIdComponent(){
		btnStart = (Button)findViewById(R.id.btn_start);
		btnStart.setOnClickListener(this.btnStartClick);
		
		btnHelp = (Button)findViewById(R.id.btn_help);
		
		btnAbout = (Button)findViewById(R.id.btn_about);
		
		btnExit = (Button)findViewById(R.id.btn_exit);
		btnExit.setOnClickListener(this.btnExitClick);
	}
	
	private AlertDialog.Builder dialogExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Confirm Exit");
		builder.setMessage("Confirm the Exit Program ?");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
			    intent.addCategory(Intent.CATEGORY_HOME);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    startActivity(intent);
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			    // logic
			}
		});
		return builder;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
