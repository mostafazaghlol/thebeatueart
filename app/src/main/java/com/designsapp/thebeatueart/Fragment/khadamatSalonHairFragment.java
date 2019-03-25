package com.designsapp.thebeatueart.Fragment;

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

import com.designsapp.thebeatueart.Adapters.khadatAdapter;
import com.designsapp.thebeatueart.Model.khadamat;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link khadamatSalonHairFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class khadamatSalonHairFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    khadatAdapter homesAdapter;
    List<khadamat> Khadamats;
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

//    @OnClick(R.id.cart)
//    public void Sa(){
//        startActivity(new Intent(getActivity(),CartActivity.class));
//    }
    public khadamatSalonHairFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static khadamatSalonHairFragment newInstance() {
        return new khadamatSalonHairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khadamat_hair, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllkhadamatSalones);
        Khadamats = new ArrayList<>();
        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Khadamats = firstsplachActivity.Khadamats;
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        homesAdapter  = new khadatAdapter(getContext(), Khadamats, new khadatAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(),MainActivity.class);
//                intent.putExtra(contants.gox,1);
//                startActivity(intent);
//                getActivity().finish();
//
////                Fragment fragment = new khadamatSalonSubFragment();
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//            }
//        });

        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);
        addfackData();
//        EdSearch.setActivated(true);
//        EdSearch.setQueryHint("اكتب اسم الوجبه هنا !");
//        EdSearch.onActionViewExpanded();
//        EdSearch.setIconified(false);
//        EdSearch.clearFocus();
//        EdSearch.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                List<montage> newList = new ArrayList<>();
//                for(int i=0;i<Khadamats.size();i++){
//                    if(Khadamats.get(i).getTitle().contains(s.toLowerCase())||Khadamats.get(i).getTitle().contains(s.toUpperCase()))
//                    {
//                        newList.add(Khadamats.get(i));
//                    }
//                }
//                homesAdapter.updatelist(newList);
//                homesAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
    }

    private void addfackData() {
//        Khadamats = firstsplachActivity.Khadamats;
//        homesAdapter.notifyDataSetChanged();
        for(int i=0;i<50;i++){
            Khadamats.add(new khadamat("العنوان",R.drawable.salon));
        }
        homesAdapter.notifyDataSetChanged();
    }
}
