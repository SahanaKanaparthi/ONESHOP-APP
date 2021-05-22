package com.one.shop.ui.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.shop.R;
import com.one.shop.data.Store;

import static com.one.shop.R.drawable.store_aldi;

public class StoreViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView image;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        name = itemView.findViewById(R.id.name);
    }

    public void bind(Store item, final Context context) {
        name.setText(item.getName());

        String image_store = item.getImage();
        Log.e("IMAGE", String.valueOf(image_store));
        switch (image_store) {

            case "store_aldi":
                image.setBackgroundResource(store_aldi);
                break;
            case "store_bigy":
                image.setBackgroundResource(R.drawable.store_bigy);
                break;
            case "store_brothersmarket":
                image.setBackgroundResource(R.drawable.store_brothersmarket);
                break;
            case "store_rochebros":
                image.setBackgroundResource(R.drawable.store_rochebros);
                break;
            case "store_shaws":
                image.setBackgroundResource(R.drawable.store_shaws);
                break;
            case "store_stopshop":
                image.setBackgroundResource(R.drawable.store_stopshop);
                break;
            case "store_target":
                image.setBackgroundResource(R.drawable.store_target);
                break;
            case "store_wegmen":
                image.setBackgroundResource(R.drawable.store_wegmen);
                break;

            case "store_americafood":
                image.setBackgroundResource(R.drawable.store_americafood);
                break;

            case "store_marketbasket":
                image.setBackgroundResource(R.drawable.store_marketbasket);
                break;

            case "store_market32":
                image.setBackgroundResource(R.drawable.store_market32);
                break;

            case "store_starmarket":
                image.setBackgroundResource(R.drawable.store_starmarket);
                break;

        }


    }
}
