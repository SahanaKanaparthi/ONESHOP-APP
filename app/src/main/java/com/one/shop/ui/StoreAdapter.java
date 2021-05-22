package com.one.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.ui.ViewHolder.StoreViewHolder;
import com.one.shop.data.Store;

import java.util.List;

class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    private List<Store> products;
    private Context context;
    private SharedPreferences preferences;

    StoreAdapter(List<Store> products, Context context) {
        this.products = products;
        this.context = context;
        preferences = context.getSharedPreferences("OneShop", Context.MODE_PRIVATE);
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stores_item, parent, false);
        StoreViewHolder storeViewHolder = new StoreViewHolder(view);
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder storeViewHolder, final int position) {
        storeViewHolder.bind(products.get(position), context);
        storeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("store_name", products.get(position).getName());
                editor.putString("store_image", products.get(position).getImage());
                editor.commit();
                context.startActivity(new
                        Intent(context, ProductsActivity.class));
            }


        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}