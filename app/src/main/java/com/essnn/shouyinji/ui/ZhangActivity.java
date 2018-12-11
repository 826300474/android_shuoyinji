package com.essnn.shouyinji.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.adapter.HaveshopAdapter;
import com.essnn.shouyinji.adapter.ZhangAdapter;
import com.essnn.shouyinji.entity.HaveshopData;
import com.essnn.shouyinji.entity.ShopData;
import com.essnn.shouyinji.entity.ZhangData;

import java.util.ArrayList;
import java.util.List;

public class ZhangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ZhangAdapter mZhangAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ZhangData> mList = new ArrayList<>();
    private List<HaveshopData> mHaveshopDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang);
        init();
        getshop();
    }

    private void getshop() {
        for (int i = 0; i < 20; i++) {
            HaveshopData data = new HaveshopData();
            mHaveshopDataList.add(data);
        }
        RecyclerView have_shop = findViewById(R.id.have_shop);
        have_shop.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        HaveshopAdapter haveshopAdapter = new HaveshopAdapter(R.layout.haveshop_item,mHaveshopDataList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        have_shop.setLayoutManager(manager);
        have_shop.setAdapter(haveshopAdapter);
        mZhangAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    private void init() {
        for (int i = 0; i < 20; i++) {
            if(i%4==0){
                ZhangData data = new ZhangData(true,"头部");
                mList.add(data);
            }else {
                ZhangData data = new ZhangData(false,"555");
                mList.add(data);
            }
        }
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mZhangAdapter = new ZhangAdapter(R.layout.zhang_item,R.layout.header_item,mList);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mZhangAdapter);
        mZhangAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mZhangAdapter.setdefItem(position);
            }
        });
        mZhangAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }


}
