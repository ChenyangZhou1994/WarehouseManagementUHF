package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.xinyun.warehousemanagementuhf.bean.Data;
import com.xinyun.warehousemanagementuhf.bean.Tag;

import java.util.ArrayList;
import java.util.Arrays;

public class CKPDActivity extends AppCompatActivity {

    private Button btn_Start;
    private Button btn_CleanData;
    private ListView lv_data;

    private ArrayList<Tag> tags = new ArrayList<Tag>();

    private boolean loopFlag = false;

    private RFIDWithUHF mReader;


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

        lv_data = (ListView) findViewById(R.id.lv_data);


        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        mReader.init();

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tags.size();
        }

        @Override
        public Object getItem(int position) {
            return tags.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                //说明没有可以复用的旧的View
                view = View.inflate(CKPDActivity.this, R.layout.tag_item, null);
            }
            else {
                //说明convertview不为空可以重新使用convertview
                view = convertView;
            }
            //找到要修改的对象
            TextView tv_Tag = (TextView) view.findViewById(R.id.tv_Tag);
            TextView tv_Count = (TextView) view.findViewById(R.id.tv_Count);


            //获取要显示的数据 拿着传入的position到数据集合中获取要展示的数据position就是当前条目在Listview中的索引
            //在展示数据的时候 第()个条目展示的就是集合中第()个元素

            Tag tag = tags.get(position);

            tv_Tag.setText(tag.SBID);
            tv_Count.setText(tag.SBMC);

            return view;
        }
    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_CKPD_Start:
                    readTag();
                    loopFlag = true;
                    lv_data.setAdapter(new MyAdapter());
                    break;
                case R.id.btn_CKPD_CleanData:
                    break;
            }
        }
    }

    public void readTag() {
        if (btn_Start.getText() == "停止扫描") {
            //停止识别
            mReader.stopInventory();
            btn_Start.setText("启动扫描");
            loopFlag = false;
            mReader.free();
            return;
        }
        if (!loopFlag) {
            if (mReader.startInventoryTag(0, 0)) {
                loopFlag = true;
                btn_Start.setText("停止扫描");
                continuousRead();
            }
        }
    }



    private void continuousRead() {
        new Thread(new MyThread()).start();
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            while (loopFlag) {
                String[] res = mReader.readTagFromBuffer();
                System.out.println(res[0]);
                Tag tag = new Tag();
                tag.SBMC = res[0];
                System.out.println(tag.SBMC);
                tags.add(tag);
            }
        }
    }

}