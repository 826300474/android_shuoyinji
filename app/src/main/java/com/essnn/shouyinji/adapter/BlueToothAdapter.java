package com.essnn.shouyinji.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.essnn.shouyinji.R;
import java.util.List;

public class BlueToothAdapter extends BaseQuickAdapter<BluetoothDevice,BaseViewHolder> {

    public BlueToothAdapter(int layoutResId, @Nullable List<BluetoothDevice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BluetoothDevice item) {
        helper.setText(R.id.name, item.getName());
    }
}
