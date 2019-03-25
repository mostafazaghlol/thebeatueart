package com.designsapp.thebeatueart.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Model.Mmodify.Modifiy;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.RealPathUtil;
import com.designsapp.thebeatueart.Utils.contants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AccountModifiyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountModifiyFragment extends Fragment {
    @BindView(R.id.modTxtaccountemail)
    EditText EdEmail;
    @BindView(R.id.modTxtaccountlocation)
    TextView EdLocation;
    private String lat,lng,xaddress,address,xcity,location;
    @BindView(R.id.modTxtaccountname) EditText EdName;
    @BindView(R.id.modTxtaccountphone) EditText EdPhone;
    private int personalcode = 1000;
    private String Onefilepath="";
    private String personalImage="";
    private String email,name,phone;

    private ProgressDialog progressDialog;
    private SharedPreferences.Editor editor;

    @OnClick({R.id.modifiy})
    public void update(){
        if(init()){
        try{
            progressDialog.show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(getContext(), uploadId, "http://toegy.net/salon/api/update_user")
                    .addParameter("user_id",String.valueOf(mSharedPreferences.getInt(contants.id,0)))
                    .addParameter("email", email)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("address", xaddress)
                    .addParameter("lat", lat)
                    .addParameter("long", lng);
            multipartUploadRequest.addFileToUpload(Onefilepath, "image");
            UploadNotificationConfig mUploadNotificationConfig = new UploadNotificationConfig()
                    .setRingToneEnabled(true)
                    .setTitleForAllStatuses("جارى التسجيل");
            mUploadNotificationConfig.getCompleted().autoClear = true;
            mUploadNotificationConfig.setIconForAllStatuses(R.drawable.logo1);
            String request = multipartUploadRequest
                    .setUtf8Charset()
                    .setNotificationConfig(mUploadNotificationConfig)
                    .setMaxRetries(10)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            // your code here
                            progressDialog.show();
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
                                            Exception exception) {
                            // your code here
//                            Toast.makeText(getContext(), serverResponse.getBodyAsString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                if(jsonObject.has("success")){

                                    int success = jsonObject.getInt("success");
                                    if (success == 1) {
                                        progressDialog.dismiss();
                                        Modifiy modifiy = new Gson().fromJson(serverResponse.getBodyAsString(),Modifiy.class);
                                        editor.putString(contants.username, modifiy.getData().getName());
                                        editor.putString(contants.email, modifiy.getData().getEmail());
                                        editor.putString(contants.phone, modifiy.getData().getPhone());
                                        editor.putString(contants.role, modifiy.getData().getTypeUser().toString());
                                        editor.putString(contants.address, modifiy.getData().getAddress().toString());
                                        editor.putInt(contants.id, modifiy.getData().getId());
                                        editor.putString(contants.image, modifiy.getData().getImage());
                                        editor.apply();
                                        editor.commit();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getContext(),MainActivity.class));
                                        getActivity().finish();
                                    } else {
                                        progressDialog.dismiss();
                                        JSONArray array = jsonObject.getJSONArray("errors");
                                        for (int i = 0; i < array.length(); i++) {
                                            String name = array.getString(i);
                                            if (name.contains("name")) {
                                                EdName.setError("هذا الاسم مستخدم من قبل");
                                            }
                                            if (name.contains("email")) {
                                                EdEmail.setError("هذا البريد مستخدم من قبل");
                                            }
                                        }
                                    }
                                }else if(jsonObject.has("status")){
                                    progressDialog.dismiss();
                                    JSONArray array = jsonObject.getJSONArray("errors");
                                    for (int i = 0; i < array.length(); i++) {
                                        String name = array.getString(i);
                                        if (name.contains("name")) {
                                            EdName.setError("هذا الاسم مستخدم من قبل");
                                        }
                                        if (name.contains("email")) {
                                            EdEmail.setError("هذا البريد مستخدم من قبل");
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                Log.e("Error register", e.getMessage().toString());
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            // your code here
                            progressDialog.dismiss();
                        }
                    })
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
//        Toast.makeText(this, "Error"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.e("Error register", e.getMessage().toString());
            /**/
        }
        }
    }

    private boolean init() {
        if(EdEmail.getText().toString().isEmpty()){
            EdEmail.setError(getString(R.string.Ederror));
            return false;
        }
        if(EdName.getText().toString().isEmpty()){
            EdName.setError(getString(R.string.Ederror));
            return false;
        }
        if(EdPhone.getText().toString().isEmpty()){
            EdLocation.setError(getString(R.string.Ederror));
            return false;
        }
        if(location.isEmpty()){
            Toast.makeText(getContext(), "اختر الموقع !", Toast.LENGTH_SHORT).show();
        }
        email = EdEmail.getText().toString();
        name = EdName.getText().toString();
        phone = EdPhone.getText().toString();
        return true;
    }

    @OnClick(R.id.modTxtaccountlocation)
    public void getlocation(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    @OnClick({R.id.photousermod,R.id.addimagemod})
    public void chageImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), personalcode);
    }
    @BindView(R.id.photousermod)
    ImageView imageView;


    public AccountModifiyFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSharedPreferences;
    public static AccountModifiyFragment newInstance() {
        return new AccountModifiyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modifiy_account, container, false);
        ButterKnife.bind(this,view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setIcon(R.drawable.logo1);
        progressDialog.setMessage(getString(R.string.loading));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EdEmail.setText(mSharedPreferences.getString(contants.email,""));
        EdName.setText(mSharedPreferences.getString(contants.username,""));
        EdPhone.setText(mSharedPreferences.getString(contants.phone,""));
        EdLocation.setText(mSharedPreferences.getString(contants.address,""));
        location = mSharedPreferences.getString(contants.address,"");
        xaddress= location;
        Picasso.get().load(mSharedPreferences.getString(contants.image,"")).placeholder(R.drawable.salon).into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
                // location.setText(place.getName().toString());
                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);

                    xaddress = addresses.get(0).getAddressLine(0);
                    if(addresses.get(0).getAddressLine(1)!=null) {
                        xcity = addresses.get(0).getAddressLine(1);
                    }
                    //String country = addresses.get(0).getAddressLine(2);
                    location = xaddress;
                    EdLocation.setText(xaddress);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        if (requestCode == personalcode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    //  Toast.makeText(getActivity(), "" + String.valueOf(count), Toast.LENGTH_SHORT).show();
                    //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if (Build.VERSION.SDK_INT < 11) {
                            Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), imageUri);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            // SDK >= 11 && SDK < 19
                            Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), imageUri);
                        } else {
                            // SDK > 19 (Android 4.4)
                            Onefilepath = RealPathUtil.getRealPathFromURI_API19(getContext(), imageUri);

                        }

                        //filePath.add(Onefilepath);
                        Log.e("file" + String.valueOf(Onefilepath), imageUri.toString());
                    }
                    //  Toast.makeText(getActivity(), "" + imagesList.size(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                } else if (data.getData() != null) {
                    //filePath = getUriRealPath(getContext(),picUri);
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        // SDK >= 11 && SDK < 19

                        Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), data.getData());
                    } else {
                        // SDK > 19 (Android 4.4)
                        Onefilepath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
//                        Onefilepath = data.getData().getPath();

                    }
                    // filePath.add(Onefilepath);
                    personalImage = Onefilepath;
                    File file = new File(personalImage);
                    Picasso.get().load(file).into(imageView);
                    Log.e("file", Onefilepath.toString());

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
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
