package com.one.shop.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.ui.ViewHolder.ProductViewHolder;
import com.one.shop.data.Item;

import java.util.List;

class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Item> products;
    private Context context;

    ProductAdapter(List<Item> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder, int position) {
        productViewHolder.bind(products.get(position), context);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}