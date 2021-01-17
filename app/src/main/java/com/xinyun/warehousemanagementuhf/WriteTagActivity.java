package com.xinyun.warehousemanagementuhf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.deviceapi.exception.ConfigurationException;


public class WriteTagActivity extends AppCompatActivity {

    private Button btn_read_TID;
    private Button btn_write_TID;
    private TextView tv_read_TID;
    private EditText et_write_TID;
    
    private RFIDWithUHF mReader;
    private MyOpenHelper myOpenHelper = new MyOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_tag);
        tv_read_TID = (TextView) findViewById(R.id.tv_read_TID);
        et_write_TID = (EditText) findViewById(R.id.et_write_TID);
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
                    mReader.free();
                    Toast.makeText(getApplicationContext(), "读标签成功!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_write_TID:
                    SQLiteDatabase database = myOpenHelper.getReadableDatabase();
                    String sql = "insert into SBMXB(SBID, SBMC) values (" + "'" +tv_read_TID.getText().toString()
                            + "','" + et_write_TID.getText().toString() + "')";
                    database.execSQL(sql);
                    System.out.println(et_write_TID.getText().toString());
                    database.close();
                    Toast.makeText(getApplicationContext(), "写标签成功!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}