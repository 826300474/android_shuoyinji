package com.essnn.shouyinji.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.essnn.shouyinji.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShezhiActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        ButterKnife.bind(this);
        title.setText("设置");
    }

    @OnClick(R.id.go_back)
    public void goBack() {
        finish();
    }
}
