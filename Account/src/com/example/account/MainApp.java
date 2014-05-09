package com.example.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainApp extends ActionBarActivity {
	private String[] itemMenu = {"Add","View","Help","Exit"};
	
	CalendarView calendar;
	String msg = "";

	/*
	 * Component
	 */
	RadioGroup groupType;
	EditText txtName;
	EditText txtDetail;
	EditText txtValue;

	public void findByIdComponent() {
		groupType = (RadioGroup) findViewById(R.id.radioGroupType);
		txtName = (EditText) findViewById(R.id.txt_name);
		txtDetail = (EditText) findViewById(R.id.txt_detail);
		txtValue = (EditText) findViewById(R.id.txt_value);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		findByIdComponent();
		calendar = (CalendarView) findViewById(R.id.calendar);
		calendar.setOnDateChangeListener(this.calendarListener);	
		registerForContextMenu(calendar);
		dialogForm();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo){
			AdapterView.AdapterContextMenuInfo info = 
					(AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(itemMenu[info.position]);
			for (int i = 0; i < itemMenu.length; i++) {
				menu.add(Menu.NONE,i,i,itemMenu[i]);
			}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		return true;
	}
	public OnDateChangeListener calendarListener = new OnDateChangeListener() {

		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			PopupMenu popup = new PopupMenu(MainApp.this, view);
			popup.setOnMenuItemClickListener(itemClick);

			MenuInflater inflater = popup.getMenuInflater();
			inflater.inflate(R.menu.popup_menu, popup.getMenu());

			popup.show();

			/*
			 * Toast.makeText(MainApp.this, "day : "+dayOfMonth+"\n"
			 * +"month: "+month+"\n" +"year: "+year, Toast.LENGTH_LONG).show();
			 */
		}
	};
	private OnMenuItemClickListener itemClick = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_add:
				// Toast.makeText(MainApp.this, "action_add",
				// Toast.LENGTH_SHORT).show();
				dialogForm().show();
				return true;
			case R.id.action_view:
				Toast.makeText(MainApp.this, "action_view", Toast.LENGTH_SHORT)
						.show();
				return true;
			default:
				return false;
			}
		}
	};

	private AlertDialog.Builder dialogForm() {
		LayoutInflater inflater = MainApp.this.getLayoutInflater();

		AlertDialog.Builder builder = new AlertDialog.Builder(MainApp.this);
		builder.setView(inflater.inflate(R.layout.activity_form, null));
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// RadioButton radio =
				// (RadioButton)findViewById(groupType.getCheckedRadioButtonId());
				// int index =
				// groupType.indexOfChild(findViewById(groupType.getCheckedRadioButtonId()));
				try {
					Toast.makeText(
							MainApp.this,
							"Type: " 
									+ "\n Name: " //+ txtName.getText()
									+ "\n Detail: " //+ txtDetail.getText()
									+ "\n Number: " //+ txtValue.getText()
							,Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(MainApp.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("CANCLE",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		return builder;
	}
}
