//package com.designsapp.thebeatueart.Utils;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RatingBar;
//import android.widget.Toast;
//
//import com.eltamiuzcom.montegoon.Activity.CartActivity;
//import com.eltamiuzcom.montegoon.R;
//
//import net.gotev.uploadservice.MultipartUploadRequest;
//import net.gotev.uploadservice.ServerResponse;
//import net.gotev.uploadservice.UploadInfo;
//import net.gotev.uploadservice.UploadStatusDelegate;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.UUID;
//
//public class modifyClass extends Dialog implements View.OnClickListener {
//    public Context c;
//    public Dialog d;
//    public Button yes;
//    public RatingBar Rate;
//    public ProgressDialog pd;
//    public int rate;
//    public String userID,adID,adPrice,oQ;
//    public EditText EdNumber;
//    public Double price1,Qu,AcuPrice;
//    public int pos;
//
//    public modifyClass(Context a, String userId, String adId, String price, String oQ, int position) {
//        super(a);
//        // TODO Auto-generated constructor stub
//        this.c = a;
//        this.adID = adId;
//        this.userID = userId;
//        this.adPrice = price;
//        this.oQ = oQ;
//        price1 = Double.parseDouble(price);
//        Qu = Double.parseDouble(oQ);
//        AcuPrice = price1/Qu ;
//        this.pos = position;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.modifiy_layout);
//        yes = (Button) findViewById(R.id.submitrate1);
//        EdNumber = (EditText) findViewById(R.id.number1);
//        yes.setOnClickListener(this);
//        //Drawable drawable = Rate.getProgressDrawable();
//        pd = new ProgressDialog(c);
//        pd.setMessage("جارى التعديل");
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.submitrate1:
//                if(!EdNumber.getText().toString().isEmpty()){
//                    pd.show();
//                    pd.setCancelable(true);
//                    sendData(EdNumber.getText().toString());
//                }else{
//                    EdNumber.setError(getContext().getString(R.string.error));
//                }
//                break;
//            default:
//                break;
//        }
//        dismiss();
//    }
//
//    private void sendData(final String edemail) {
//        Double numer = Double.parseDouble(edemail);
//        //code to do the HTTP request
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            Log.e("O Mostafa", "MultiRequest");
//            //Creating a multi part request
//            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(c, uploadId, "https://muntijin.net/api/updateqty")
//                    .addParameter("user_id", userID)
//                    .addParameter("cart_id",adID)
//                    .addParameter("qty",String.valueOf(edemail))
//                    .addParameter("price",String.valueOf(AcuPrice*Double.parseDouble(edemail)));
//            MultipartUploadRequest uploadRequest = multipartUploadRequest;
//            uploadRequest.setUtf8Charset();
//            uploadRequest.setMaxRetries(10);
//            uploadRequest.setDelegate(new UploadStatusDelegate() {
//                @Override
//                public void onProgress(Context context, UploadInfo uploadInfo) {
//                    // your code here
//                }
//
//                @Override
//                public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
//                                    Exception exception) {
//                    // your code here
//                    Toast.makeText(c, c.getString(R.string.Error), Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
//                }
//
//                @Override
//                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//                    // your code here
//                    // if you have mapped your server response to a POJO, you can easily get it:
//                    // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
//                    pd.dismiss();
//                    try {
//                        JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
//                        boolean success = jsonObject.getBoolean("message");
//                        if (success) {
//                            Toast.makeText(c, "تم التعديل", Toast.LENGTH_SHORT).show();
//                            CartActivity.montages.get(pos).setPrice(String.valueOf(AcuPrice*Double.parseDouble(edemail)));
//                            CartActivity.montages.get(pos).setQ(String.valueOf(Double.parseDouble(edemail)));
//                            CartActivity.refresh();
//                        } else {
//                            Toast.makeText(c, "لم يتم التعديل", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onCancelled(Context context, UploadInfo uploadInfo) {
//                    // your code here
//                }
//            });
//            String request = uploadRequest.startUpload();
//            //Toast.makeText(logupActivity.this, "جارى التسجيل ", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//
//        }
//    }
//
//
//}
//
