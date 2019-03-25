package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class home extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/home";
    private Map<String, String> params;

    public home(Response.Listener<String> listener, String lat, String lng) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("lat",lat);
        params.put("lang",lng);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}