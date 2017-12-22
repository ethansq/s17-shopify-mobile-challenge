package com.han.ethan.s17shopifymobilechallenge.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.ethan.s17shopifymobilechallenge.R;
import com.han.ethan.s17shopifymobilechallenge.activities.ActivityProduct;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter implements Filterable {
    private final String TAG = "AdapterDetails";
    private Context mContext;
    private ArrayList<JSONObject> mProductList;
    private ArrayList<JSONObject> mFilteredList;
    private CustomFilter mFilter;

    public AdapterProducts(ArrayList<JSONObject> productList, EditText searchBox, Context context) {
        this.mProductList = productList;
        this.mFilteredList = new ArrayList<>();
        this.mFilteredList.addAll(mProductList);
        mContext = context;

        mFilter = new CustomFilter(this);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
        JSONObject product = (JSONObject) mFilteredList.get(pos);

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
                    in.putExtra("productJsonString", mFilteredList.get(index).toString());
                    mContext.startActivity(in);
                }
            });
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class CustomFilter extends Filter {
        private AdapterProducts mAdapter;

        private CustomFilter(AdapterProducts mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mFilteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                mFilteredList.addAll(mProductList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final JSONObject productJson : mProductList) {
                    try {
                        if (productJson.getString("title").toLowerCase().startsWith(filterPattern)) {
                            mFilteredList.add(productJson);
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            results.values = mFilteredList;
            results.count = mFilteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
