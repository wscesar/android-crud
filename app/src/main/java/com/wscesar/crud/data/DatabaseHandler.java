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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;
    public static final int DB_VERSION = 5;
    public static final String DB_NAME = "mydb";
    public static final String TABLE_NAME = "tbl_products";

    public static final String COL_ID = "id";
    public static final String COL_PRODUCT = "product";
    public static final String COL_PRICE = "price";
    public static final String COL_DATE_ADDED = "date_added";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_PRODUCT + " TEXT,"
                + COL_PRICE + " FLOAT,"
                + COL_DATE_ADDED + " LONG);";

        db.execSQL(sql);
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
        values.put(COL_PRODUCT, item.getItemName());
        values.put(COL_PRICE, item.getPrice());
        values.put(COL_DATE_ADDED, java.lang.System.currentTimeMillis());// timestamp of the system

        // Inset the row
        db.insert(TABLE_NAME, null, values);
        Log.d("DBHandler", "added Item: ");
    }

    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        String[] columns = new String[] { COL_ID, COL_PRODUCT, COL_PRICE, COL_DATE_ADDED };

        Cursor cursor = db.query(
                            TABLE_NAME, columns,
                            null, null, null, null,
                            COL_DATE_ADDED + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(COL_PRODUCT)));
                item.setPrice(cursor.getFloat(cursor.getColumnIndex(COL_PRICE)));

                // convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate =
                    dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_ADDED))).getTime()); // Feb 23, 2020
                item.setDateItemAdded(formattedDate);

                NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                String formattedNumber =
                    numberFormat.format(cursor.getInt(cursor.getColumnIndex(COL_PRICE)));
                    item.setCurrency(formattedNumber);

                // Add to arraylist
                itemList.add(item);

            } while (cursor.moveToNext());
        }

        return itemList;

    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT, item.getItemName());
        values.put(COL_PRICE, item.getPrice());
        values.put(COL_DATE_ADDED, java.lang.System.currentTimeMillis()); // timestamp of the system

        return db.update(TABLE_NAME, values, COL_ID + "=?", new String[] { String.valueOf(item.getId()) });

    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }

    /*
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] { COL_ID, COL_ITEM, COL_PRICE, COL_DATE_ADDED };

        Cursor cursor = db.query(TABLE_NAME, columns, COL_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(COL_ITEM)));
            item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(COL_PRICE)));

            // convert Timestamp to something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat
                    .format(new Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_ADDED))).getTime()); // Feb 23, 2020

            item.setDateItemAdded(formattedDate);

        }

        return item;
    }
    */
}
