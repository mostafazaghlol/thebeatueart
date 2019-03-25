package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getmessage extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/salon_messages";
    private Map<String, String> params;

    public getmessage(Response.Listener<String> listener, String user_id, String salon_id) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("salon_id",salon_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}