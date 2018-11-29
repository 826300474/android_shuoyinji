package com.essnn.shouyinji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.essnn.shouyinji.R;
import com.essnn.shouyinji.entity.ShopData;

import java.util.List;

public class ShopAdapter extends BaseAdapter{

    private Context mContext;
    private List<ShopData> mList;
    private LayoutInflater inflater;
    private ShopData data;

    public ShopAdapter(Context mContext, List<ShopData> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.shop_item,null);
            viewHolder.shop_name = (TextView) view.findViewById(R.id.shop_name);
            viewHolder.shop_jiage = (TextView) view.findViewById(R.id.shop_jiage);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ShopData data = mList.get(i);
        viewHolder.shop_name.setText(data.getName()+"");
        viewHolder.shop_jiage.setText(data.getMon()+"");
        return view;
    }

    class ViewHolder{
        private TextView shop_name;
        private TextView shop_jiage;
    }
}
