package com.essnn.shouyinji.ui;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.adapter.BlueToothAdapter;
import com.essnn.shouyinji.utils.BlueTooth;
import com.essnn.shouyinji.utils.Print;
import com.essnn.shouyinji.utils.ShareUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class BlueToothActivity extends BasePrintActivity {
    BlueToothAdapter mBlueToothAdapter;
    private RecyclerView recyclerView;
    List<BluetoothDevice> printerDevices;
    final static int TASK_TYPE_CONNECT = 1;
    final static int TASK_TYPE_PRINT = 2;
    int mSelectedPosition = -1;

    public void onConnected(BluetoothSocket socket, int taskType) {
        switch (taskType){
            case TASK_TYPE_PRINT:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
                Print.printTest(socket, bitmap);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillAdapter();
    }

    private void fillAdapter() {
        printerDevices = BlueTooth.getPairedPrinterDevices();
        mBlueToothAdapter.addData(printerDevices);

    }

    private void initViews() {
        recyclerView = findViewById(R.id.mList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mBlueToothAdapter = new BlueToothAdapter(R.layout.bluetooth_item,printerDevices);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mBlueToothAdapter);
        mBlueToothAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSelectedPosition = position;
                connectDevice(TASK_TYPE_PRINT);
            }
        });
    }

    private void connectDevice(int taskType){
        if(mSelectedPosition >= 0){
            BluetoothDevice device = mBlueToothAdapter.getItem(mSelectedPosition);
            if(device!= null)
            ShareUtils.putString(this,"bluetooth", device.getAddress());
            super.connectDevice(device, taskType);
        }else{
            Toast.makeText(this, "还未选择打印设备", Toast.LENGTH_SHORT).show();
        }
    }

}
