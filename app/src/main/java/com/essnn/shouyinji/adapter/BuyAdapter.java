package com.essnn.shouyinji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.essnn.shouyinji.R;
import com.essnn.shouyinji.entity.BuyData;
import com.essnn.shouyinji.utils.CallBackInterface;

import java.util.List;

public class BuyAdapter extends BaseAdapter {

    private Context mContext;
    private List<BuyData> mList;
    private LayoutInflater inflater;
    private BuyData data;
    private CallBackInterface mCallBackInterface;
    public BuyAdapter(Context mContext, List<BuyData> mList,CallBackInterface mCallBackInterface){
        this.mContext = mContext;
        this.mList = mList;
        this.mCallBackInterface = mCallBackInterface;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.buy_item,null);
            viewHolder.buy_name = (TextView) view.findViewById(R.id.buy_name);
            viewHolder.buy_mon = (TextView) view.findViewById(R.id.buy_mon);
            viewHolder.buy_num = (TextView) view.findViewById(R.id.buy_num);
            viewHolder.buy_zongjia = (TextView) view.findViewById(R.id.buy_zongjia);
            viewHolder.num_add = (ImageView)view.findViewById(R.id.num_add);
            viewHolder.num_remove = (ImageView)view.findViewById(R.id.num_remove);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        BuyData data = mList.get(i);
        viewHolder.buy_name.setText(data.getName());
        viewHolder.buy_mon.setText(data.getMon()+"");
        viewHolder.buy_num.setText(data.getNum()+"");
        viewHolder.buy_zongjia.setText(data.getZongjia()+"");

        viewHolder.num_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBackInterface.callBackClick(i,0);
            }
        });
        viewHolder.num_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBackInterface.callBackClick(i,1);
            }
        });


        return view;
    }

    class ViewHolder{
        private TextView buy_name;
        private TextView buy_mon;
        private TextView buy_num;
        private TextView buy_zongjia;
        private ImageView num_add;
        private ImageView num_remove;
    }
}
