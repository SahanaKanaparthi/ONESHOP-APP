package com.one.shop.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.one.shop.R;
import com.one.shop.ui.ViewHolder.CartViewHolder;
import com.google.android.material.snackbar.Snackbar;
import com.one.shop.data.Cart;
import com.one.shop.data.DatabaseHelper;

import java.util.List;

class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<Cart> cartItems;
    private Context context;
    private int noOfItems = 0;
    private Double total = 0.0;
    DatabaseHelper helper;
    private CartActivity cart;

    CartAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
        helper = new DatabaseHelper(context);
        this.cart = (CartActivity) context;
    }

    public void removeItem(Cart cartItem) {
        cartItems.remove(cartItem);
        cart.getTotalPrice(cartItems);
        notifyDataSetChanged();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder cartViewHolder, final int position) {
        Log.e("roundOff", String.format("%.2f", Double.valueOf(cartItems.get(position).getTotal())));
        cartViewHolder.cart_name.setText(cartItems.get(position).getName());
        cartViewHolder.cart_price.setText("$" +
                String.format("%.2f", Double.valueOf(cartItems.get(position).getTotal()))
        );
        cartViewHolder.cart_quantity.setText(cartItems.get(position).getQuantity());
        cartViewHolder.cart_number_button.setNumber(cartItems.get(position).getQuantity());

        cartViewHolder.cart_number_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.e("oldvalue", oldValue + "");
                Log.e("newValue", newValue + "");

                if (newValue == 0) {
                    DatabaseHelper helper = new DatabaseHelper(context);
                    helper.deleteCartItem(cartItems.get(position));
                    removeItem(cartItems.get(position));
                } else if (newValue > 0) {

                    noOfItems = newValue;
                    total = noOfItems * Double.valueOf(cartItems.get(position).getCost());
                    helper.updateCostAndQuantity(String.valueOf(noOfItems),
                            String.valueOf(total), String.valueOf(cartItems.get(position).getId()));
                    cartViewHolder.cart_quantity.setText(String.valueOf(noOfItems));
                    cartViewHolder.cart_price.setText(
                            String.format("%.2f", Double.valueOf(total))
                    );
                    cartItems.get(position).setTotal(String.format("%.2f", Double.valueOf(total)));
                    cart.getTotalPrice(cartItems);
                    Log.e("RoundOff", String.format("%.2f", Double.valueOf(total)));
                }

            }
        });

        cartViewHolder.btn_del_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("This item will be deleted from your CartActivity.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Snackbar.make(view, " Removed From Cart", Snackbar.LENGTH_LONG).show();

                                DatabaseHelper helper = new DatabaseHelper(context);
                                helper.deleteCartItem(cartItems.get(position));
                                removeItem(cartItems.get(position));
                            }
                        });
                builder.setTitle("Remove From Cart?");
                AlertDialog alert = builder.create();
                alert.setCancelable(true);
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}