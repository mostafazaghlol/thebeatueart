package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class about extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/aboutus?lang=ar";
    private Map<String, String> params;

    public about(Response.Listener<String> listener) {
        super(Method.GET, images_url, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}