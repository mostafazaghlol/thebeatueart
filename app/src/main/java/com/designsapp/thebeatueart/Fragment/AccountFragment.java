package com.designsapp.thebeatueart.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
//    RecyclerView RecyclerMontagat;
//    HomesAdapter homesAdapter;
//    List<Salon> Salons;
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
    @OnClick(R.id.TxtAccountmodifiy)
    public void modifiy(){
        Fragment fragment = new AccountModifiyFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @BindView(R.id.photouser)
   ImageView circleImageView2;
    @BindView(R.id.Txtaccountname)
    TextView TxtName;
    @BindView(R.id.Txtaccountemail)
    TextView TxtEmail;
    @BindView(R.id.Txtaccountlocation)
    TextView Txtlocation;
    @BindView(R.id.Txtaccountphone)
    TextView Txtphone;
    public AccountFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        circleImageView2.bringToFront();
        TxtName.setText(mSharedPreferences.getString(contants.username,""));
        TxtEmail.setText(mSharedPreferences.getString(contants.email,""));
        Txtlocation.setText(mSharedPreferences.getString(contants.address,""));
        Txtphone.setText(mSharedPreferences.getString(contants.phone,""));
        Picasso.get().load(mSharedPreferences.getString(contants.image,"")).placeholder(R.drawable.salon).into(circleImageView2);
    }

    private void addfackData() {
//        Salons = firstsplachActivity.Salons;
//        homesAdapter.notifyDataSetChanged();
//        for(int i=0;i<50;i++){
//            Salons.add(new Salon("العنوان","العنوان",4.5f,R.drawable.salon));
//        }
//        homesAdapter.notifyDataSetChanged();
    }
}
