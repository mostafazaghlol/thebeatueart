package com.designsapp.thebeatueart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Model.MSearch.Search;
import com.designsapp.thebeatueart.Model.MSearch.Search_;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;
import com.designsapp.thebeatueart.Volley.search;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @OnClick(R.id.home)
    public void goMAin(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @OnClick(R.id.back2)
    public void back(){
        finish();
    }
    List<Search_> Searchs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_maps);
        ButterKnife.bind(this);
        Searchs = new ArrayList<>();
        screenwithoutAction.FullScreen(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("مكان ما").icon(BitmapDescriptorFactory.fromResource(R.drawable.group81)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        addfackdata();
        mMap.setOnMarkerClickListener(marker -> {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            return false;
        });
    }

    private void addfackdata() {
        Searchs.clear();
        search mSearch = new search(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("search");
                if(jsonArray.length()>0){
                    Search mSearch1 = new Gson().fromJson(response,Search.class);
                    Searchs.addAll(mSearch1.getSearch());
                    addAllMarkers();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },"");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mSearch);
    }

    private void addAllMarkers() {
        for(int i=0;i<Searchs.size();i++){
            Search_ search_ = Searchs.get(i);
            if( !search_.getLang().isEmpty() || !search_.getLat().isEmpty()){
                LatLng sydney = new LatLng(Double.parseDouble(search_.getLat()),Double.parseDouble(search_.getLang()));
                mMap.addMarker(new MarkerOptions().position(sydney).title(search_.getName()+" "+search_.getDetails()).icon(BitmapDescriptorFactory.fromResource(R.drawable.group81)));
            }
        }
    }
}
