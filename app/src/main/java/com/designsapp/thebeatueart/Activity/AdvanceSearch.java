package com.designsapp.thebeatueart.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;

import butterknife.ButterKnife;

public class AdvanceSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
    }
}
