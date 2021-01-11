package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;


public class WriteTagActivity extends AppCompatActivity {

    private Button btn_read_TID;
    private Button btn_write_TID;
    private TextView tv_read_TID;
    
    private RFIDWithUHF mReader;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_tag);
        tv_read_TID = (TextView) findViewById(R.id.tv_read_TID);
        btn_read_TID = (Button) findViewById(R.id.btn_read_TID);
        btn_read_TID.setOnClickListener(new MyOnclickListener());
        btn_write_TID = (Button) findViewById(R.id.btn_write_TID);
        btn_write_TID.setOnClickListener(new MyOnclickListener());
    }

    private class MyOnclickListener implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_read_TID:
                    try {
                        mReader = RFIDWithUHF.getInstance();
                    } catch (ConfigurationException e) {
                        e.printStackTrace();
                    }
                    mReader.init();
                    String res = mReader.inventorySingleTag();
                    System.out.println(res);
                    tv_read_TID.setText(res);
                    break;
                case R.id.btn_write_TID:
                    break;
            }
        }
    }
}