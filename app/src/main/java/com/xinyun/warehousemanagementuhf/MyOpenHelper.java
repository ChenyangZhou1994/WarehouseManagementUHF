package com.xinyun.warehousemanagementuhf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(@Nullable Context context) {
        super(context, "Scan.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库文件第一次创建就会调用该方法，用于创建数据库。
        //存储都是字符串
        //数据库初始化
        db.execSQL("CREATE TABLE SBMXB(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, SBID VARCHAR(50), SBMC VARCHAR(50))");
        db.execSQL("CREATE TABLE PDB(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, DH VARCHAR(50), PDSJ VARCHAR(50), PDLX VARCHAR(50), PDR VARCHAR(50))");
        db.execSQL("CREATE TABLE PDXD(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, DH VARCHAR(50), SBID VARCHAR(50), SFZK INTERGER(2))");
        System.out.println("OnCreate被调用");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库操作
        //添加表结构

    }
}
