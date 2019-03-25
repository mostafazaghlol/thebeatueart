package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class profile extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/profile";
    private Map<String, String> params;

    public profile(Response.Listener<String> listener, String user) {
        super(Method.POST, images_url, listener,null);
        params = new HashMap<>();
        params.put("user_id",user);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}