package top.rizix.helloworld;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(MainActivity.this, "android");
        webView.setWebChromeClient(new MyWebChromeClient());
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //创建数据库
        dbHelper = new MyDatabaseHelper(this, "Schedule.db", null, 5);
        db = dbHelper.getWritableDatabase();
        //获取日期
        String nowDateString = getNowDateString();
        //入库操作
        insertDate(db, nowDateString);
        //searchAll(db);
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //获取当前日期字符串方法
    public String getNowDateString() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateString = dateFormat.format(now);
        return nowDateString;
    }

    //入库方法定义
    public boolean insertDate(SQLiteDatabase db, String nowDateString) {
        List<String> scheduleDateList = searchAll(db);
        for(String item : scheduleDateList) {
            if(nowDateString.equals(item)) {
                return false;
            }
        }
        ContentValues values = new ContentValues();
        values.put("schedule_date", nowDateString);
        db.insert("Schedule", null, values);
        return true;
    }

    /**
     * 查询所有数据
     */
    public List<String> searchAll(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from Schedule", null);
        List<String> scheduleDateList = new ArrayList<>();
        while(cursor.moveToNext()) {
            //遍历
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String scheduleDate = cursor.getString(cursor.getColumnIndex("schedule_date"));
            String t01 = cursor.getString(cursor.getColumnIndex("t01"));
            scheduleDateList.add(scheduleDate);
            //Toast.makeText(MainActivity.this, "" + id + " " + scheduleDate + " " + t01,Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return scheduleDateList;
    }

    /**
     * 查询某日数据
     */
    public String[] searchByDate(SQLiteDatabase db, String dateString) {
        Cursor cursor = db.query("Schedule", null, "schedule_date = ?", new String[]{dateString}, null, null, null);
        String[] strings = new String[96];
        while(cursor.moveToNext()) {
            //Toast.makeText(MainActivity.this, cursor.getString(cursor.getColumnIndex("t01")),Toast.LENGTH_SHORT).show();
            for(int i = 0; i <= 23; i++) {
                for(int j = 1; j <= 4; j++) {
                    String columnName = "t" + i + j;
                    int num = i * 4 + j - 1;
                    strings[num] = cursor.getString(cursor.getColumnIndex(columnName));
                }
            }
        }
        return strings;
    }

    /**
     * 事件入库
     */
    public boolean insertEvent(SQLiteDatabase db, String event) {
        List<String> eventList = searchAllEvent(db);
        for(String item : eventList) {
            if(event.equals(item)) {
                return false;
            }
        }
        ContentValues values = new ContentValues();
        values.put("event", event);
        db.insert("Event", null, values);
        return true;
    }

    /**
     * 查询所有事件数据
     */
    public List<String> searchAllEvent(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from Event", null);
        List<String> eventDataList = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String event = cursor.getString(cursor.getColumnIndex("event"));
            eventDataList.add(event);
            //Toast.makeText(MainActivity.this, id + " " + event, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return eventDataList;
    }

    /**
     * 删除事件
     */
    public void deleteEvent(SQLiteDatabase db, String event) {
        db.delete("Event", "event = ?", new String[] {event});
    }


    /**
     * 从js获取数据并更新数据库
     */
    @JavascriptInterface
    public void getDataListFromJSAndUpdate(final String[] dataList, final String dateString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(MainActivity.this, dataList[0], Toast.LENGTH_SHORT).show();
                String now = getNowDateString();
                ContentValues values = new ContentValues();
                //values.put("t01", dataList[0]);
                for(int i = 0; i <= 23; i++) {
                    for(int j = 1; j <=4; j++) {
                        String columnName = "t" + i + j;
                        int num = i * 4 + j - 1;
                        String data = dataList[num];
                        values.put(columnName, data);

                    }
                }
                insertDate(db, dateString);
                db.update("Schedule", values, "schedule_date = ?", new String[] {dateString});
            }
        });
    }

    /**
     * 获取某天时间表数据并返回数据给js函数
     */
    @JavascriptInterface
    public void doValueByDate(final String dateString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] dataList = searchByDate(db, dateString);
                for(int i = 0; i < dataList.length; i++) {
                    webView.loadUrl("javascript:doAllValue(" + i + ",'" + dataList[i] + "')");
                }
                webView.loadUrl("javascript:applyAllColor()");
            }
        });
    }

    /**
     * 供js调用的插入事件数据函数
     */
    @JavascriptInterface
    public void doInsertEvent(final String event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                insertEvent(db, event);
                searchAllEvent(db);
                webView.loadUrl("javascript:addSlideEvent('" + event + "')");
            }
        });
    }

    /**
     * 供js调用的查询事件数据函数
     */
    @JavascriptInterface
    public void doSearchEvent() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> events = searchAllEvent(db);
                for(String item : events) {
                    webView.loadUrl("javascript:doAllEvent('" + item + "')");
                }
            }
        });
    }

    /**
     * 供js调用的删除事件数据函数
     */
    @JavascriptInterface
    public void doDeleteEvent(final String event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deleteEvent(db, event);
            }
        });
    }

    /**
     * 测试
     */
    @JavascriptInterface
    public void jsTest() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] dataList = {"吃饭", "学习"};
                for(int i = 0; i < dataList.length; i++) {
                    webView.loadUrl("javascript:doAllValue(" + i + ",'" + dataList[i] + "')");
                    Toast.makeText(MainActivity.this,"javascript:doAllValue(" + i + ",'" + dataList[i] + "')", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
