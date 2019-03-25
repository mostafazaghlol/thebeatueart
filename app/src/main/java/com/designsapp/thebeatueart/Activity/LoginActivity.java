package com.designsapp.thebeatueart.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Model.MLogin.Login;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.login;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private String email,pass;
    private ProgressDialog progressDialog;
    @OnClick(R.id.Txtforgetpass)
    public void goForgetpass(){
        startActivity(new Intent(this,RestPassActivity.class));
    }
    @OnClick(R.id.TxtRegister)
    public void goRegister(){
        startActivity(new Intent(this,RegisterActivity.class));
    }
    @OnClick(R.id.Btlogin)
    public void getLogin(){
        if(init()){
            progressDialog.show();
            login mLogin = new login(response -> {
                try {
//                    Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")){
                        Login login = new Gson().fromJson(response, Login.class);
                        editor.putString(contants.username, login.getUser().getName());
                        editor.putString(contants.email, login.getUser().getEmail());
                        editor.putString(contants.phone, login.getUser().getPhone());
                        editor.putString(contants.role, login.getUser().getTypeUser().toString());
                        editor.putString(contants.address, login.getUser().getAddress().toString());
                        editor.putInt(contants.id, login.getUser().getId());
                        editor.putString(contants.image, login.getUser().getImage());
                        editor.apply();
                        editor.commit();
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "تأكد من البيانات ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, email, pass);
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(mLogin);
        }

    }

    private boolean init() {
        if(Edemail.getText().toString().isEmpty()){
            Edemail.setError(getString(R.string.Ederror));
            return false;
        }
        if(Edpassword.getText().toString().isEmpty()){
            Edpassword.setError(getString(R.string.Ederror));
            return false;
        }
        email = Edemail.getText().toString().trim();
        pass = Edpassword.getText().toString();

        return true;
    }

    @BindView(R.id.EdLoginemail)
    EditText Edemail;
    @BindView(R.id.EdLoginpassword)
    EditText Edpassword;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSharedPreferences  = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        if(mSharedPreferences.contains(contants.id)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
