package com.essnn.shouyinji.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essnn.shouyinji.R;
import com.essnn.shouyinji.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

public class XianFragment extends Fragment implements View.OnClickListener {
    Bundle bundle;
    String message;
    private EditText valYingshou,valShishou,valZhaolin;
    private TextView addEight,addSeven,addDian,jiesuan,addThree,addSix,addTwo,addFive,addZero,addOne,addFour,remove,addNine;
    private ImageView close;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xian,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        valYingshou = (EditText) view.findViewById(R.id.val_yingshou);
        valShishou = (EditText) view.findViewById(R.id.val_shishou);
        valZhaolin = (EditText) view.findViewById(R.id.val_zhaolin);
        addSeven = (TextView) view.findViewById(R.id.add_seven);
        addSeven.setOnClickListener(this);
        addEight = (TextView) view.findViewById(R.id.add_eight);
        addEight.setOnClickListener(this);
        addNine = (TextView) view.findViewById(R.id.add_nine);
        addNine.setOnClickListener(this);
        remove = (TextView) view.findViewById(R.id.remove);
        remove.setOnClickListener(this);
        addFour = (TextView) view.findViewById(R.id.add_four);
        addFour.setOnClickListener(this);
        addOne = (TextView) view.findViewById(R.id.add_one);
        addOne.setOnClickListener(this);
        addZero = (TextView) view.findViewById(R.id.add_zero);
        addZero.setOnClickListener(this);
        addFive = (TextView) view.findViewById(R.id.add_five);
        addFive.setOnClickListener(this);
        addTwo = (TextView) view.findViewById(R.id.add_two);
        addTwo.setOnClickListener(this);
        addSix = (TextView) view.findViewById(R.id.add_six);
        addSix.setOnClickListener(this);
        addThree = (TextView) view.findViewById(R.id.add_three);
        addThree.setOnClickListener(this);
        jiesuan = (TextView) view.findViewById(R.id.jiesuan);
        jiesuan.setOnClickListener(this);
        addDian = (TextView) view.findViewById(R.id.add_dian);
        addDian.setOnClickListener(this);
        close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        bundle = this.getArguments();
        message = bundle.getString("message");
        valYingshou.setText(message);
    }


    @Override
    public void onClick(View view) {
        String str = valShishou.getText().toString();
        BigDecimal yingshuo = new BigDecimal(valYingshou.getText().toString());
        BigDecimal zhaolin = null;
        BigDecimal shishou = null;
        switch (view.getId()) {
            case R.id.add_seven:
            case R.id.add_eight:
            case R.id.add_nine:
            case R.id.add_four:
            case R.id.add_one:
            case R.id.add_zero:
            case R.id.add_five:
            case R.id.add_two:
            case R.id.add_six:
            case R.id.add_three:
            case R.id.add_dian:
                str = str + ((TextView) view).getText();
                valShishou.setText(str);
                shishou = new BigDecimal(str);
                zhaolin = shishou.subtract(yingshuo);
                valZhaolin.setText(zhaolin.toString());
                if(shishou.compareTo(yingshuo) >= 0 ){
                    valZhaolin.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                }else{
                    valZhaolin.setTextColor(this.getResources().getColor(R.color.colorAccent));
                }
            break;
            case R.id.remove:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                    if(str.length() > 0 ){
                        shishou = new BigDecimal(str);
                        zhaolin = shishou.subtract(yingshuo);
                        valZhaolin.setText(zhaolin.toString());
                        if(shishou.compareTo(yingshuo) >= 0 ){
                            valZhaolin.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                        }else{
                            valZhaolin.setTextColor(this.getResources().getColor(R.color.colorAccent));
                        }
                    }else{
                        valShishou.setText("");
                        valZhaolin.setText("");
                    }
                    valShishou.setText(str);
                }else {
                    valShishou.setText("");
                    valZhaolin.setText("");
                }
                break;
            case R.id.close:
                EventBus.getDefault().post(new MessageEvent());
                break;
        }
    }
}
