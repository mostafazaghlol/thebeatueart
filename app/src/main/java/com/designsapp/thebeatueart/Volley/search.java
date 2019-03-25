package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class search extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/search";
    private Map<String, String> params;

    public search(Response.Listener<String> listener, String name) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("name",name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}