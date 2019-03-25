package com.designsapp.thebeatueart.Fragment;

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
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Adapters.BookAdapter;
import com.designsapp.thebeatueart.Model.Book;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.BookRequest;

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
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    BookAdapter homesAdapter;
    List<Book> Salons;
    private String userid;
    @BindView(R.id.emptyhogojat)TextView Txt;

    @OnClick(R.id.cartayoutBook)
    public void opendrawer(){
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }
    public BookFragment() {
        // Required empty public constructor
    }
    @OnClick(R.id.search0)
    public void gosearch(){
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    SharedPreferences mSharedPreferences;
    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllBooks);
        Salons = new ArrayList<>();
        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        userid = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Salons = firstsplachActivity.Salons;
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homesAdapter = new BookAdapter(getContext(), Salons);

        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);
        addfackData();
    }

    private void addfackData() {
        BookRequest mBook = new BookRequest(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject status = jsonObject.getJSONObject("status");
                if(status.has("success")){
                    Integer success = status.getInt("success");
                    if(success == 1){
                        JSONArray data = status.getJSONArray("data");
                        for(int i=0;i<data.length();i++){
                            JSONObject dataobject = data.getJSONObject(i);
                            Salons.add(new Book(dataobject.getString("name"),dataobject.getString("phone"),dataobject.getString("total_price"),dataobject.getString("date"),dataobject.getString("time"),dataobject.getString("photo")));
                        }
                        homesAdapter.notifyDataSetChanged();
                        Txt.setVisibility(View.INVISIBLE);
                    }else{
                        Txt.setVisibility(View.VISIBLE);
                        RecyclerMontagat.setVisibility(View.INVISIBLE);
                    }

                }else{
                    RecyclerMontagat.setVisibility(View.INVISIBLE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },userid);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mBook);
    }
}
