package com.essnn.shouyinji;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.essnn.shouyinji.adapter.BuyAdapter;
import com.essnn.shouyinji.adapter.ShopAdapter;
import com.essnn.shouyinji.entity.BuyData;
import com.essnn.shouyinji.entity.ShopData;
import com.essnn.shouyinji.utils.CallBackInterface;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackInterface, View.OnClickListener {
    //列表
    private GridView mGridView;
    //数据
    private List<ShopData> mList = new ArrayList<>();
    //适配器
    private ShopAdapter mShopAdapter;

    private ListView mListView;

    private List<BuyData> mBuy = new ArrayList<>();

    private BuyAdapter mBuyAdapter;

    private TextView text_zongji,text_clear,text_guadan,text_huiyuan;

    private BigDecimal buy_zongji;

    private Button buy_jiesuan;

    private TabLayout tabLayout;

    private String[] titles = new String[]{"全部","零食","小吃"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        text_zongji = findViewById(R.id.buy_zongji);
        buy_jiesuan = findViewById(R.id.buy_jiesuan);
        text_clear = findViewById(R.id.text_clear);
        text_guadan = findViewById(R.id.text_guadan);
        text_huiyuan = findViewById(R.id.text_huiyuan);
        tabLayout = findViewById(R.id.tabLayout);
        text_zongji.setText("总计：0");
        buy_jiesuan.setText("结算(0)");

        text_clear.setOnClickListener(this);

        for (int i = 0; i < titles.length ; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(titles[i]);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int i = 0; i < 20; i++) {
            ShopData data = new ShopData();
            data.setName("name" + i);
            data.setMon(new BigDecimal(0.5));
            mList.add(data);
        }
        mGridView = findViewById(R.id.mGridView);
        mShopAdapter = new ShopAdapter(this, mList);
        //设置适配器
        mGridView.setAdapter(mShopAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShopData shopdata = mList.get(i);
                for (int j = 0; j < mBuy.size(); j++) {
                    if(mBuy.get(j).getName() == shopdata.getName()){
                        int num = mBuy.get(j).getNum() + 1;
                        BigDecimal danjia = mBuy.get(j).getMon();
                        BigDecimal zongji = new BigDecimal(num).multiply(danjia);
                        mBuy.get(j).setNum(num);
                        mBuy.get(j).setZongjia(zongji);
                        mBuyAdapter.notifyDataSetChanged();
                        get_zongji();
                        return;
                    }
                }
                BuyData buyData = new BuyData();
                buyData.setName(shopdata.getName());
                buyData.setMon(new BigDecimal("0.5"));
                buyData.setNum(1);
                buyData.setZongjia(new BigDecimal("0.5"));
                mBuy.add(0,buyData);
                mBuyAdapter.notifyDataSetChanged();
                get_zongji();
                get_zongnum();
            }
        });

        mListView = findViewById(R.id.mListView);
        mBuyAdapter = new BuyAdapter(this, mBuy,this);
        //设置适配器
        mListView.setAdapter(mBuyAdapter);

    }

    private void get_zongnum() {
        buy_jiesuan.setText("结算("+mBuy.size()+")" );
    }

    @Override
    public void callBackClick(int i,int tpye) {
        int num;
        if(tpye == 1){
            if(mBuy.get(i).getNum()==1){
                return;
            }
            num = mBuy.get(i).getNum() - 1;
        }else{
            num = mBuy.get(i).getNum() + 1;
        }
        BigDecimal danjia = mBuy.get(i).getMon();
        BigDecimal zongji = new BigDecimal(num).multiply(danjia);
        mBuy.get(i).setNum(num);
        mBuy.get(i).setZongjia(zongji);
        mBuyAdapter.notifyDataSetChanged();
        get_zongji();
    }

    private void get_zongji() {
        buy_zongji = new BigDecimal(0);
        for (int i = 0; i < mBuy.size(); i++) {
            buy_zongji = buy_zongji.add(mBuy.get(i).getZongjia());
        }
        text_zongji.setText("总计："+ buy_zongji);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_clear:
                buy_zongji = new BigDecimal(0);
                text_zongji.setText("总计："+ buy_zongji);
                mBuy.clear();
                mBuyAdapter.notifyDataSetChanged();
                break;
            case R.id.text_guadan:

                break;
            case R.id.text_huiyuan:

                break;
        }
    }
}
