package com.essnn.shouyinji.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.entity.ShopData;

import java.util.List;

public class ShopAdapter extends BaseQuickAdapter<ShopData,BaseViewHolder> {

    public ShopAdapter(int layoutResId, @Nullable List<ShopData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopData item) {
        if(item.getState() != null){
            helper.setGone(R.id.box,item.getState());
        }

        helper.setText(R.id.shop_name,item.getName());
        helper.setText(R.id.shop_jiage, ""+ item.getMoney());
        Glide.with(mContext).load(item.getPic().getFileUrl()).into((ImageView) helper.getView(R.id.shop_pic));
    }
}
