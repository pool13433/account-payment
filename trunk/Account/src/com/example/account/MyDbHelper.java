package com.example.account;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	private static final String typeRev = "revenue",typeExp = "expenses";
	
	public static final String TABLE_NAME = "people";
	public static final String COL_NAME = "pName";
	public static final String COL_DATE = "pDate";
	
	/*
	 * database account
	 */
	public static final String TABLE_TRANSAC = "transac";
	public static final String COL_TRAN_ID = "tId";
	public static final String COL_TRAN_DATE = "tDate";
	public static final String COL_TRAN_NAME = "tName";
	public static final String COL_TRAN_DETAIL = "tDetail";
	public static final String COL_TRAN_TYPE = "tType";
	public static final String COL_TRAN_VALUE = "tValue";
	
	private static final String STRING_CREATE = 
			"CREATE TABLE "+ TABLE_NAME
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME+ " TEXT ," + COL_DATE + " DATE);";
	
	private static final String STRING_CREATE_TRANSAC = 
			"CREATE TABLE "+ TABLE_TRANSAC
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_TRAN_NAME + " TEXT ,"
			+ COL_TRAN_DATE + " DATE ,"
			+ COL_TRAN_DETAIL + " TEXT ,"
			+ COL_TRAN_TYPE + " TEXT ,"
			+ COL_TRAN_VALUE + " TEXT );";
	
	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		System.out.println("sql STRING_CREATE_TRANSAC: "+STRING_CREATE_TRANSAC);
		System.out.println("sql STRING_CREATE: "+STRING_CREATE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			db.execSQL(STRING_CREATE);
			
			ContentValues cv = new ContentValues();		
			cv.put(COL_NAME, "Promlert Lovichit");				
			cv.put(COL_DATE, dateFormat.format(new Date()));
			
			db.insert(TABLE_NAME, null, cv);
			
			/*
			 * 
			 */
			
			db.execSQL(STRING_CREATE_TRANSAC);
			
			ContentValues cvTran = new ContentValues();
			cvTran.put(COL_TRAN_NAME, "mama");
			cvTran.put(COL_TRAN_DETAIL, "wiwi");
			cvTran.put(COL_TRAN_DATE, dateFormat.format(new Date()));
			cvTran.put(COL_TRAN_TYPE, typeRev);
			cvTran.put(COL_TRAN_VALUE, 500);
			
			db.insert(TABLE_TRANSAC, null, cvTran);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
			
		onCreate(db);
		
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSAC);
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
