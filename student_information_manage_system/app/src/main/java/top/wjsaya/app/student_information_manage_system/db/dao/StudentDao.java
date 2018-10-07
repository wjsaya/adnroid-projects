package top.wjsaya.app.student_information_manage_system.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import top.wjsaya.app.student_information_manage_system.db.StudentDBOpenHelper;

/**
 * 数据库dao(data access object)
 * 增删改查操作集
 */
public class StudentDao {
    private StudentDBOpenHelper helper;

    /**
     * 构造方法要求必须传入上下文
     * @param context
     */
    public StudentDao(Context context) {
        helper = new StudentDBOpenHelper(context);

    }
    /**
     * 添加一个学生
     * @param name  姓名
     * @param gender 性别
     */
    public void add(String name, String gender) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO student (name, gender) VALUES(?, ?)", new Object[]{name, gender});
        db.close();//释放资源
    }
    /**
     * 删除一个学生
     * @param name  姓名
     */
    public void delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM student WHERE name=?", new Object[]{name});
        db.close();//释放资源
    }
    /**
     * 修改一个学生的性别
     * @param name  姓名
     * @param newgender 性别
     */
    public void update(String name, String newgender) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE TABLE student newgender=? WHERE name=?", new Object[]{newgender, name});
        db.close();//释放资源
    }
    /**
     * 查询信息
     * @param name  姓名
     * @param result 返回信息
     */
    public String update(String name) {
        String result = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT gender FROM student WHERE name=?", new String[]{name});
        try {
            cur.moveToNext();
            result = cur.getString(0);
        }
        catch (Exception e) {
            Log.e("StudentDao.update:", "can't find record of " + name);
        }
        cur.close();
        db.close();
        return result;
    }

}
