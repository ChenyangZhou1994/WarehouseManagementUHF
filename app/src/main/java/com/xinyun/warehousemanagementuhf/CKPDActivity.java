package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;

public class CKPDActivity extends AppCompatActivity {

    private Button btn_Start;
    private Button btn_CleanData;

    private RFIDWithUHF mReader;
    //
    //开始扫描和停止扫描状态
    private boolean pressedFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_k_p_d);

        btn_Start = (Button) findViewById(R.id.btn_CKPD_Start);
        btn_Start.setOnClickListener(new MyOnclickListener());
        btn_CleanData = (Button) findViewById(R.id.btn_CKPD_CleanData);
        btn_CleanData.setOnClickListener(new MyOnclickListener());

    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_CKPD_Start:
                    try {
                        mReader = RFIDWithUHF.getInstance();
                    } catch (ConfigurationException e) {
                        e.printStackTrace();
                    }
                    mReader.init();
                    if(mReader.startInventoryTag(0,0)) {
                        String res = mReader.readUidFromBuffer();
                        System.out.println(res);
                    }
                    mReader.stopInventory();
                    mReader.free();
                    break;
                case R.id.btn_CKPD_CleanData:
                    break;
            }
        }
    }

    public void readTag() {

    }

}