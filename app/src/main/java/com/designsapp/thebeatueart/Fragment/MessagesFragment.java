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
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.ChatActivity;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Adapters.messagesAdapter;
import com.designsapp.thebeatueart.Model.message;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.messages;

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
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    messagesAdapter homesAdapter;
    ProgressDialog progressDialog;
    @BindView(R.id.empmes)
    TextView Txt;
    @OnClick(R.id.cartMessage)
    public void  cartMessage(){
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }
    @OnClick(R.id.search00)
    public void gosearch(){
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    List<message> Salons;
    public MessagesFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllmessages);
        Salons = new ArrayList<>();
        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homesAdapter = new messagesAdapter(getContext(), Salons, (view1, position) -> {
         Intent intent =new Intent(getContext(),ChatActivity.class);
         intent.putExtra(contants.salonid,String.valueOf(Salons.get(position).getId()));
            startActivity(intent);
         getActivity().finish();
        });

        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);
        addfackData();
    }
    private void addfackData() {
        progressDialog.show();
        for(int i=0;i<50;i++){
            Salons.add(new message("العنوان","العنوان","09:30am",R.drawable.salon));
        }
        Salons.clear();

        messages mMessages = new messages(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("data")){
                    progressDialog.dismiss();
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);
                        Salons.add(new message(k.getString("name"),k.getString("photo"),k.getInt("salon_id")));
                    }

                    Txt.setVisibility(View.INVISIBLE);
                    RecyclerMontagat.setVisibility(View.VISIBLE);
                }else{
                    Txt.setVisibility(View.VISIBLE);
                    RecyclerMontagat.setVisibility(View.INVISIBLE);
                }
                homesAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }

        },String.valueOf(mSharedPreferences.getInt(contants.id,0)));
        RequestQueue requestQueue =Volley.newRequestQueue(getContext());
        requestQueue.add(mMessages);
    }
}
