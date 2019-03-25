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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Adapters.khadatAdapter;
import com.designsapp.thebeatueart.Model.MSalonServies.Service;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link khadamatSalonmackupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class khadamatSalonmackupFragment extends Fragment {
    RecyclerView RecyclerMontagat;
    khadatAdapter homesAdapter;
    List<Service> Khadamats;
    private String strtext2;

    public khadamatSalonmackupFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static khadamatSalonmackupFragment newInstance() {
        return new khadamatSalonmackupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ProgressDialog progressDialog;
    Integer strtext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khadamat_mackup, container, false);
        RecyclerMontagat = view.findViewById(R.id.ReAllkhadamatmackupSalones);
        Khadamats = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        ButterKnife.bind(this,view);
        strtext = getArguments().getInt("edttext");
        strtext2 = getArguments().getString("edttext2");
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        Khadamats.addAll(MainActivity.mSaloneServices.getData().get(strtext).getServices());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Khadamats = firstsplachActivity.Khadamats;
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homesAdapter  = new khadatAdapter(getContext(), Khadamats, (view1, position) -> {
            Intent intent = new Intent(getContext(),MainActivity.class);
            intent.putExtra(contants.gox,Khadamats.get(position).getId());
            intent.putExtra(contants.user_id,strtext2);
            startActivity(intent);
            getActivity().finish();
//                Fragment fragment = new khadamatSalonSubFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
        });

        RecyclerMontagat.setAdapter(homesAdapter);
        RecyclerMontagat.setLayoutManager(VlayoutManager);

    }

    private void addfackData() {
        progressDialog.show();
        homesAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}
