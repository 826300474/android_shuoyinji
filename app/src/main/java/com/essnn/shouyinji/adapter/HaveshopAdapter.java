package com.essnn.shouyinji.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.essnn.shouyinji.entity.HaveshopData;
import com.essnn.shouyinji.entity.HeaderData;

import java.util.List;

public class HaveshopAdapter extends BaseQuickAdapter<HaveshopData,BaseViewHolder> {

    public HaveshopAdapter(int layoutResId, @Nullable List<HaveshopData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HaveshopData item) {

    }
}
