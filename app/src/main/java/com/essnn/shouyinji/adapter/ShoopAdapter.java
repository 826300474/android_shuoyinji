package com.essnn.shouyinji.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.entity.ShopData;

import java.util.List;

public class ShoopAdapter extends RecyclerView.Adapter<ShoopAdapter.ViewHolder>{
    private Context context;
    private List<ShopData> data;

    public ShoopAdapter(Context context,List<ShopData> data){
        this.context = context;
        this.data = data;

    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.money.setText(""+data.get(position).getMoney());
        if(data.get(position).getState()!=null){
            holder.setVisibility(data.get(position).getState());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView money;
        private ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shop_name);
            money = itemView.findViewById(R.id.shop_jiage);
            pic = itemView.findViewById(R.id.shop_pic);
        }

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            if (isVisible) {
                param.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;// 这里注意使用自己布局的根布局类型
                param.width = ConstraintLayout.LayoutParams.MATCH_PARENT;// 这里注意使用自己布局的根布局类型
                itemView.setVisibility(View.VISIBLE);
            } else {
                param.height = 0;
                param.width = 0;
                itemView.setVisibility(View.GONE);
            }
            itemView.setLayoutParams(param);
        }

    }
}
