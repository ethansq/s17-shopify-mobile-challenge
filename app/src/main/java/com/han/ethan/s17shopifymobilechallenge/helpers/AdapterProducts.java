package com.han.ethan.s17shopifymobilechallenge.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.ethan.s17shopifymobilechallenge.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<JSONObject> mProductList;

    public AdapterProducts(ArrayList<JSONObject> productList, Context context) {
        this.mProductList = productList;
        mContext = context;
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView productImage;
        public TextView productTitle;
        public TextView productDescription;

        public ProductViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
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
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        JSONObject product = (JSONObject) mProductList.get(pos);

        try {
            viewHolder.productTitle.setText(
                    product.getString("title")
            );
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
