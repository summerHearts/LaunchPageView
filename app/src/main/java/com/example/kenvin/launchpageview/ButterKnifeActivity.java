package com.example.kenvin.launchpageview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Kenvin on 2017/11/6.
 */

public class ButterKnifeActivity extends AppCompatActivity {


    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;


    @OnClick(R.id.button1 )      //点击事件
    public void showToast(){
        Toast.makeText(this, " OnClick", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick( R.id.button1 )  //长按事件
    public boolean showToast2(){
        Toast.makeText(this, " OnLongClick", Toast.LENGTH_SHORT).show();
        return true  ;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterkinfe);
        ButterKnife.bind(this);
    }


}
