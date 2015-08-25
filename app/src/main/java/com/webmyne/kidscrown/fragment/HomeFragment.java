package com.webmyne.kidscrown.fragment;

import android.app.Activity;

import java.lang.reflect.Type;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.webmyne.kidscrown.R;
import com.webmyne.kidscrown.adapters.ProductAdapter;
import com.webmyne.kidscrown.helper.CallWebService;
import com.webmyne.kidscrown.helper.Constants;
import com.webmyne.kidscrown.helper.DatabaseHandler;
import com.webmyne.kidscrown.helper.Functions;
import com.webmyne.kidscrown.helper.ToolHelper;
import com.webmyne.kidscrown.model.Product;
import com.webmyne.kidscrown.ui.CrownActivity;
import com.webmyne.kidscrown.ui.MyDrawerActivity;
import com.webmyne.kidscrown.ui.ProductDetailActivity;
import com.webmyne.kidscrown.ui.RefillActivity;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listProducts;
    private ProductAdapter adapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View v) {

        listProducts = (ListView) v.findViewById(R.id.listProducts);
        listProducts.setOnItemClickListener(this);
        listProducts.setEmptyView(v.findViewById(R.id.txtLoading));
        ((MyDrawerActivity) getActivity()).setTitle("Products");

    }

    @Override
    public void onResume() {
        super.onResume();
        displayProducts();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchProducts();
    }

    private void displayProducts() {

        try {
            DatabaseHandler handler = new DatabaseHandler(getActivity());
            handler.openDataBase();
            Cursor cursor = handler.getProductsCursor();
            handler.close();
            adapter = new ProductAdapter(getActivity(), cursor, 0);
            listProducts.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fetchProducts() {

        View view = ((MyDrawerActivity) getActivity()).getToolbar().getRootView();
        //final ToolHelper helper = new ToolHelper(getActivity(), view);
        // helper.displayProgress();

        new CallWebService(Constants.FETCH_PRODUCTS, CallWebService.TYPE_GET) {
            @Override
            public void response(String response) {

                // helper.hideProgress();
                Log.e("Response Products", response);
                Type listType = new TypeToken<List<Product>>() {
                }.getType();
                ArrayList<Product> products = new GsonBuilder().create().fromJson(response, listType);
                DatabaseHandler handler = new DatabaseHandler(getActivity());
                handler.saveProducts(products);
                handler.close();
                displayProducts();

            }

            @Override
            public void error(String error) {
                // helper.hideProgress();
                Log.e("Error", error);

            }
        }.call();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        processProductClick(cursor);

    }

    private void processProductClick(Cursor cursor) {

        String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));

        if (productName.equalsIgnoreCase("Crown")) {

            Intent iRefill = new Intent(getActivity(), RefillActivity.class);
            iRefill.putExtra("product_id", productId);
            startActivity(iRefill);


        } else {

            Intent iDetail = new Intent(getActivity(), ProductDetailActivity.class);
            iDetail.putExtra("product_id", productId);
            startActivity(iDetail);
            getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }

    }


}