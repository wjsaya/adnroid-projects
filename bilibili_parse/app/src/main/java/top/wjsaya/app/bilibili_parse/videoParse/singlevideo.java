package top.wjsaya.app.bilibili_parse.videoParse;

import android.os.Environment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
    private String is_completed = "";
    private String downloaded_bytes = "0";
    private String total_bytes = "0";
    private String title = "";
    private String type_tag = "";
    private String cover = "";

    /**
     * 构造方法，初始化基本属性
     * @param avid　av号
     */
    public singlevideo(String avid) {
        this.avid = avid;
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        this.avFilePath = new File(ExtRootDir + "/Android/data/tv.danmaku.bili/download/" + avid);
        this.parseJson(this.avFilePath);
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
//            Log.d("single:", file.toString());
            if (file.toString().contains(".blv")) {
//                Log.d("singleXXXXXX:", file.toString());
                re += file.toString();
            }
        }
        return re;
    }
/*
    public void parseJson(File path) {
        File[] files = path.listFiles();
        //   + "/entry.json");
        for(File file : files) {
            parseJsonfile(file);
        }
    }
    */
    public void parseJson(File path) {
        File file = new File(path.toString() + "/1/entry.json");
//        Log.e("parseJsonfile path", file.getAbsolutePath().toString());

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bis = new BufferedReader(isr);
            String in = bis.readLine();
            try {
                JSONObject jso = new JSONObject(in);
                this.title = jso.getString("title");
                this.type_tag = jso.getString("type_tag");
                this.cover = jso.getString("cover");
//                Log.e("调试", "封面路径"+this.cover);
                this.avid = jso.getString("avid");
                this.is_completed = jso.getString("is_completed");
//                this.downloaded_bytes += Formatter.formatFileSize(MainActivity.this, Long.valueOf(jso.getString("downloaded_bytes")));
                long temp = Long.valueOf(jso.getString("downloaded_bytes")) + Long.valueOf(this.downloaded_bytes);
                this.downloaded_bytes = String.valueOf(temp);
//                Log.e("downloaded_bytes", downloaded_bytes);
//                this.total_bytes += Formatter.formatFileSize(MainActivity.this, Long.valueOf(jso.getString("total_bytes")));
                temp = Long.valueOf(jso.getString("total_bytes")) + Long.valueOf(this.total_bytes);
                this.total_bytes = String.valueOf(temp);
//                Log.e("total_bytes", total_bytes);
//                this.total_bytes += jso.getString("total_bytes");

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

    public String[] getInfo() {
        String[] re = {
                this.title,
                this.total_bytes
        };
//        Log.wtf("getINfo内部title", re[0].toString());
//        Log.wtf("getINfo内部total_bytes", re[1].toString());
        return re;


    }

    public Map<String, String> getInfoMAP() {
        Map<String, String> remap = new HashMap<String, String>();
        remap.put("avid", this.avid);
        remap.put("title", this.title);
        remap.put("cover", this.cover);
        remap.put("is_completed", this.is_completed);
        remap.put("downloaded_bytes", this.downloaded_bytes);
        remap.put("total_bytes", this.total_bytes);
        remap.put("type_tag", this.type_tag);
        remap.put("avFilePath", this.avFilePath.getAbsolutePath().toString());
//        Log.wtf("getINfo内部", remap.get("avid"));
        return remap;


        }
    }
