package com.wscesar.crud.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wscesar.crud.model.Item;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "mydb";
    public static final String TABLE_NAME = "tbl_items";

    public static final String ID = "id";
    public static final String ITEM = "item";
    public static final String AMOUNT = "amount";
    public static final String DATE_ADDED = "date_added";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + ITEM
                + " INTEGER," + AMOUNT + " INTEGER," + DATE_ADDED + " LONG);";

        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CRUD operations
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM, item.getItemName());
        values.put(AMOUNT, item.getItemQuantity());
        values.put(DATE_ADDED, java.lang.System.currentTimeMillis());// timestamp of the system

        // Inset the row
        db.insert(TABLE_NAME, null, values);
        Log.d("DBHandler", "added Item: ");
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { ID, ITEM, AMOUNT, DATE_ADDED }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(ITEM)));
            item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(AMOUNT)));

            // convert Timestamp to something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat
                    .format(new Date(cursor.getLong(cursor.getColumnIndex(DATE_ADDED))).getTime()); // Feb 23, 2020

            item.setDateItemAdded(formattedDate);

        }

        return item;
    }

    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[] { ID, ITEM, AMOUNT, DATE_ADDED }, null, null, null, null,
                DATE_ADDED + " DESC");

        if (cursor.moveToFirst()) {
            do {

                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(ITEM)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(AMOUNT)));

                // convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat
                        .format(new Date(cursor.getLong(cursor.getColumnIndex(DATE_ADDED))).getTime()); // Feb 23, 2020
                item.setDateItemAdded(formattedDate);

                // Add to arraylist
                itemList.add(item);

            } while (cursor.moveToNext());
        }

        return itemList;

    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM, item.getItemName());
        values.put(AMOUNT, item.getItemQuantity());
        values.put(DATE_ADDED, java.lang.System.currentTimeMillis()); // timestamp of the system

        return db.update(TABLE_NAME, values, ID + "=?", new String[] { String.valueOf(item.getId()) });

    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }

    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

}
