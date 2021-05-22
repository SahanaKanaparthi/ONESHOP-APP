package com.one.shop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.one.shop.R;
import com.one.shop.data.Item;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public void VegMethod(View view) {
        Intent items_intent = new Intent(ProductsActivity.this, ItemsActivity.class);
        items_intent.putExtra("type", Item.VEGETABLES);
        startActivity(items_intent);
    }

    public void GroMethod(View view) {
        Intent items_intent = new Intent(ProductsActivity.this, ItemsActivity.class);
        items_intent.putExtra("type", Item.BEVERAGES);
        startActivity(items_intent);
    }
}