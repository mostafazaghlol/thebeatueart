
package com.designsapp.thebeatueart.Model.MSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("search")
    @Expose
    private List<Search_> search = null;

    public List<Search_> getSearch() {
        return search;
    }

    public void setSearch(List<Search_> search) {
        this.search = search;
    }

}
