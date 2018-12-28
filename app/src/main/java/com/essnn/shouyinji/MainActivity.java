package com.essnn.shouyinji;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.essnn.shouyinji.adapter.BuyAdapter;
import com.essnn.shouyinji.adapter.ShopAdapter;
import com.essnn.shouyinji.entity.BuyData;
import com.essnn.shouyinji.entity.NavData;
import com.essnn.shouyinji.entity.ShopData;
import com.essnn.shouyinji.fragment.MaFragment;
import com.essnn.shouyinji.fragment.PayFragment;
import com.essnn.shouyinji.fragment.SaoFragment;
import com.essnn.shouyinji.fragment.XianFragment;
import com.essnn.shouyinji.ui.BasePrintActivity;
import com.essnn.shouyinji.ui.ShezhiActivity;
import com.essnn.shouyinji.ui.ZhangActivity;
import com.essnn.shouyinji.utils.AddShop;
import com.essnn.shouyinji.utils.GoPay;
import com.essnn.shouyinji.utils.MessageEvent;
import com.essnn.shouyinji.utils.Print;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends BasePrintActivity implements View.OnClickListener {
    //列表
    private RecyclerView mGridView;
    //数据
    private List<ShopData> mList = new ArrayList<>();
    //适配器
    private ShopAdapter mShopAdapter;

    private ListView mListView;

    private List<BuyData> mBuy = new ArrayList<>();

    private List<NavData> mNav = new ArrayList<>();

    private BuyAdapter mBuyAdapter;

    private TextView text_zongji,text_clear,text_guadan,text_huiyuan,text_shezhi,text_zhang;

    private BigDecimal buy_zongji;

    private Button buy_jiesuan;

    private TabLayout tabLayout;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    PayFragment payFragment;
    XianFragment xianFragment;
    SaoFragment saoFragment;
    MaFragment maFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        findView();

//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        device = mBluetoothAdapter.getRemoteDevice(ShareUtils.getString(this,"bluetooth","555"));
//        super.connectDevice( device , 2);
    }

    public void onConnected(BluetoothSocket socket, int taskType) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Print.printTest(socket, bitmap);
    }

    private void findView() {
        text_zongji = findViewById(R.id.buy_zongji);
        buy_jiesuan = findViewById(R.id.buy_jiesuan);
        text_clear = findViewById(R.id.text_clear);
        text_guadan = findViewById(R.id.text_guadan);
        text_huiyuan = findViewById(R.id.text_huiyuan);
        tabLayout = findViewById(R.id.tabLayout);
        text_shezhi = findViewById(R.id.shezhi);
        text_zhang = findViewById(R.id.zhangdan);
        mListView = findViewById(R.id.mListView);
        mGridView = findViewById(R.id.mGridView);
        initNav();
        text_zongji.setText("总计：0");
        buy_jiesuan.setText("结算(0)");
        text_shezhi.setOnClickListener(this);
        text_zhang.setOnClickListener(this);
        text_clear.setOnClickListener(this);
        buy_jiesuan.setOnClickListener(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = mNav.get(tab.getPosition()).getNum();
                setShop(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(),4);
        mShopAdapter = new ShopAdapter(R.layout.shop_item,mList);
        mGridView.setLayoutManager(manager);
        mGridView.setAdapter(mShopAdapter);
        mShopAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        BmobQuery<ShopData> query = new BmobQuery<ShopData>();
        query.addWhereEqualTo("username","18367458480").findObjects(new FindListener<ShopData>() {
            @Override
            public void done(List<ShopData> list, BmobException e) {
                mList = list;
                mShopAdapter.addData(list);
            }
        });

        mShopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
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
                buyData.setMon(new BigDecimal(String.valueOf(shopdata.getMoney())));
                buyData.setNum(1);
                buyData.setZongjia(new BigDecimal(String.valueOf(shopdata.getMoney())));
                mBuy.add(0,buyData);
                mBuyAdapter.notifyDataSetChanged();
                get_zongji();
                get_zongnum();
            }
        });


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
    //设置导航
    private void setShop(int i){
        for (int j = 0; j < mList.size(); j++) {
            if( mList.get(j).getClass_no() == i){
                mList.get(j).setState(false);
            }else{
                mList.get(j).setState(true);
            }
        }
        mShopAdapter.notifyDataSetChanged();
    }

    private void initNav() {
        BmobQuery<NavData> query = new BmobQuery<>();
        query.findObjects(new FindListener<NavData>() {
            @Override
            public void done(List<NavData> list, BmobException e) {
                mNav = list;
                for (int i = 0; i < list.size(); i++) {
                    TabLayout.Tab tab = tabLayout.newTab();
                    tab.setText(list.get(i).getName());
                    tabLayout.addTab(tab);
                }
            }
        });
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
                get_zongnum();
                break;
            case R.id.text_guadan:
                break;
            case R.id.text_huiyuan:

                break;
            case R.id.buy_jiesuan:
                if(mBuy.size() > 0){
                    if(xianFragment==null){
                        //打开收款页面
                        xianFragment = new XianFragment();
                        //传参
                        Bundle bundle = new Bundle();
                        bundle.putString("message",buy_zongji+"");
                        xianFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().add(R.id.xianjin_fragment,xianFragment).commit();
                        //隐藏商品
                        mGridView.setVisibility(View.INVISIBLE);
                        //付款导航
                        payFragment = new PayFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.pay_fragment,payFragment).commit();
                    }
                }else {
                    Toast.makeText(this, "请选择商品", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.shezhi:
                startActivity(new Intent(MainActivity.this,ShezhiActivity.class));
                break;
            case R.id.zhangdan:
                startActivity(new Intent(MainActivity.this,ZhangActivity.class));
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
