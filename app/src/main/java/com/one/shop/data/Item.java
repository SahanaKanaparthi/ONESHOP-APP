package com.one.shop.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_COST_PER_ITEM = "costperitem";
    public static final String COLUMN_EACH = "each";
    public static final String COLUMN_ITEM_TYPE = "type";
    public static final String COLUMN_IMAGE = "image";


    public static final String VEGETABLES = "Vegetables";
    public static final String BEVERAGES = "Beverages";

    private String name;
    private String cost;
    private String costperitem;
    private String each;
    private String type;
    private String image;

    protected Item(Parcel in) {
        name = in.readString();
        cost = in.readString();
        costperitem = in.readString();
        each = in.readString();
        type = in.readString();
        image = in.readString();
        id = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //create table SQl query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COST + " TEXT,"
            + COLUMN_COST_PER_ITEM + " TEXT,"
            + COLUMN_ITEM_TYPE + " TEXT,"
            + COLUMN_EACH + " TEXT,"
            + COLUMN_IMAGE + " TEXT"
            + ")";


    public Item() {
    }

    public Item(String name, String cost, String costperitem, String each, String type, String image) {
        this.name = name;
        this.cost = cost;
        this.costperitem = costperitem;
        this.each = each;
        this.type = type;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCostperitem() {
        return costperitem;
    }

    public void setCostperitem(String costperitem) {
        this.costperitem = costperitem;
    }

    public String getEach() {
        return each;
    }

    public void setEach(String each) {
        this.each = each;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cost);
        dest.writeString(costperitem);
        dest.writeString(each);
        dest.writeString(type);
        dest.writeString(image);
        dest.writeInt(id);
    }
}
