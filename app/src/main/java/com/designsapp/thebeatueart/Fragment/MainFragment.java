package com.designsapp.thebeatueart.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Adapters.HomesAdapter;
import com.designsapp.thebeatueart.Model.MSalon.Datum;
import com.designsapp.thebeatueart.Model.MSalon.Salon;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.home;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    HomesAdapter homesAdapter;
    List<Datum> Salons;
//    @BindView(R.id.searchitems)
//    android.support.v7.widget.SearchView EdSearch;
//    @BindView(R.id.main)
//    LinearLayout linearLayoutMain;
//    @BindView(R.id.serch)
//    LinearLayout linearLayoutSearch;
//    @OnClick(R.id.search_icon)
//    public void search(){
//        linearLayoutMain.setVisibility(View.INVISIBLE);
//        linearLayoutSearch.setVisibility(View.VISIBLE);
//    }
//    @OnClick(R.id.backfromsearch)
//    public void Backsearch(){
//
//    }

    @OnClick(R.id.carthome)
    public void Sa(){
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }
    @OnClick(R.id.search)
    public void Sa1(){
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    @BindView(R.id.TxtEmpty)
    TextView TxtEmpty;
    private ProgressDialog progressDialog;
    public MainFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllSalones);
        Salons = new ArrayList<>();
        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Salons = firstsplachActivity.Salons;
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homesAdapter = new HomesAdapter(getContext(), Salons, (view1, position) -> {
            Fragment fragment =SalonFragment.newInstance(Salons.get(position).getId().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);
        addfackData();

    }

    private void addfackData() {
//        Salons = firstsplachActivity.Salons;
//        homesAdapter.notifyDataSetChanged();
//        for(int i=0;i<50;i++){
//            Salons.add(new Salon("العنوان","العنوان",4.5f,R.drawable.salon));
//        }
        homesAdapter.notifyDataSetChanged();
        progressDialog.show();
        home mHome = new home(response -> {
                Salon mSalons = new Gson().fromJson(response,Salon.class);
                progressDialog.dismiss();
                if(mSalons.getData().size()>0){
                    Salons.addAll(mSalons.getData());
                    homesAdapter.notifyDataSetChanged();
                }else{
                    RecyclerMontagat.setVisibility(View.INVISIBLE);
                    TxtEmpty.setVisibility(View.INVISIBLE);
                }
        },"31.0362","31.3574");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mHome);
    }
}
