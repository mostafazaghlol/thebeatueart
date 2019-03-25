package com.designsapp.thebeatueart.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Adapters.subkhadatAdapter;
import com.designsapp.thebeatueart.Model.MSalonSupServies.SaloneSupservices;
import com.designsapp.thebeatueart.Model.MSalonSupServies.SupService;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.addtocart;
import com.designsapp.thebeatueart.Volley.salone_supservices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link khadamatSalonSubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class khadamatSalonSubFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    subkhadatAdapter homesAdapter;
    List<SupService> Khadamats;
    private static String userid,serviesid;
    public khadamatSalonSubFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static khadamatSalonSubFragment newInstance(String x, String userx) {
        khadamatSalonSubFragment.userid = userx;
        khadamatSalonSubFragment.serviesid = x;
        return new khadamatSalonSubFragment();
    }
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_khadamat, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllkhadamatsubSalones);
        Khadamats = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homesAdapter  = new subkhadatAdapter(getContext(), Khadamats, (view1, position) -> {
           progressDialog.show();
            addtocart mAddtocart = new addtocart(response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("1")){
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "تم اضافة طلبك", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            },String.valueOf(mSharedPreferences.getInt(contants.id,0)),Khadamats.get(position).getId().toString());
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(mAddtocart);
        });
        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        addfackData();

    }

    private void addfackData() {
        progressDialog.show();
        salone_supservices mSalone_supservices = new salone_supservices(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    progressDialog.dismiss();
                    SaloneSupservices mSaloneSupservices = new Gson().fromJson(response,SaloneSupservices.class);
                    Khadamats.addAll(mSaloneSupservices.getData().get(0).getSupServices());
                    homesAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        },userid,serviesid);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mSalone_supservices);
        homesAdapter.notifyDataSetChanged();
    }
}
