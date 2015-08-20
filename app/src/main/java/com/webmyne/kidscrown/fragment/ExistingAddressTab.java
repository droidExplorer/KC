package com.webmyne.kidscrown.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.webmyne.kidscrown.R;
import com.webmyne.kidscrown.helper.CallWebService;
import com.webmyne.kidscrown.helper.ComplexPreferences;
import com.webmyne.kidscrown.helper.Constants;
import com.webmyne.kidscrown.helper.DatabaseHandler;
import com.webmyne.kidscrown.helper.ToolHelper;
import com.webmyne.kidscrown.model.Address;
import com.webmyne.kidscrown.model.Product;
import com.webmyne.kidscrown.model.UserProfile;
import com.webmyne.kidscrown.ui.MyDrawerActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExistingAddressTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExistingAddressTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExistingAddressTab extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String userId;
    View parentView;

    public static ExistingAddressTab newInstance(String param1, String param2) {
        ExistingAddressTab fragment = new ExistingAddressTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ExistingAddressTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_existing_address, container, false);

        init(parentView);

        return parentView;
    }

    private void init(View parentView) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "user_pref", 0);
        UserProfile currentUserObj = new UserProfile();
        currentUserObj = complexPreferences.getObject("current-user", UserProfile.class);
        userId = currentUserObj.UserID;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchAddress();
    }

    private void fetchAddress() {
        String user = "?UserId=" + userId;
        Log.e("Address URL", Constants.GET_EXISTING_ADDRESS + user);
        View view = ((MyDrawerActivity) getActivity()).getToolbar().getRootView();
        final ToolHelper helper = new ToolHelper(getActivity(), view);
        helper.displayProgress();

        new CallWebService(Constants.GET_EXISTING_ADDRESS + user, CallWebService.TYPE_GET) {
            @Override
            public void response(String response) {
                helper.hideProgress();
                Log.e("Response Address", response);
                Type listType = new TypeToken<List<Address>>() {
                }.getType();
                ArrayList<Address> addresses = new GsonBuilder().create().fromJson(response, listType);
                DatabaseHandler handler = new DatabaseHandler(getActivity());
                handler.saveAddress(addresses);
                handler.close();
                displayAddress();

            }

            @Override
            public void error(String error) {
                helper.hideProgress();
                Log.e("Error", error);

            }
        }.call();
    }

    private void displayAddress() {

    }
}
