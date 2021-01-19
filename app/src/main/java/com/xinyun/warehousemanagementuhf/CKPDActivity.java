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

    private MyOpenHelper openHelper;

    private Button btn_Start;
    private Button btn_CleanData;
    private ListView lv_data;

    private ArrayList<Tag> tags = new ArrayList<Tag>();

    private boolean loopFlag = false;
    private boolean isStop = false;

    private RFIDWithUHF mReader;


    //开始扫描和停止扫描状态
    private boolean pressedFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_k_p_d);

        openHelper = new MyOpenHelper(this);

        btn_Start = (Button) findViewById(R.id.btn_CKPD_Start);
        btn_Start.setOnClickListener(new MyOnclickListener());
        btn_CleanData = (Button) findViewById(R.id.btn_CKPD_CleanData);
        btn_CleanData.setOnClickListener(new MyOnclickListener());

        lv_data = (ListView) findViewById(R.id.lv_data);


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
            tv_Count.setText("1");

            return view;
        }
    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_CKPD_Start:
                    if (isStop) {
                        loopFlag = true;
                        btn_Start.setText("启动扫描");
                        isStop = false;
                        mReader.free();
                    }
                    else {
                        try {
                            mReader = RFIDWithUHF.getInstance();
                        } catch (ConfigurationException e) {
                            e.printStackTrace();
                        }
                        mReader.init();
                        System.out.println("Init");
                        readTag();
                        lv_data.setAdapter(new MyAdapter());
                    }
                    break;
                case R.id.btn_CKPD_CleanData:

                    break;
            }
        }
    }

    public void readTag() {
        if (mReader.startInventoryTag(0, 0)) {
            loopFlag = true;
            isStop = true;
            btn_Start.setText("停止扫描");
            new TagThread().start();
            System.out.println("StartScan");
        }
    }

    class TagThread extends Thread {
        public void run() {
            String[] res = null;
            while (loopFlag) {
                res = mReader.readTagFromBuffer();
                if (res != null) {
                    Tag tag = new Tag();
                    tag.SBID = res[0];
                    tags.add(tag);
                    System.out.println(res[0]);
                }
            }
        }
    }


}