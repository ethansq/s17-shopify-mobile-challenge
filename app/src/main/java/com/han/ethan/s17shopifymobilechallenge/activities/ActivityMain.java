package com.han.ethan.s17shopifymobilechallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.han.ethan.s17shopifymobilechallenge.R;
import com.han.ethan.s17shopifymobilechallenge.helpers.AdapterProducts;
import com.han.ethan.s17shopifymobilechallenge.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "ActivityMain";

    private RequestQueue mRequestQueue;
    private ArrayList<JSONObject> mProductList;
    private RecyclerView mProductRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mProductList = new ArrayList<>();

        mProductRecyclerView = findViewById(R.id.productRecyclerView);
        // Set its layout manager
        mProductRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        /**
         * On create, fetch product data using the given URL
         */
        mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constants.PRODUCT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse through response and get all individual products
                            JSONArray productsArray = response.getJSONArray("products");
                            int n = productsArray.length();
                            for (int i=0; i<n; i++) {
                                mProductList.add((JSONObject) productsArray.get(i));
                                // Add a random colour for decoration
                                mProductList.get(i).put(
                                        Constants.COLOUR_FIELD, Constants.COLOURS[new Random().nextInt(Constants.NUM_COLOURS)]
                                );
                            }

                            mProductRecyclerView.setAdapter(
                                    new AdapterProducts(mProductList, (EditText) findViewById(R.id.searchBox), ActivityMain.this)
                            );

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
        // lastly, add the request to queue
        mRequestQueue.add(jsonRequest);
    }
}
