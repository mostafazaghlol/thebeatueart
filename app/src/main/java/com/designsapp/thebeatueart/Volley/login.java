package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class login extends StringRequest {
    private static final String images_url= "https://toegy.net/salon/api/login";
    private Map<String, String> params;

    public login(Response.Listener<String> listener, String user, String pasword) {
        super(Method.POST, images_url, listener,null);
        params = new HashMap<>();
        params.put("user",user);
        params.put("password",pasword);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}