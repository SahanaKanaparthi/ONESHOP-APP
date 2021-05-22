package com.one.shop.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {

    public static final String TABLE_NAME = "CartActivity";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_ITEM = "itemid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_COST_PER_ITEM = "costperitem";
    public static final String COLUMN_EACH = "each";
    public static final String COLUMN_ITEM_TYPE = "type";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL = "total";

    private String name;
    private String cost;
    private String costperitem;
    private String each;
    private String type;
    private String quantity;
    private String total;
    private int id;
    private int item_id;


    protected Cart(Parcel in) {
        name = in.readString();
        cost = in.readString();
        costperitem = in.readString();
        each = in.readString();
        type = in.readString();
        quantity = in.readString();
        total = in.readString();
        id = in.readInt();
        item_id = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //create table SQl query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID_ITEM + " INTEGER,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COST + " TEXT,"
            + COLUMN_COST_PER_ITEM + " TEXT,"
            + COLUMN_ITEM_TYPE + " TEXT,"
            + COLUMN_EACH + " TEXT,"
            + COLUMN_QUANTITY + " TEXT,"
            + COLUMN_TOTAL + " TEXT"
            + ")";


    public Cart() {
    }

    public Cart(String name, String cost, String costperitem,
                String each, String type, String quantity, String total, int itemid) {
        this.name = name;
        this.cost = cost;
        this.costperitem = costperitem;
        this.each = each;
        this.type = type;
        this.total = total;
        this.quantity = quantity;
        this.item_id = itemid;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
        dest.writeString(quantity);
        dest.writeString(total);
        dest.writeInt(id);
        dest.writeInt(item_id);
    }
}
