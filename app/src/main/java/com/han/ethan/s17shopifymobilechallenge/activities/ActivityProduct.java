package com.han.ethan.s17shopifymobilechallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.han.ethan.s17shopifymobilechallenge.R;
import com.han.ethan.s17shopifymobilechallenge.helpers.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityProduct extends AppCompatActivity {
    private final String TAG = "ActivityProduct";

    private JSONObject mProductJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            mProductJson = new JSONObject(getIntent().getStringExtra("productJsonString"));
            getSupportActionBar().setTitle(mProductJson.getString(Constants.TITLE_FIELD));

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
