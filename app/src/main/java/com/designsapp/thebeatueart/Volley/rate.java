package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rate extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/rate";
    private Map<String, String> params;

    public rate(Response.Listener<String> listener, String user_id, String salon_id,String rate) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("salon_id",salon_id);
        params.put("rate",rate);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}