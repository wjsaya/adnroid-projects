package top.wjsaya.app.studyprojectscollection.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MyDbDao {
    private MyOpenHelper helper;


    public MyDbDao(Context context) {
        Log.e("MyDbDao调试", "构造方法");
        helper = new MyOpenHelper(context, "img_cache.db", null, 1);
    }


    public void add(String avid, String imgurl, String imgSize) {
        Log.e("MyDbDao调试", "add方法");
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues initValues = new ContentValues();
        initValues.put("avid", avid);//av号
        initValues.put("imgurl", imgurl);//图片地址
        initValues.put("imgSize", imgSize);//图片大小
        initValues.put("imgSize", imgSize);//图片大小

        db.insert("avimg", null, initValues);
        db.close();

    }

    public void delete() {
        Log.e("MyDbDao调试", "delete方法");
    }

    public int getTotal() {
        int total = 0;
        Log.e("MyDbDao调试", "getTotal方法");
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT COUNT(1) FROM avimg;", null);
            cur.moveToLast();
            total = cur.getInt(0);
//            Log.e("MyDbDao调试", "sql查询结果为" + total);
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e("MyDbDao调试", "total为" + total);
        return total;
    }

    public Object getBitmap(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqli = "SELECT imgbinary FROM avimg WHERE _id=?;";
        Cursor cursor = db.rawQuery(sqli, new String[]{String.valueOf(id)});
        try {
            Log.e("getBitmap调试", "dao取出图片：" + String.valueOf(id));
//            Log.wtf("sql语句:", "SELECT imgbinary FROM avimg WHERE _id=" + id + ";");
            while(cursor.moveToNext()) {
                //            Log.e("调试", String.valueOf(avid) + "\tcursor.moveToFirst()成立");
                byte[] in;
                in = cursor.getBlob(0);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 5;  //宽缩到1/4，高缩到1/4，整体缩放为原来1/16
                cursor.close();
                return BitmapFactory.decodeByteArray(in, 0, in.length, opts);
            }
        }catch (OutOfMemoryError e) {
            Log.e("OutOfMemoryError", "内存爆了");
            e.printStackTrace();
        }
        cursor.close();
        Log.e("getBitmap调试", "未取到图片");
        return "未取到图片";
    }

    public Map<String, String> getInfo(String id) {
        Log.e("MyDbDao调试", "select方法");
        String reid = "";
        String reavid = "";
        String reurl = "";
        String resize = "";
        Bitmap reavimg = null;
        Map<String, String> remap = new HashMap<String, String>();

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM avimg WHERE _id=?;", new String[]{id});
            Log.e("查询语句:", "SELECT * FROM avimg WHERE _id= " + id + ";");
            if (cursor.getCount() != 0) {
                cursor.moveToLast();
                reid = cursor.getString(0);
                reavid = cursor.getString(1);
                reurl = cursor.getString(2);
                resize = cursor.getString(3);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remap.put("id", reid);
            remap.put("avid", reavid);
            remap.put("url", reurl);
            remap.put("size", resize);
            return remap;
        }
    }

    public void update() {
        Log.e("MyDbDao调试", "update方法");
    }

}
