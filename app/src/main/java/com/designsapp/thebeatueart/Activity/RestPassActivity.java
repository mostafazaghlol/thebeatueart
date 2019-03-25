package com.designsapp.thebeatueart.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Volley.checkcode;
import com.designsapp.thebeatueart.Volley.resetpassword;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestPassActivity extends AppCompatActivity {
    @BindView(R.id.EdResetemail)
    EditText EdEmail;
    Boolean first = true,second = true;
    @OnClick(R.id.Btsendpass)
    public void sendpass() {
            if(EdEmail.getText().toString().isEmpty()){
                EdEmail.setError(getString(R.string.Ederror));
                return;
            }else{
                progressDialog.show();
                if(first){
                    resetpassword mResetpassword = new resetpassword(response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if(success == 1){
                                progressDialog.dismiss();
                                ReFirst.setVisibility(View.INVISIBLE);
                                ToChange.setText("ادخل الكود");
                                Toast.makeText(RestPassActivity.this, "ادخل الكود", Toast.LENGTH_SHORT).show();
                                EdCode.setVisibility(View.VISIBLE);
                                first = false;
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RestPassActivity.this, "هذا البريد غير مسجل لدينا", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },EdEmail.getText().toString().trim());
                    RequestQueue requestQueue = Volley.newRequestQueue(RestPassActivity.this);
                    requestQueue.add(mResetpassword);
                }else{
                    checkcode mResetpassword = new checkcode(response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if(success == 1){
                                progressDialog.dismiss();
                              EdCode.setVisibility(View.INVISIBLE);
                              LiPass.setVisibility(View.VISIBLE);
                              second = false;
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(this, "هذا الكود غير صحيح ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },EdEmail.getText().toString(),EdCode.getText().toString());
                    RequestQueue requestQueue = Volley.newRequestQueue(RestPassActivity.this);
                    requestQueue.add(mResetpassword);
                }
                if(!second){
                    //change password.
                }
            }
            
            
    }

    @BindView(R.id.texttochange)
    TextView ToChange;
    @BindView(R.id.first)
    RelativeLayout ReFirst;
    @BindView(R.id.second)
    EditText EdCode;
    @BindView(R.id.EdResetpassword)
    EditText EdResetpass1;
    @BindView(R.id.EdResetpassword2)
    EditText EdResetpass2;
    @BindView(R.id.third)
    LinearLayout LiPass;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_pass);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        ReFirst.setVisibility(View.VISIBLE);
    }
}
