package com.essnn.shouyinji.ui;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.essnn.shouyinji.utils.BlueTooth;

import java.io.IOException;


public abstract class BasePrintActivity extends BaseActivity {

    private BluetoothSocket mSocket;
    private AsyncTask mConnectTask;

    public abstract void onConnected(BluetoothSocket socket, int taskType);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean checkBluetoothState() {
        if (BlueTooth.isBluetoothOn()) {
            return true;
        } else {
            BlueTooth.openBluetooth(this);
            return false;
        }
    }

    protected void connectDevice(BluetoothDevice device, int taskType) {
        Log.i("wode", "onClick: 222");
        if (checkBluetoothState() && device != null) {
            mConnectTask = new ConnectBluetoothTask(taskType).execute(device);
        }
    }

    class ConnectBluetoothTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {

        int mTaskType;

        public ConnectBluetoothTask(int taskType) {
            this.mTaskType = taskType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... params) {
            if(mSocket != null){
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mSocket = BlueTooth.connectDevice(params[0]);
            if(mSocket != null){
                onConnected(mSocket, mTaskType);
            };
            return mSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket socket) {
            if (socket == null || !socket.isConnected()) {
                Toast.makeText(BasePrintActivity.this, "连接打印机失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BasePrintActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(socket);
        }
    }
}
