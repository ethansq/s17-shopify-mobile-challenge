package com.han.ethan.s17shopifymobilechallenge.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.ethan.s17shopifymobilechallenge.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AdapterDetails extends RecyclerView.Adapter {
    private final String TAG = "AdapterDetails";
    private Context mContext;
    private JSONObject mProductJson;
    private ArrayList<Pair<String, Object>> mProductDetails;

    public AdapterDetails(JSONObject productJson, Context context) {
        this.mProductJson = productJson;
        mContext = context;
        mProductDetails = new ArrayList<>();

        Iterator<String> iter = productJson.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = productJson.get(key);
                if (value instanceof String || value instanceof Long || value instanceof Integer) {
                    mProductDetails.add(
                            new Pair<>(key, value)
                    );
                }

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class DetailViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView detailName;
        public TextView detailValue;

        public DetailViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.detailName = itemView.findViewById(R.id.detailName);
            this.detailValue = itemView.findViewById(R.id.detailValue);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View alertView = inflater.inflate(R.layout.item_detail, parent, false);
        viewHolder = new DetailViewHolder(alertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        final DetailViewHolder viewHolder = (DetailViewHolder) holder;
        Pair<String, Object> detail = mProductDetails.get(pos);

        viewHolder.detailName.setText(detail.first);
        viewHolder.detailValue.setText(detail.second.toString());
    }

    @Override
    public int getItemCount() {
        return mProductDetails.size();
    }
}
