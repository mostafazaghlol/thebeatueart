package com.designsapp.thebeatueart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Adapters.SearchsAdapter;
import com.designsapp.thebeatueart.Model.MSearch.Search;
import com.designsapp.thebeatueart.Model.MSearch.Search_;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;
import com.designsapp.thebeatueart.Volley.search;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @OnClick(R.id.TxtAdvanceSearch)
    public void advnace(){
        startActivity(new Intent(this,AdvanceSearch.class));
    }
    @OnClick(R.id.TxtMapSearch)
    public void MapSearch(){
        startActivity(new Intent(this,SearchMapsActivity.class));
    }
    @BindView(R.id.ImSearch) ImageView Search2;
    List<Search_> Searchs;
    @OnClick(R.id.ImSearch)
    public void search(){
        if(EdSearch.getText().toString().isEmpty()){
            EdSearch.setError(getString(R.string.Ederror));
            return;
        }else{
            Searchs.clear();
            search mSearch = new search(response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("search");
                    if(jsonArray.length()>0){
                        Search mSearch1 = new Gson().fromJson(response,Search.class);
                        Searchs.addAll(mSearch1.getSearch());
                        searchsAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            },EdSearch.getText().toString());
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(mSearch);
        }
    }
    @BindView(R.id.Edsearch) EditText EdSearch;
    @OnClick(R.id.back)
    public void back(){
        onBackPressed();
    }
    @BindView(R.id.searchrecycler)
    RecyclerView ReSearch;
    SearchsAdapter searchsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
        Searchs = new ArrayList<>();
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchsAdapter = new SearchsAdapter(this, Searchs, (view1, position) -> {
//            Fragment fragment =SalonFragment.newInstance(Salons.get(position).getId().toString());
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        });
        ReSearch.setAdapter(searchsAdapter);
        ReSearch.setLayoutManager(VlayoutManager);
    }
}
