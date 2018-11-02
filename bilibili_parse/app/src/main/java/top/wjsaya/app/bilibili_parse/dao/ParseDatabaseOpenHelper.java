package top.wjsaya.app.bilibili_parse.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParseDatabaseOpenHelper extends SQLiteOpenHelper {
    /**
     * @param context 上下文
     */
    public ParseDatabaseOpenHelper(Context context) {
        super(context, "img_cache.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.print("数据库 onCreate");
        db.execSQL("create table avimg(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "avid VARCHAR(20) UNIQUE, " +
                "title VARCHAR(200), " +
                "imgurl VARCHAR(200), " +
                "totalBytes VARCHAR(200), " +
                "typeTag VARCHAR(200), " +
                "avFilePath VARCHAR(200), " +
                "imgSize varchar(20), " +
                "imgbinary BLOB);");
        db.execSQL("update sqlite_sequence SET seq = 0 where name = 'avimg';");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("数据库 onUpgrade");
    }
}
