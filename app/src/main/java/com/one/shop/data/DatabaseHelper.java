package com.one.shop.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "items_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Item.CREATE_TABLE);
        db.execSQL(Cart.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public long insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Item.COLUMN_NAME, item.getName());
        values.put(Item.COLUMN_COST, item.getCost());
        values.put(Item.COLUMN_COST_PER_ITEM, item.getCostperitem());
        values.put(Item.COLUMN_EACH, item.getEach());
        values.put(Item.COLUMN_ITEM_TYPE, item.getType());
        values.put(Item.COLUMN_IMAGE, item.getImage());
        long id = db.insert(Item.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public long insertItem_cart(Cart item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cart.COLUMN_NAME, item.getName());
        values.put(Cart.COLUMN_COST, item.getCost());
        values.put(Cart.COLUMN_COST_PER_ITEM, item.getCostperitem());
        values.put(Cart.COLUMN_EACH, item.getEach());
        values.put(Cart.COLUMN_ITEM_TYPE, item.getType());
        values.put(Cart.COLUMN_QUANTITY, item.getQuantity());
        values.put(Cart.COLUMN_TOTAL, item.getTotal());
        values.put(Cart.COLUMN_ID_ITEM, item.getItem_id());
        long id = db.insert(Cart.TABLE_NAME, null, values);
        db.close();
        return id;
    }


    public List<Item> getAllItems(String type) {
        List<Item> items = new ArrayList<>();
        // String selectQuery = "SELECT  * FROM " + Item.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Item.TABLE_NAME,
                new String[]{Item.COLUMN_ID, Item.COLUMN_NAME, Item.COLUMN_COST,
                        Item.COLUMN_COST_PER_ITEM,
                        Item.COLUMN_EACH, Item.COLUMN_IMAGE},
                Item.COLUMN_ITEM_TYPE + "=?",
                new String[]{type}, null, null, null, null);
        //   Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(Item.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(Item.COLUMN_NAME)));
                item.setCost(cursor.getString(cursor.getColumnIndex(Item.COLUMN_COST)));
                item.setCostperitem(cursor.getString(cursor.getColumnIndex(Item.COLUMN_COST_PER_ITEM)));
                item.setEach(cursor.getString(cursor.getColumnIndex(Item.COLUMN_EACH)));
                item.setImage(cursor.getString(cursor.getColumnIndex(Item.COLUMN_IMAGE)));
                items.add(item);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return items;
    }


    public List<Cart> getCart() {
        List<Cart> items = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Cart.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart item = new Cart();
                item.setId(cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_ID)));
                item.setCost(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_COST)));
                item.setCostperitem(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_COST_PER_ITEM)));
                item.setEach(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_EACH)));
                item.setName(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_NAME)));
                item.setQuantity(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_QUANTITY)));
                item.setTotal(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_TOTAL)));
                item.setType(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_ITEM_TYPE)));
                item.setItem_id(cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_ID_ITEM)));
                items.add(item);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return items;
    }


    public int updateCostAndQuantity(String quantity, String total, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Cart.COLUMN_QUANTITY, quantity);
        if (!total.equalsIgnoreCase("0.00")) {
            values.put(Cart.COLUMN_TOTAL, total);
        }

        // updating row
        int update = db.update(Cart.TABLE_NAME, values, Cart.COLUMN_ID + " = ?",
                new String[]{id});
        Log.e("update", update + "");
        return update;
    }

    public void deleteCartItem(Cart item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Cart.TABLE_NAME, Cart.COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deleteCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Cart.TABLE_NAME, null, null);
        db.close();
    }

    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + Item.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
