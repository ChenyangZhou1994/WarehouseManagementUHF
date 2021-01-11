package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //把布局文件加载到界面上
        setContentView(R.layout.activity_main);
        //找到相应的控件
        //标签写入
        ImageButton ib_writeTag = (ImageButton) findViewById(R.id.imageButton_writeTag);
        ib_writeTag.setOnClickListener(new MyOnclickListener());
        //查询记录
        ImageButton ib_history = (ImageButton) findViewById(R.id.imageButton_history);
        ib_history.setOnClickListener(new MyOnclickListener());
        //出库盘点
        ImageButton ib_ckpd = (ImageButton) findViewById(R.id.imageButton_ckpd);
        ib_ckpd.setOnClickListener(new MyOnclickListener());
        //下道盘点
        ImageButton ib_xdpd = (ImageButton) findViewById(R.id.imageButton_xdpd);
        ib_xdpd.setOnClickListener(new MyOnclickListener());
        //返程盘点
        ImageButton ib_fcpd = (ImageButton) findViewById(R.id.imageButton_fcpd);
        ib_fcpd.setOnClickListener(new MyOnclickListener());
        //归库盘点
        ImageButton ib_gkpd = (ImageButton) findViewById(R.id.imageButton_gkpd);
        ib_gkpd.setOnClickListener(new MyOnclickListener());
        //设置功率
        Button btn_SetPower = (Button) findViewById(R.id.btn_SetPower);
        btn_SetPower.setOnClickListener(new MyOnclickListener());
    }

    private class MyOnclickListener implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            //View v参数 当控件被点击的时候，通过id来进行区分
            int id = v.getId();
            switch (id) {
                case R.id.imageButton_writeTag:
                    Intent intent = new Intent(MainActivity.this, WriteTagActivity.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton_history:
                    break;
                case R.id.imageButton_ckpd:
                    break;
                case R.id.imageButton_xdpd:
                    break;
                case R.id.imageButton_fcpd:
                    break;
                case R.id.imageButton_gkpd:
                    break;
                case R.id.btn_SetPower:
                    break;
            }
        }
    }
}