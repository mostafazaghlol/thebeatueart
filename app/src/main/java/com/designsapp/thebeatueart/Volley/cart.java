package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class cart extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/cart";
    private Map<String, String> params;

    public cart(Response.Listener<String> listener, String user_id) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}