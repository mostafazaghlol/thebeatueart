package com.designsapp.thebeatueart.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplachActivity extends AppCompatActivity {

    private static final int RC_CAMERA_AND_LOCATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        screenwithoutAction.FullScreen(this);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            new AlertDialog.Builder(SplachActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }else{
            methodRequiresTwoPermission();
        }
    }
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...

            doFirstWork();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.request),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

        if(EasyPermissions.hasPermissions(SplachActivity.this,perms)){
            doFirstWork();
        }else{
            methodRequiresTwoPermission();
        }
    }
    public void doFirstWork() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            //Do something after 100ms
//            Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplachActivity.this,LoginActivity.class));

          //  handler.postDelayed(this, 2000);
        }, 1500);

        }
    }



