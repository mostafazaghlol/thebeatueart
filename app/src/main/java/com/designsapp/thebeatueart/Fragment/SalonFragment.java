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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.ChatActivity;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Model.MLogin.Login;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.RateDialogClass;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.profile;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderLayout;

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
 * Use the {@link SalonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalonFragment extends Fragment {
    private ProgressDialog progressDialog;
    @BindView(R.id.TxtSalondate)
    TextView TxtDate;
    @BindView(R.id.TxtSalonName)
    TextView TxtName;
    @BindView(R.id.TxtSalonDetails)
    TextView TxtDetails;
    @BindView(R.id.TxtSalonLocation)
    TextView TxtLocation;
    @BindView(R.id.imageSlider)
    SliderLayout sliderLayout;
    @BindView(R.id.SalonRating)
    RatingBar ratingBar;
    static String id;
    String userID;
    @OnClick({R.id.ratelayout,R.id.rateme})
    public void rate() {
        RateDialogClass rateDialogClass = new RateDialogClass(getActivity(),id,userID);
        rateDialogClass.show();
    }
    @OnClick(R.id.ImSalonSearch)
    public void fo()
    {
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    @OnClick(R.id.LinearSalon)
    public void getservies() {
        Fragment fragment =new khadamatSalonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.startchat)
    public void startchat() {
        Intent intentstartchat = new Intent(getContext(),ChatActivity.class);
        intentstartchat.putExtra(contants.salonid,id);
        startActivity(intentstartchat);
    }

    @OnClick(R.id.ImSalonNavigation)
    public void openDrawer() {
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }

    public SalonFragment() {
        // Required empty public constructor
    }

    SharedPreferences mSharedPreferences;

    public static SalonFragment newInstance(String id) {
        SalonFragment.id = id;
        return new SalonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon, container, false);
        ButterKnife.bind(this, view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account, Context.MODE_PRIVATE);
        urlmaps2 = new ArrayList<>();
        userID = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addfackdata();
    }

    private void addfackdata() {
        progressDialog.show();
        profile mProfile = new profile(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("status");
                if (success.equals("1")) {
                    Login login = new Gson().fromJson(response, Login.class);
                    urlmaps2.add(login.getUser().getImage());
                    TxtName.setText(login.getUser().getName());
                    TxtDate.setText(login.getUser().getTimeWork());
                    TxtLocation.setText(login.getUser().getAddress());
                    TxtDetails.setText(login.getUser().getDetails());
                    ratingBar.setRating(login.getUser().getRate());
                    ratingBar.setIsIndicator(true);
                    progressDialog.dismiss();
                    id = login.getUser().getId().toString();
                    setupsilders();
                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, "تأكد من البيانات ", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException s) {
                progressDialog.dismiss();
            }
        }, id);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mProfile);
    }

    List<String> urlmaps2;

    private void setupsilders() {
        for (int i = 0; i < urlmaps2.size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView.setImageUrl(urlmaps2.get(i));
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER);
            final int finalI = i;
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}
