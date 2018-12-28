package com.essnn.shouyinji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.essnn.shouyinji.MainActivity;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.R2;
import com.essnn.shouyinji.entity._User;
import com.essnn.shouyinji.utils.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity{
    @BindView(R2.id.username)
    public EditText my_username;
    @BindView(R2.id.password)
    public EditText my_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        request();
    }

//    public void request() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://wthrcdn.etouch.cn/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        RxWeatherService rxWeatherService = retrofit.create(RxWeatherService.class);
//        rxWeatherService.getMessage("北京")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<WeatherEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("wode", "onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("wode", "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(WeatherEntity weatherEntity) {
//                        Log.e("wode", "response == " + weatherEntity.getData().getGanmao());
//                    }
//                });
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick(R2.id.login_button)
    public void login() {
        final String name = my_username.getText().toString().trim();
        final String password = my_password.getText().toString().trim();
        if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
            final _User user = new _User();
            user.setUsername(""+ name);
            user.setPassword(""+ password);
            user.login(new SaveListener<_User>() {
                @Override
                public void done(_User user, BmobException e) {
                    if(e==null){
                        _User _user = BmobUser.getCurrentUser(_User.class);
                        ShareUtils.putString(getApplicationContext(),"username", name );
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "登录失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }

    }
}
