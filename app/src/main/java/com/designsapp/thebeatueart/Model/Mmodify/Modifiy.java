
package com.designsapp.thebeatueart.Model.Mmodify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Modifiy {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
