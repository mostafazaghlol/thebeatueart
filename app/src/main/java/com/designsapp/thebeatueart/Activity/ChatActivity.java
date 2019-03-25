package com.designsapp.thebeatueart.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Adapters.chatItemsAdapter;
import com.designsapp.thebeatueart.Model.chatModel;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.getmessage;
import com.designsapp.thebeatueart.Volley.profile;
import com.designsapp.thebeatueart.Volley.send_message;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    public chatItemsAdapter xchatItemsAdapter;
    private List<chatModel> chatmodelsList;
    private SharedPreferences mSharedPreferences;
    private String Sender_id,Reciever_id,Message,notificationId1,notificationId2,finalNotification,chat_id,SenderName;
    @BindView(R.id.EdMessage)
    EditText EdMessage;
    private String userid;
    private String salonId;

    @OnClick(R.id.sendMessage)
    public void sendmessage(){
        if(EdMessage.getText().toString().isEmpty()){
            EdMessage.setError(getString(R.string.Ederror));
            return;
        }else{
            send_message mSend_message = new send_message(response -> {
                getMessages();
                EdMessage.setText("");
            },userid,salonId,EdMessage.getText().toString());
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(mSend_message);
        }
    }

    @BindView(R.id.image1)
    ImageView ImMessage;
    @BindView(R.id.name)
    TextView TxtName;
    ProgressDialog progressDialog;
//    @BindView(R.id.sendMessage) ImageView ImSend;
    //@BindView(R.id.sendImage) ImageView ImSendImage;
//    @BindView(R.id.imagesRecycler) RecyclerView recyclerView;
    //DatabaseReference Dp,DpUser;
    private String MyNotification;
    private LinearLayoutManager VlayoutManager;
    private String message="";
    private ArrayList<File> files;
    //ImageUploadItemsAdapter imageUploadItemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
        files = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo1);
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        userid =String.valueOf(mSharedPreferences.getInt(contants.id,0));
        salonId =String.valueOf(getIntent().getStringExtra(contants.salonid));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
//        recyclerView.setLayoutManager(linearLayoutManager);
        Sender_id = getIntent().getStringExtra("sender_id");
        Reciever_id = getIntent().getStringExtra("reciever_id");
        notificationId1 = getIntent().getStringExtra(contants.notificationID);
        notificationId2 = getIntent().getStringExtra(contants.notificationID2);
        chat_id = getIntent().getStringExtra(contants.chat_id);
        message = getIntent().getStringExtra("message");
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        VlayoutManager = new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,true);
        chatRecyclerView = findViewById(R.id.chatrecycler);
        chatRecyclerView.setLayoutManager(VlayoutManager);
        chatmodelsList = new ArrayList<>();
        xchatItemsAdapter = new chatItemsAdapter(this,chatmodelsList);
        chatRecyclerView.setAdapter(xchatItemsAdapter);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        getprofile();
    }
    String imageurl;
    private void getprofile() {
        profile mProfile = new profile(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    JSONObject user = jsonObject.getJSONObject("user");
                    String name = user.getString("name");
                    imageurl = user.getString("image");
                    TxtName.setText(name);
                    Picasso.get().load(imageurl).placeholder(R.drawable.salon).into(ImMessage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },salonId);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mProfile);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                getMessages();

            }
        });
    }

    private void getMessages() {
        chatmodelsList.clear();
        progressDialog.show();
        getmessage mGetmessages = new getmessage(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("data")){
                    JSONArray data = jsonObject.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        if(jsonObject1.getString("sender").equals("user")){
                            chatmodelsList.add(new chatModel(jsonObject1.getString("message"),1,imageurl));
                            xchatItemsAdapter.notifyDataSetChanged();
                        }else{
                            chatmodelsList.add(new chatModel(jsonObject1.getString("message"),0,imageurl));
                            xchatItemsAdapter.notifyDataSetChanged();
                        }
                        progressDialog.dismiss();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        },userid,salonId);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mGetmessages);
    }






    public void backMessage(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
