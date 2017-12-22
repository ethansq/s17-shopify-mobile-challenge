package com.han.ethan.s17shopifymobilechallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.ethan.s17shopifymobilechallenge.R;
import com.han.ethan.s17shopifymobilechallenge.helpers.AdapterDetails;
import com.han.ethan.s17shopifymobilechallenge.helpers.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityProduct extends AppCompatActivity {
    private final String TAG = "ActivityProduct";

    private JSONObject mProductJson;
    private ImageView mProductImageView;
    private RecyclerView mDetailsRecyclerView;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        mProductImageView = findViewById(R.id.productImage);
        mTitleTextView = findViewById(R.id.productTitle);

        mDetailsRecyclerView = findViewById(R.id.detailsRecyclerView);
        // Set its layout manager
        mDetailsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        try {
            mProductJson = new JSONObject(getIntent().getStringExtra("productJsonString"));

            mTitleTextView.setText(mProductJson.getString(Constants.TITLE_FIELD));

            // Load image with a fade-in
            final String IMAGE_URL = mProductJson.getJSONObject(Constants.IMAGE_FIELD).getString(Constants.IMAGE_SRC_FIELD);
            Picasso.with(this).load(IMAGE_URL).fetch(new Callback(){
                @Override
                public void onSuccess() {
                    mProductImageView.setAlpha(0f);
                    Picasso.with(ActivityProduct.this).load(IMAGE_URL).into(mProductImageView);
                    mProductImageView.animate().setDuration(500).alpha(1f).start();
                }

                @Override
                public void onError() {
                    Log.e(TAG, "Picasso> onError");
                }
            });

            mDetailsRecyclerView.setAdapter(new AdapterDetails(mProductJson, this));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
