package com.designsapp.thebeatueart.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;
import com.designsapp.thebeatueart.Volley.terms;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShrootActivity extends AppCompatActivity {
    @BindView(R.id.html_text_shroot)
    HtmlTextView htmlTextView;
    ProgressDialog progressDialog;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shroot);
        screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
        // loads html from string and displays cat_pic.png from the app's drawable folder
        htmlTextView.setHtml("الشروط والاحكام",
                new HtmlResImageGetter(htmlTextView));
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        addfackdata();
    }
    private void addfackdata() {
        progressDialog.show();
        terms mAbout = new terms(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    progressDialog.dismiss();
                    if(jsonObject.getJSONObject("data").has("content_en")){
                        content = jsonObject.getJSONObject("data").getString("content_en");
                        htmlTextView.setHtml(content,new HtmlResImageGetter(htmlTextView));
                    }
                    if(jsonObject.getJSONObject("data").has("content_ar")){
                        content = jsonObject.getJSONObject("data").getString("content_ar");
                        htmlTextView.setHtml(content,new HtmlResImageGetter(htmlTextView));
                    }
                }else{
                    progressDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mAbout);
    }
    public void back(View view) {
        onBackPressed();
    }
}
