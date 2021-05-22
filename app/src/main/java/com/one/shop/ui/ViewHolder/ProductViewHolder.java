package com.one.shop.ui.ViewHolder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.ui.ProductDetailsActivity;
import com.one.shop.R;
import com.one.shop.data.Item;

public class ProductViewHolder extends RecyclerView.ViewHolder {


    public TextView product_name, product_price, product_quantity;
    public ImageView product_image;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        product_image = (ImageView) itemView.findViewById(R.id.product_image);
        product_name = (TextView) itemView.findViewById(R.id.product_name);
        product_price = (TextView) itemView.findViewById(R.id.product_price);
        product_quantity = (TextView) itemView.findViewById(R.id.product_quantity);


    }


    public void bind(final Item item, final Context context) {
        product_name.setText(item.getName());
        product_price.setText(item.getCostperitem());
        product_quantity.setText(item.getEach());

        int resId = context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName());

        product_image.setImageDrawable(getDrawableForSdkVersion(resId, context.getResources()));


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ItemsActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent productDetail = new Intent(context, ProductDetailsActivity.class);
                productDetail.putExtra("item", item);
                context.startActivity(productDetail);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private Drawable getDrawableForSdkVersion(int resId, Resources res) {

        Drawable drawable = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            drawable = res.getDrawable(resId, null);
        } else {
            drawable = res.getDrawable(resId);
        }

        return drawable;

    }
}
