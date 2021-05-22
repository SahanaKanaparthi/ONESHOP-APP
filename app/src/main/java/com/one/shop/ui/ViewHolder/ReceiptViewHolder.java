package com.one.shop.ui.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.data.Cart;

public class ReceiptViewHolder extends RecyclerView.ViewHolder {
    public TextView cart_name, cart_price, cart_quantity;

    public ReceiptViewHolder(@NonNull View itemView) {
        super(itemView);
        cart_name = itemView.findViewById(R.id.receipt_item);
        cart_price = itemView.findViewById(R.id.receipt_price);
        cart_quantity = itemView.findViewById(R.id.receipt_quantity);

    }

    public void bind(Cart cart) {
        Log.e("CartActivity", cart.toString());
        cart_name.setText(cart.getName());
        cart_price.setText("$" + cart.getTotal());
        cart_quantity.setText(cart.getQuantity());
    }
}
