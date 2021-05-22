package com.one.shop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.data.DatabaseHelper;
import com.one.shop.data.Item;

import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables_list);

        String type = getIntent().getExtras().getString("type");

        this.setTitle(type);
        recyclerView = findViewById(R.id.recycler_menu_vegetables);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        fetchItems(type);
    }

    private void fetchItems(String type) {
        DatabaseHelper helper = new DatabaseHelper(ItemsActivity.this);
        List<Item> itemList = helper.getAllItems(type);
        ProductAdapter adapter = new ProductAdapter(itemList, ItemsActivity.this);
        recyclerView.setAdapter(adapter);
    }

}
