package top.wjsaya.app.bilibili_parse.SingleVideo;

import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class singlevideo {
    String avid;
    File avFilePath;
/*
    String is_completed = "\n下载状态:\t";
    String downloaded_bytes = "\n已下载大小:\t";
    String total_bytes = "\n总大小:\t";
    String title = "\nav名:\t";
    public String type_tag = "\ntype_tag:\t";
    String cover = "\n封面图:\t";
*/
    public String is_completed = "";
    public String downloaded_bytes = "";
    public String total_bytes = "";
    public String title = "";
    public String type_tag = "";
    public String cover = "";

    /**
     * 构造方法，初始化基本属性
     * @param avid　av号
     */
    public singlevideo(String avid) {
        this.avid = avid;
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        this.avFilePath = new File(ExtRootDir + "/Android/data/tv.danmaku.bili/download/" + avid);
    }

    /**
     * 解析分ｐ
     */
    public String parseVideos() {
        String temp = "";
        try {
            File[] files = this.avFilePath.listFiles();
            for (File file : files) {
                parseJson(file);
                temp += parseSingle(file);
                temp += "|";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }


    /**
     * 解析单ｐ
     * @param path
     */
    public String parseSingle(File path) {
        File[] files = new File(path.toString() + this.type_tag).listFiles();
        String re = "";
        for(File file : files) {
            Log.d("single:", file.toString());
            if (file.toString().contains(".blv")) {
                Log.d("singleXXXXXX:", file.toString());
                re += file.toString();
            }
        }
        return re;
    }

    public void parseJson(File path) {
        File file = new File(path.toString() + "/entry.json");
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bis = new BufferedReader(isr);
            String in = bis.readLine();
            try {
                JSONObject jso = new JSONObject(in);
                this.avid += "\nav号:\t";
                this.title += jso.getString("title");
                this.type_tag += jso.getString("type_tag");
                this.cover += jso.getString("cover");
                this.avid += jso.getString("avid");
                this.is_completed += jso.getString("is_completed");
//                this.downloaded_bytes += Formatter.formatFileSize(this, Long.valueOf(jso.getString("downloaded_bytes")));
                this.downloaded_bytes += "  (" + jso.getString("downloaded_bytes") + " Bytes)";
//                this.total_bytes += Formatter.formatFileSize(this, Long.valueOf(jso.getString("total_bytes")));
                this.total_bytes += "  (" + jso.getString("total_bytes") + "Bytes)";

                //String re = avid + title + is_completed + downloaded_bytes + total_bytes + type_tag + cover;
                //tv_content.setText(tv_content.getText() + re);
                //Toast.makeText(this, title, Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                e.printStackTrace();
//                Toast.makeText(this, "json解析失败", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, "读取错误", Toast.LENGTH_LONG).show();
        }
    }


}
