package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xinyun.warehousemanagementuhf.bean.Data;

import java.util.ArrayList;

public class ScanHistoryActivity extends AppCompatActivity {

    private MyOpenHelper openHelper;
    private ArrayList<Data> datas = new ArrayList<Data>();
    private ListView lv_History;
    private Button btn_History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);

        btn_History = (Button) findViewById(R.id.btn_History);
        lv_History = (ListView) findViewById(R.id.lv_History);
        btn_History.setOnClickListener(new MyOnclickListener());
        openHelper = new MyOpenHelper(this);
        lv_History.setAdapter(new MyAdapter());

    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SQLiteDatabase database = openHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from PDB", null);

            while (cursor.moveToNext()) {
                Data data = new Data();
                data.DH = cursor.getString(2);
                data.PDSJ = cursor.getString(3);
                data.PDLX = cursor.getString(4);
                data.PDR = cursor.getString(5);
                datas.add(data);
            }
            cursor.close();
            database.close();

            for (Data data:datas) {
                System.out.println(data);
            }

        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
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
                view = View.inflate(ScanHistoryActivity.this, R.layout.data_item, null);
            }
            else {
                //说明convertview不为空可以重新使用convertview
                view = convertView;
            }
            //找到要修改的对象
            TextView tv_DH = (TextView) view.findViewById(R.id.tv_DH);
            TextView tv_PDSJ = (TextView) view.findViewById(R.id.tv_PDSJ);
            TextView tv_PDLX = (TextView) view.findViewById(R.id.tv_PDLX);
            TextView tv_PDR = (TextView) view.findViewById(R.id.tv_PDR);

            //获取要显示的数据 拿着传入的position到数据集合中获取要展示的数据position就是当前条目在Listview中的索引
            //在展示数据的时候 第()个条目展示的就是集合中第()个元素

            Data data = datas.get(position);

            tv_DH.setText(data.DH);
            tv_PDSJ.setText(data.PDSJ);
            tv_PDLX.setText(data.PDLX);
            tv_PDR.setText(data.PDR);

            return view;
        }
    }

}