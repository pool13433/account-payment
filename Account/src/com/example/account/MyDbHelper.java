package com.example.account;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "people";
	public static final String COL_NAME = "pName";
	public static final String COL_DATE = "pDate";
	
	/*
	 * database account
	 */
	public static final String TABLE_REVENUE = "revenue";
	public static final String COL_REV_ID = "rId";
	public static final String COL_REV_DATE = "rDate";
	public static final String COL_REV_NAME = "rName";
	public static final String COL_REV_DETAIL = "rDetail";
	public static final String COL_REV_VALUE = "rValue";
	
	public static final String TABLE_EXPENSES = "expenses";
	public static final String COL_EXP_ID = "eId";
	public static final String COL_EXP_DATE = "eDate";
	public static final String COL_EXP_NAME = "eName";
	public static final String COL_EXP_DETAIL = "eDetail";
	public static final String COL_EXP_VALUE = "eValue";
	
	private static final String STRING_CREATE = 
			"CREATE TABLE "+ TABLE_NAME
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME+ " TEXT ," + COL_DATE + " DATE);";
	private static final String STRING_CREATE_REV = 
			"CREATE TABLE "+TABLE_REVENUE
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_REV_NAME + "VARCHAR(50) ,"
			+ COL_REV_DATE + "DATE ,"
			+ COL_REV_DETAIL + "TEXT ,"
			+ COL_REV_VALUE + "INTEGER(10));";
	
	private static final String STRING_CREATE_EXP = 
			"CREATE TABLE "+TABLE_EXPENSES
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_EXP_NAME + " VARCHAR(50) ,"
			+ COL_EXP_DATE + "DATE ,"
			+ COL_EXP_DETAIL + "TEXT ,"
			+ COL_EXP_VALUE + "INTEGER(10));";
	
	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(STRING_CREATE);
		
		ContentValues cv = new ContentValues();
		
		cv.put(COL_NAME, "Promlert Lovichit");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		cv.put(COL_DATE, dateFormat.format(new Date()));
		
		db.insert(TABLE_NAME, null, cv);
		
		db.execSQL(STRING_CREATE_EXP);
		db.execSQL(STRING_CREATE_REV);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
			
		onCreate(db);
		
		if (oldVersion <= 1) {
			db.execSQL("ALTER TABLE "+TABLE_NAME
					+ " ADD COLUMN phone_number TEXT;");
		}
		
		if (oldVersion <=2) {
			db.execSQL("ALTER TABLE "+TABLE_NAME
					+ " ADD COLUMN email TEXT;");
		}
	}
	
}
