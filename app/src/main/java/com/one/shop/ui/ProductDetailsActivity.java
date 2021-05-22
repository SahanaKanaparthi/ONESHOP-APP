package com.one.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.one.shop.R;
import com.one.shop.data.Cart;
import com.one.shop.data.DatabaseHelper;
import com.one.shop.data.Item;

import java.util.List;

import info.hoang8f.widget.FButton;

public class ProductDetailsActivity extends AppCompatActivity {


    TextView prod_name, prod_price, prod_desc;
    ImageView prod_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    ImageView img_product;
    private Item item;
    Double total = 0.0;
    int noOfItems = 0;
    boolean isItemAlreadyPresent = false;
    Cart alreadyPresentedCart = null;
    FButton btn_addtocart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        item = getIntent().getExtras().getParcelable("item");

        // findItemAlreadyAvailableInCart();
        prod_name = (TextView) findViewById(R.id.product_details_name);
        prod_price = (TextView) findViewById(R.id.product_details_price);
        prod_desc = (TextView) findViewById(R.id.product_details_desc);
        prod_image = (ImageView) findViewById(R.id.product_details_image);
        btn_addtocart = findViewById(R.id.btn_addtocart);
        prod_name.setText(item.getName());
        prod_price.setText(item.getCostperitem());
        prod_desc.setText(item.getName());

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        img_product = (ImageView) findViewById(R.id.product_details_image);
        btnCart = findViewById(R.id.btn_cart);
        numberButton = findViewById(R.id.number_button);

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                noOfItems = newValue;
                total = noOfItems * Double.valueOf(item.getCost());

            }
        });

        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noOfItems > 0) {

                    DatabaseHelper helper = new DatabaseHelper(ProductDetailsActivity.this);
                    findItemAlreadyAvailableInCart(false);
                    if (isItemAlreadyPresent) {
                        Double total = noOfItems * Double.valueOf(alreadyPresentedCart.getCost());
                        helper.updateCostAndQuantity(numberButton.getNumber(), String.valueOf(total)
                                , String.valueOf(alreadyPresentedCart.getId()));

                        Snackbar.make(view, "updated to cart", Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }
                    Cart cart = new Cart();
                    cart.setName(item.getName());
                    cart.setCost(item.getCost());
                    cart.setCostperitem(item.getCostperitem());
                    cart.setEach(item.getEach());
                    cart.setType(item.getType());
                    cart.setQuantity(String.valueOf(noOfItems));
                    cart.setTotal(String.valueOf(total));
                    cart.setItem_id(item.getId());
                    helper.insertItem_cart(cart);

                    Snackbar.make(view, "added to cart", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("noOfItems", noOfItems + "");
                startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
            }
        });
    }

    private void findItemAlreadyAvailableInCart(boolean show) {
        isItemAlreadyPresent = false;
        if (item != null) {
            DatabaseHelper helper = new DatabaseHelper(ProductDetailsActivity.this);
            List<Cart> cartList = helper.getCart();
            for (int i = 0; i < cartList.size(); i++) {
                Log.e("CID", cartList.get(i).getItem_id() + "");
                Log.e("ID", item.getId() + "");
                if (item.getId() == cartList.get(i).getItem_id()) {
                    isItemAlreadyPresent = true;
                    alreadyPresentedCart = cartList.get(i);
                    if (show) {
                        numberButton.setNumber(cartList.get(i).getQuantity());
                        Toast.makeText(getApplicationContext(), "already present in CartActivity", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findItemAlreadyAvailableInCart(true);
    }
}
