package top.wjsaya.app.bilibili_parse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParseDatabaseOpenHelper extends SQLiteOpenHelper {
    /**
     *
     * @param context   上下文
     * @param name      数据库名
     * @param factory   游标工厂，默认从头部开始
     * @param version   版本号，从1开始
     */
    public ParseDatabaseOpenHelper(Context context) {
        super(context, "img_cache.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.print("数据库 onCreate");
        db.execSQL("create table avimg(_id INTEGER PRIMARY KEY AUTOINCREMENT, avid VARCHAR(20) UNIQUE, imgurl VARCHAR(200), imgSize varchar(20), imgbinary BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("数据库 onUpgrade");
    }
}
