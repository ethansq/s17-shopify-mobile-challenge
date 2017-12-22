package com.han.ethan.s17shopifymobilechallenge.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.ethan.s17shopifymobilechallenge.R;
import com.han.ethan.s17shopifymobilechallenge.activities.ActivityProduct;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class AdapterProducts extends RecyclerView.Adapter {
    private final String TAG = "AdapterProducts";
    private Context mContext;
    private ArrayList<JSONObject> mProductList;

    public AdapterProducts(ArrayList<JSONObject> productList, Context context) {
        this.mProductList = productList;
        mContext = context;
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public View borderView;
        public ImageView productImage;
        public TextView productTitle;
        public TextView productDescription;

        public ProductViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.borderView = itemView.findViewById(R.id.borderView);
            this.productImage = itemView.findViewById(R.id.productImage);
            this.productTitle = itemView.findViewById(R.id.productTitle);
            this.productDescription = itemView.findViewById(R.id.productDescription);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View alertView = inflater.inflate(R.layout.item_product, parent, false);
        viewHolder = new ProductViewHolder(alertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        final ProductViewHolder viewHolder = (ProductViewHolder) holder;
        JSONObject product = (JSONObject) mProductList.get(pos);

        try {
            viewHolder.productTitle.setText(
                    product.getString(Constants.TITLE_FIELD) // title
            );

            viewHolder.productDescription.setText(
                    product.getString(Constants.DESCRIPTION_FIELD) // description
            );

            // Set a random border colour
            viewHolder.borderView.setBackgroundColor(
                    product.getInt(Constants.COLOUR_FIELD)
            );

            // Load image with a fade-in
            final String IMAGE_URL = product.getJSONObject(Constants.IMAGE_FIELD).getString(Constants.IMAGE_SRC_FIELD);
            Picasso.with(mContext).load(IMAGE_URL).fetch(new Callback(){
                @Override
                public void onSuccess() {
                    viewHolder.productImage.setAlpha(0f);
                    Picasso.with(mContext).load(IMAGE_URL).into(viewHolder.productImage);
                    viewHolder.productImage.animate().setDuration(500).alpha(1f).start();
                }

                @Override
                public void onError() {
                    Log.e(TAG, "Picasso> onError");
                }
            });

            // Set click listener to show product details when tapped
            final int index = pos;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(mContext, ActivityProduct.class);
                    in.putExtra("productJsonString", mProductList.get(index).toString());
                    mContext.startActivity(in);
                }
            });
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
