package com.designsapp.thebeatueart.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class resetpassword extends StringRequest {
    private static final String images_url= "http://toegy.net/salon/api/sendmail";
    private Map<String, String> params;

    public resetpassword(Response.Listener<String> listener, String email) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}