package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class checkcode extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/checkcode";
    private Map<String, String> params;

    public checkcode(Response.Listener<String> listener, String email,String code) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("code",code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}