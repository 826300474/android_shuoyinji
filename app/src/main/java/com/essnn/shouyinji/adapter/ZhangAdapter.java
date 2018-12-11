package com.essnn.shouyinji.adapter;

import android.graphics.Color;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.entity.ZhangData;

import java.util.List;

public class ZhangAdapter extends BaseSectionQuickAdapter<ZhangData,BaseViewHolder> {
    private int defItem = 1;

    public ZhangAdapter(int layoutResId, int sectionHeadResId, List<ZhangData> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhangData item) {
        int index = helper.getAdapterPosition();
        if(index == defItem){
            helper.setBackgroundColor(R.id.box, Color.rgb(253,235,163));
        }else{
            helper.setBackgroundColor(R.id.box, Color.rgb(255,255,255));
        }
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ZhangData item) {

    }

    public void setdefItem(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }
}
