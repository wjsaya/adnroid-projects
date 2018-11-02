package top.wjsaya.app.bilibili_parse.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ParseDao {
    private ParseDatabaseOpenHelper helper;

    public ParseDao(Context context) {
        helper = new ParseDatabaseOpenHelper(context);
    }

    public void addToInfo( Map<String, String>) {
        Log.e("调试", "dao存入：" + String.valueOf(imgurl));
        ContentValues initValues = new ContentValues();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        avImg.compress(Bitmap.CompressFormat.JPEG, 50, os);
        initValues.put("avid", avid);//av号
        initValues.put("imgurl", imgurl);//图片地址?
        initValues.put("imgSize", imgSize);//图片大小
        initValues.put("imgbinary", os.toByteArray());//图片二进制

        String _id = "";
        String avid = "";
        String title = "";
        String imgurl = "";
        String totalBytes = "";
        String typeTag = "";
        String avFilePath = "";
        String imgSize = "";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert("avimg", null, initValues);
        //        db.execSQL("INSERT INTO avimg(avid, imgurl, imgSize, imgbinary) values(?, ?, ?, ?);", new Object[]{avid, imgurl, imgSize, initValues});
        db.close();
    }

    public void delete(String avid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM avimg WHERE avid=?;", new Object[]{avid});
        db.close();
    }

    public void updateUrl(String avid, String imgurl) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE avimg SET imgurl=? WHERE avid=?;", new Object[]{imgurl, avid});
        db.close();
    }

    public void updateBitmap(String avid, Bitmap avImg) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        avImg.compress(Bitmap.CompressFormat.PNG, 100, os);

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE avimg SET imgbinary=? WHERE avid=?;", new Object[]{os.toByteArray(), avid});
        db.close();
    }

    /**
     * 从数据取出size
     * 取出行数非0，则有记录，返回
     * 取出行数为0，那就是没有记录
     *
     * @param avid av号
     * @return
     */
    public String selectSize(String avid) {
        String result = "0";
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT imgSize FROM avimg WHERE avid=?;", new String[]{avid});
            if (cursor.getCount() != 0) {
                cursor.moveToLast();
                result = cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public int getCount() {
        Log.e("MyDbDao调试", "getCount()方法");
        int count = 0;
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(1) FROM avimg;", null);
            cursor.moveToLast();
            count = cursor.getInt(0);
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getCOunt()调试", "数据库查询为空或查询失败");
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, String> getInfo(int id) {
        Log.e("MyDbDao调试", "getInfo()方法");
        String _id = "";
        String avid = "";
        String title = "";
        String imgurl = "";
        String totalBytes = "";
        String typeTag = "";
        String avFilePath = "";
        String imgSize = "";
//        Bitmap imgbinary = null; img二进制文件单独做处理

        Map<String, String> remap = new HashMap<String, String>();

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            String sqli_1 = "*";//目前暂时，等数据库更新后替换
            String sqli = "SELECT " + sqli_1 + " FROM avimg WHERE _id=?;";
            Cursor cursor = db.rawQuery(sqli, new String[]{String.valueOf(id)});
            cursor.moveToLast();
            _id = cursor.getString(0);
            avid = cursor.getString(1);
            imgurl = cursor.getString(2);
            totalBytes = cursor.getString(3);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {remap.put("_id", _id);
            remap.put("avid", avid);
            remap.put("title", title);
            remap.put("imgurl", imgurl);
            remap.put("totalBytes", totalBytes);
            remap.put("typeTag", typeTag);
            remap.put("avFilePath", avFilePath);
            remap.put("imgSize", imgSize);
            return remap;
        }
    }

    public Object getBitmap(int _id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqli = "SELECT imgbinary FROM avimg WHERE _id=?;";
        Cursor cursor = db.rawQuery(sqli, new String[]{String.valueOf(_id)});
        try {
            Log.e("getBitmap调试", "dao取出图片：" + String.valueOf(_id));
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

    public Object selectBitmap(String avid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqli = "SELECT imgbinary FROM avimg WHERE avid=?;";
        Cursor cursor = db.rawQuery(sqli, new String[]{avid});
        try {
            Log.e("调试", "dao取出图片：" + String.valueOf(avid));

            Log.wtf("sql语句:", "SELECT imgbinary FROM avimg WHERE avid=" + avid + ";");
            while(cursor.moveToNext()) {
                //            Log.e("调试", String.valueOf(avid) + "\tcursor.moveToFirst()成立");
                byte[] in;
                in = cursor.getBlob(0);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 4;  //宽缩到1/4，高缩到1/4，整体缩放为原来1/16
                cursor.close();
                return BitmapFactory.decodeByteArray(in, 0, in.length, opts);
            }
        }catch (OutOfMemoryError e) {
            Log.e("OutOfMemoryError", "内存爆了");
            e.printStackTrace();
        }
            cursor.close();
            return "未取到图片";
          /*
            if (cursor.getCount() != 0) {
                try {
                    Log.e("调试", "111111111111111111111");
                    cursor.moveToFirst();
                    byte[] in;
                    in = cursor.getBlob(0);
                    cursor.close();
                    return BitmapFactory.decodeByteArray(in, 0, in.length);
                } catch (IllegalStateException ile) {
                    Log.e("调试", "2222222222222222222222222222222");
                    byte[] in;
                    in = cursor.getBlob(1);
                    cursor.close();
                    return BitmapFactory.decodeByteArray(in, 0, in.length);
                }
            }

        } catch (Exception e) {
            Log.e("调试", "dao取图片报错：|" + String.valueOf(avid) + "|");
            e.printStackTrace();
        }
        cursor.close();
        */
    }
}

