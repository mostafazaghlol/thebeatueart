package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class salone_supservices extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/salone_supservices";
    private Map<String, String> params;

    public salone_supservices(Response.Listener<String> listener, String user_id,String service_id) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("service_id",service_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}