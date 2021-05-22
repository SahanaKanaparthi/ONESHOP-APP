package com.one.shop.ui.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.one.shop.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView cart_name, cart_price, cart_quantity;
    public ElegantNumberButton cart_number_button;
    public ImageView btn_del_cart;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cart_name = itemView.findViewById(R.id.cart_item_name);
        cart_price = itemView.findViewById(R.id.cart_item_price);
        cart_quantity = itemView.findViewById(R.id.cart_item_quantity);
        cart_number_button = itemView.findViewById(R.id.cart_number_button);
        btn_del_cart = itemView.findViewById(R.id.btn_del_cart);


    }
}
