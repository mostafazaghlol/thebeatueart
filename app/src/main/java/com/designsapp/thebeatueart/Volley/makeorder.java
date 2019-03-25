package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class makeorder extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/make_order";
    private Map<String, String> params;

    public makeorder(Response.Listener<String> listener, String user_id,String order_id,String sub_service_id,String date,String time,String phone,String people_number,String total_price) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("order id", order_id);
        params.put("sub_service_id", sub_service_id);
        params.put("date", date);
        params.put("time", time);
        params.put("phone",phone);
        params.put("people_number",people_number);
        params.put("total_price",total_price);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}