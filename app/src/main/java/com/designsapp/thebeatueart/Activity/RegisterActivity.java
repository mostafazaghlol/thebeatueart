package com.designsapp.thebeatueart.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.RealPathUtil;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.EdSignupname)
    EditText Edname;
    @BindView(R.id.EdSignupemail)
    EditText EdEmail;
    @BindView(R.id.EdSignuppassword)
    EditText EdPassword;
    @BindView(R.id.EdSignuppassword2)
    EditText EdPassword2;
    @BindView(R.id.EdSignupphone)
    EditText EdPhone;
    @OnClick(R.id.TxtShroot)
    public void goShroot(){
        startActivity(new Intent(this,ShrootActivity.class));
    }
    private String lat, lng, xaddress, xcity;
    @BindView(R.id.title3)
    TextView Textlocation;
    private String firebaseToken;

    @OnClick({R.id.ReSignupLocation,R.id.title3})
    public void getLocaiton() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @BindView(R.id.radioaccapt)
    CheckBox RadioAccapt;
    private int personalcode = 1000;
    private String Onefilepath;
    private String personalImage = "", name = "", email = "", pass = "", pass2 = "", phone = "", location = "";
    private ProgressDialog progressDialog;
    @BindView(R.id.photouseradd)
    ImageView imageView;

    @OnClick({R.id.photouseradd, R.id.photouseradd2})
    public void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), personalcode);
    }

    @OnClick(R.id.Btsignup)
    public void signup() {
        if (init()) {
            sendRequest();
        }
    }

    private void sendRequest() {
        try {
            progressDialog.show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(RegisterActivity.this, uploadId, "https://toegy.net/salon/api/register")
                    .addParameter("email", email)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("password", pass)
                    .addParameter("password_confirmation", pass)
                    .addParameter("address", xaddress)
                    .addParameter("lat", lat)
                    .addParameter("long", lng)
                    .addParameter("device_type","android")
                    .addParameter("device_token",firebaseToken);
           // multipartUploadRequest.addFileToUpload(Onefilepath, "image");
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
//                            Toast.makeText(RegisterActivity.this, serverResponse.getBodyAsString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                String success = jsonObject.getString("status");
                                if (success.equals("1")) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    JSONArray array = jsonObject.getJSONArray("errors");
                                    for (int i = 0; i < array.length(); i++) {
                                        String name = array.getString(i);
                                        if (name.contains("name")) {
                                            Edname.setError("هذا الاسم مستخدم من قبل");
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

    private boolean init() {
//        if (personalImage.isEmpty()) {
//            Toast.makeText(this, "اختر الصورة الشخصيه ", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (Edname.getText().toString().isEmpty()) {
            Edname.setError(getString(R.string.Ederror));
            return false;
        }
        if (EdEmail.getText().toString().isEmpty()) {
            EdEmail.setError(getString(R.string.Ederror));
            return false;
        }
        if (EdPhone.getText().toString().isEmpty()) {
            EdPhone.setError(getString(R.string.Ederror));
            return false;
        }
        if (EdPassword.getText().toString().isEmpty()) {
            EdPassword.setError(getString(R.string.Ederror));
            return false;
        }
        if (EdPassword2.getText().toString().isEmpty()) {
            EdPassword2.setError(getString(R.string.Ederror));
            return false;
        }
        pass = EdPassword.getText().toString();
        pass2 = EdPassword2.getText().toString();
        if (!pass.equals(pass2)) {
            EdPassword2.setError(getString(R.string.EderrorPass));
            return false;
        }
        name = Edname.getText().toString();
        email = EdEmail.getText().toString().trim();
        phone = EdPhone.getText().toString();
        if(!RadioAccapt.isChecked()){
            Toast.makeText(this, "يجب الموافقه على الشروط والاحكام", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setIcon(R.drawable.logo1);
        progressDialog.setMessage(getString(R.string.loading));
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        firebaseToken = token;

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == personalcode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    //  Toast.makeText(getActivity(), "" + String.valueOf(count), Toast.LENGTH_SHORT).show();
                    //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if (Build.VERSION.SDK_INT < 11) {
                            Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(RegisterActivity.this, imageUri);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            // SDK >= 11 && SDK < 19
                            Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(RegisterActivity.this, imageUri);
                        } else {
                            // SDK > 19 (Android 4.4)
                            Onefilepath = RealPathUtil.getRealPathFromURI_API19(RegisterActivity.this, imageUri);

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
                        Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(RegisterActivity.this, data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        // SDK >= 11 && SDK < 19

                        Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(RegisterActivity.this, data.getData());
                    } else {
                        // SDK > 19 (Android 4.4)
                        Onefilepath = RealPathUtil.getRealPathFromURI_API19(RegisterActivity.this, data.getData());
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
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
                // location.setText(place.getName().toString());
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);

                    xaddress = addresses.get(0).getAddressLine(0);
                    if(addresses.get(0).getAddressLine(1)!=null) {
                        xcity = addresses.get(0).getAddressLine(1);
                    }
                    //String country = addresses.get(0).getAddressLine(2);
                    location = xaddress;
                    Textlocation.setText(xaddress);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }

}
