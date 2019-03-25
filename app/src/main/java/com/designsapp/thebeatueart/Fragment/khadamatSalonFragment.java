package com.designsapp.thebeatueart.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Model.MSalonServies.Datum;
import com.designsapp.thebeatueart.Model.MSalonServies.SaloneServices;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.SimpleFragmentPagerAdapter;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.salone_services;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link khadamatSalonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class khadamatSalonFragment extends Fragment {
        @OnClick(R.id.cartSalon)
        public void opennavigation(){
            MainActivity.drawer.openDrawer(Gravity.RIGHT);
        }
        static String  id;
    public khadamatSalonFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static khadamatSalonFragment newInstance(String id) {
        khadamatSalonFragment.id = id;
        return new khadamatSalonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ViewPager viewPager;
    SimpleFragmentPagerAdapter adapter;
    TabLayout tabLayout;
    ArrayList<Datum> xservies;
    LinearLayout linearLayout;
    TextView TxtEmpty;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khadamat, container, false);
        ButterKnife.bind(this,view);
        xservies =new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        linearLayout = view.findViewById(R.id.licc);
        TxtEmpty = view.findViewById(R.id.textEmpty);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        id = getArguments().getString("id");
        adapter = new SimpleFragmentPagerAdapter(getContext(), getActivity().getSupportFragmentManager(),xservies,id);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Find the view pager that will allow the user to swipe between fragments

        // Create an adapter that knows which fragment should be shown on each page

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
        addfackData();
    }

    private void addfackData() {
        progressDialog.show();
        salone_services mSalone_services = new salone_services(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    progressDialog.dismiss();
                    TxtEmpty.setVisibility(View.INVISIBLE);
                    SaloneServices saloneServices = new Gson().fromJson(response,SaloneServices.class);
                    MainActivity.mSaloneServices = saloneServices;
                    xservies.addAll(saloneServices.getData());
                    adapter.notifyDataSetChanged();
                }else{
                    progressDialog.dismiss();
                    tabLayout.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    TxtEmpty.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                Log.e("Error",e.getMessage().toString());
                progressDialog.dismiss();
            }

        },id);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mSalone_services);
    }
}
