package com.designsapp.thebeatueart.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Volley.rate;

import org.json.JSONException;
import org.json.JSONObject;

public class RateDialogClass extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes;
    public RatingBar Rate;
    public ProgressDialog pd;
    public int rate;
    public String userID,salonID;

    public RateDialogClass(Activity a, String id,String userID) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.salonID = id;
        this.userID = userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rate_layout);
        Rate = (RatingBar) findViewById(R.id.ratxing);
        Rate.setRating(0.0f);
//        yes.setOnClickListener(this);
        Drawable drawable = Rate.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FFFF00"), PorterDuff.Mode.SRC_ATOP);
        pd = new ProgressDialog(c);
        pd.setMessage("جارى ارسال التقييم ");
        Rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate mRate;
                mRate = new rate(response -> {
                    try {
                        pd.show();
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        if(success == 1){
                            dismiss();
                            pd.dismiss();
                            Toast.makeText(c, "تم اضافة التقييم", Toast.LENGTH_SHORT).show();
                        }else{
                            dismiss();
                            pd.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },userID,salonID,String.valueOf(rating));
                RequestQueue requestQueue = Volley.newRequestQueue(c);
                requestQueue.add(mRate);
//                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

//    private void sendData(final float edemail) {
//        //code to do the HTTP request
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            Log.e("O Mostafa", "MultiRequest");
//            //Creating a multi part request
//            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(c, uploadId, "http://muntijin.net/api/addrate")
//                    .addParameter("user_id", userID)
//                    .addParameter("item_id",adID)
//                    .addParameter("rate",String.valueOf(edemail))
//                    .addParameter("name","test");
//            String request = multipartUploadRequest
//                    .setUtf8Charset()
//                    .setMaxRetries(10)
//                    .setDelegate(new UploadStatusDelegate() {
//                        @Override
//                        public void onProgress(Context context, UploadInfo uploadInfo) {
//                            // your code here
//                        }
//
//                        @Override
//                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
//                                            Exception exception) {
//                            // your code here
//                            Toast.makeText(c, c.getString(R.string.Error), Toast.LENGTH_SHORT).show();
//                            pd.dismiss();
//                        }
//                        @Override
//                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//                            // your code here
//                            // if you have mapped your server response to a POJO, you can easily get it:
//                            // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
//                            pd.dismiss();
//                            try {
//                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
//                                boolean success = jsonObject.getBoolean("message");
//                                if (success) {
//                                    Toast.makeText(c, "تم اضافة تقيمك بنجاح", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(c, "لم يتم اضافة تقيمك", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(Context context, UploadInfo uploadInfo) {
//                            // your code here
//                        }
//                    })
//                    .startUpload();
//            //Toast.makeText(logupActivity.this, "جارى التسجيل ", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//
//        }
//    }


}

