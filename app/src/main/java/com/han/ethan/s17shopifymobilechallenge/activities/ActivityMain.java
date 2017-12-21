package com.han.ethan.s17shopifymobilechallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.han.ethan.s17shopifymobilechallenge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "ActivityMain";

    private RequestQueue mRequestQueue;
    private ArrayList<JSONObject> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductList = new ArrayList<>();

        /**
         * On create, fetch product data using the given URL
         */
        mRequestQueue = Volley.newRequestQueue(this);

        final String URL = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse through response and get all individual products
                            JSONArray productsArray = response.getJSONArray("products");
                            int n = productsArray.length();
                            for (int i=0; i<n; i++) {
                                mProductList.add((JSONObject) productsArray.get(i));
                            }

                            Log.e(TAG, mProductList.size()+"abc");
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
