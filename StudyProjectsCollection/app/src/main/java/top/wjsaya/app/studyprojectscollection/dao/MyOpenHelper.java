package top.wjsaya.app.studyprojectscollection.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    /**
     *
     * @param context   上下文
     * @param name      数据库文件名
     * @param factory   factory
     * @param version   版本号
     */
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.print("数据库 onCreate");
        String sqli = "create table avimg(_id INTEGER PRIMARY KEY AUTOINCREMENT, avid VARCHAR(20) UNIQUE, imgurl VARCHAR(200), imgSize varchar(20), imgbinary BLOB)";
        db.execSQL(sqli);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("数据库 onUpgrade");
    }
}
