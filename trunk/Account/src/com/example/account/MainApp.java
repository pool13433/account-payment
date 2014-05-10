package com.example.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

public class MainApp extends ActionBarActivity {
	private String[] itemMenu = { "Add", "View", "Help", "Exit" };
	ListView list_tran;
	MyDbHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	/*
	 * Component
	 */
	CalendarView calendar;
	RadioGroup groupType;
	EditText txtName;
	EditText txtDetail;
	EditText txtValue;

	public void findByIdComponent() {
		groupType = (RadioGroup) findViewById(R.id.radioGroupType);
		txtName = (EditText) findViewById(R.id.txt_name);
		txtDetail = (EditText) findViewById(R.id.txt_detail);
		txtValue = (EditText) findViewById(R.id.txt_value);

		list_tran = (ListView) findViewById(R.id.list_transaction);
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

		dbHelper = new MyDbHelper(this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(itemMenu[info.position]);
		for (int i = 0; i < itemMenu.length; i++) {
			menu.add(Menu.NONE, i, i, itemMenu[i]);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		return true;
	}

	public OnDateChangeListener calendarListener = new OnDateChangeListener() {

		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			/*PopupMenu popup = new PopupMenu(MainApp.this, view);
			popup.setOnMenuItemClickListener(itemClick);

			MenuInflater inflater = popup.getMenuInflater();
			inflater.inflate(R.menu.popup_menu, popup.getMenu());

			popup.show();*/
			
			dialogMenu().show();

			/*
			 * Toast.makeText(MainApp.this, "day : "+dayOfMonth+"\n"
			 * +"month: "+month+"\n" +"year: "+year, Toast.LENGTH_LONG).show();
			 */
		}
	};
	private AlertDialog.Builder dialogMenu(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Function");
		builder.setItems(itemMenu, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (itemMenu[which]) {
				case "Add":		
					dialogForm().show();
					break;
				case "View":
					dialogListItem().show();
					break;
				case "Help":					
					break;
				case "Exit":					
					break;
				default:
					System.out.println("default==>>");
					break;
				}
			}
		});
		return builder;
	}
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
				/*
				 * Toast.makeText(MainApp.this, "action_view",
				 * Toast.LENGTH_SHORT) .show();
				 */
				loadListItem();
				dialogListItem().show();
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
					Toast.makeText(MainApp.this, "Type: " + "\n Name: " // +
																		// txtName.getText()
							+ "\n Detail: " // + txtDetail.getText()
							+ "\n Number: " // + txtValue.getText()
					, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(MainApp.this, e.toString(),
							Toast.LENGTH_LONG).show();
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

	private AlertDialog.Builder dialogListItem() {

		LayoutInflater inflater = MainApp.this.getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(MainApp.this);
		builder.setView(inflater.inflate(R.layout.list_transaction, null));
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		builder.setNegativeButton("CANCLE",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		return builder;
	}

	private Account getValue() {
		Account account = new Account();

		return account;
	}

	private void loadListItem() {
		// ชื่อ field ที่จะ query ข้อมูล
		/*
		 * String[] queryColumnRev = new String[] {"_id",
		 * MyDbHelper.COL_TRAN_NAME, MyDbHelper.COL_TRAN_DATE,
		 * MyDbHelper.COL_TRAN_DETAIL, MyDbHelper.COL_TRAN_TYPE,
		 * MyDbHelper.COL_TRAN_VALUE}; cursor =
		 * db.query(MyDbHelper.TABLE_TRANSAC, queryColumnRev,
		 * null,null,null,null,null);
		 * 
		 * String[] showColumnRev = new String[] { MyDbHelper.COL_TRAN_NAME,
		 * MyDbHelper.COL_TRAN_TYPE, MyDbHelper.COL_TRAN_DETAIL,
		 * MyDbHelper.COL_TRAN_DATE, MyDbHelper.COL_TRAN_VALUE };
		 * 
		 * int[] viewsRev = new int[] { android.R.id.text1, android.R.id.text2
		 * };
		 * 
		 * this.adapter = new SimpleCursorAdapter(this, R.layout.listitem_data,
		 * cursor, showColumnRev, viewsRev); list_tran.setAdapter(adapter);
		 */

		String sql = "SELECT * FROM transac";

		cursor = db.rawQuery(sql, null);

		ArrayList data = new ArrayList();		
		int item = android.R.layout.simple_list_item_1;
		while (cursor.moveToNext()) {
			
			String name = cursor.getString(cursor
					.getColumnIndex(MyDbHelper.COL_TRAN_NAME));
			String detail = cursor.getString(cursor
					.getColumnIndex(MyDbHelper.COL_TRAN_DETAIL));
			String date = cursor.getString(cursor
					.getColumnIndex(MyDbHelper.COL_TRAN_DATE));
			String type = cursor.getString(cursor
					.getColumnIndex(MyDbHelper.COL_TRAN_TYPE));
			String value = cursor.getString(cursor
					.getColumnIndex(MyDbHelper.COL_TRAN_VALUE));
			
			HashMap map = new HashMap();
				map.put(MyDbHelper.COL_TRAN_NAME, name);
				map.put(MyDbHelper.COL_TRAN_DETAIL,detail);
				map.put(MyDbHelper.COL_TRAN_DATE, date);
				map.put(MyDbHelper.COL_TRAN_TYPE, type);
				map.put(MyDbHelper.COL_TRAN_VALUE, value);						
			data.add(map);
		}
		System.out.println("data.size: "+data.size());
		
		String[] column = new String[] { MyDbHelper.COL_TRAN_NAME,
				MyDbHelper.COL_TRAN_TYPE, MyDbHelper.COL_TRAN_DETAIL,
				MyDbHelper.COL_TRAN_DATE, MyDbHelper.COL_TRAN_VALUE };
		int[] value = new int[] {
				R.id.txt_result_name,R.id.txt_result_type,
				R.id.txt_result_detail,R.id.txt_result_date,
				R.id.txt_result_value
		};
		
		int itemData = R.layout.listitem_data;
		
		SimpleAdapter adapter = 
				new SimpleAdapter(this, data, itemData, column, value);
		
		list_tran.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		// เข้าถึงฐานข้อมูลเพื่ออ่านและเขียน
		db = dbHelper.getWritableDatabase();
	}

	@Override
	public void onPause() {
		super.onPause();
		cursor.close();
		db.close();
	}
}
