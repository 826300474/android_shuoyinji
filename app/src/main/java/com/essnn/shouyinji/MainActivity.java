package com.essnn.shouyinji;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.essnn.shouyinji.adapter.BuyAdapter;
import com.essnn.shouyinji.adapter.ShopAdapter;
import com.essnn.shouyinji.entity.BuyData;
import com.essnn.shouyinji.entity.ShopData;
import com.essnn.shouyinji.fragment.MaFragment;
import com.essnn.shouyinji.fragment.PayFragment;
import com.essnn.shouyinji.fragment.SaoFragment;
import com.essnn.shouyinji.fragment.XianFragment;
import com.essnn.shouyinji.ui.BaseActivity;
import com.essnn.shouyinji.utils.AddShop;
import com.essnn.shouyinji.utils.GoPay;
import com.essnn.shouyinji.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
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

    PayFragment payFragment;
    XianFragment xianFragment;
    SaoFragment saoFragment;
    MaFragment maFragment;

    private String[] titles = new String[]{"全部","零食","小吃"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
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
        buy_jiesuan.setOnClickListener(this);

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
        mBuyAdapter = new BuyAdapter(this, mBuy);
        //设置适配器
        mListView.setAdapter(mBuyAdapter);
        //test
//        xianFragment = new XianFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("message","100");
//        xianFragment.setArguments(bundle);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.pay_fragment,xianFragment).commit();
//        mGridView.setVisibility(View.INVISIBLE);
    }

    private void get_zongnum() {
        buy_jiesuan.setText("结算("+mBuy.size()+")" );
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
            case R.id.buy_jiesuan:
                if(xianFragment==null){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    xianFragment = new XianFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("message",buy_zongji+"");
                    xianFragment.setArguments(bundle);
                    ft.add(R.id.xianjin_fragment,xianFragment).commit();
                    mGridView.setVisibility(View.INVISIBLE);

                    payFragment = new PayFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.pay_fragment,payFragment).commit();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get_addshop(AddShop addShop) {
        int i = addShop.getI();
        int type = addShop.getType();
        int num;
        if(type == 1){
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent){
        getSupportFragmentManager().beginTransaction().remove(xianFragment).commit();
        getSupportFragmentManager().beginTransaction().remove(payFragment).commit();
        mGridView.setVisibility(View.VISIBLE);
        xianFragment = null;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get_gopay(GoPay goPay){
        int i = goPay.getPay();
        switch (i){
            case 0:
                if(xianFragment==null){
                    xianFragment = new XianFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.xianjin_fragment,xianFragment).commit();
                break;
            case 1:
                if(saoFragment==null){
                    saoFragment = new SaoFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.xianjin_fragment,saoFragment).commit();
                break;
            case 2:
                if(maFragment==null){
                    maFragment = new MaFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.xianjin_fragment,maFragment).commit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
