package com.designsapp.thebeatueart.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Adapters.notificationsAdapter;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.notifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link notificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notificationsFragment extends Fragment {
    @OnClick(R.id.cartcart1)
    public void cart(){
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }
    @OnClick(R.id.search11)
    public void search(){
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    @BindView(R.id.ReAllNotifications)
    RecyclerView ReNotification;
    @BindView(R.id.emptynoi)
    TextView TxtNot;
    notificationsAdapter mNotificationsAdapter;
    LinearLayoutManager linearLayoutManager;
    List<String> Notifications;
    ProgressDialog progressDialog;
    String userid;
    public notificationsFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static notificationsFragment newInstance() {
        return new notificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        userid = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        Notifications = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mNotificationsAdapter = new notificationsAdapter(getContext(),Notifications);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReNotification.setLayoutManager(linearLayoutManager);
        ReNotification.setAdapter(mNotificationsAdapter);
        addfackdata();
    }

    private void addfackdata() {
        progressDialog.show();
        notifications mNotifications = new notifications(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("notification")){
                        JSONArray notificato = jsonObject.getJSONArray("notification");
                        for(int i=0;i<notificato.length();i++){
                            JSONObject jsonObject1 = notificato.getJSONObject(i);
                            Notifications.add(jsonObject1.getString("message"));
                        }
                        if(Notifications.size() == 0){
                            ReNotification.setVisibility(View.INVISIBLE);
                            TxtNot.setVisibility(View.VISIBLE);
                        }
                        mNotificationsAdapter.notifyDataSetChanged();
                    }else{
                        ReNotification.setVisibility(View.INVISIBLE);
                        TxtNot.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },userid);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mNotifications);
    }


}
