package com.one.shop.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.ui.ViewHolder.ReceiptViewHolder;
import com.one.shop.data.Cart;

import java.util.List;

class ReceiptAdapter extends RecyclerView.Adapter<ReceiptViewHolder> {
    private List<Cart> cartItems;

    ReceiptAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
    }


    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_item, parent, false);
        ReceiptViewHolder receiptViewHolder = new ReceiptViewHolder(view);
        return receiptViewHolder;
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder receiptViewHolder, final int position) {
        receiptViewHolder.bind(cartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}