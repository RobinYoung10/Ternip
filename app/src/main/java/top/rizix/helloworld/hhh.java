package top.rizix.helloworld;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robin on 18-4-21.
 * 留个底
 */

public class hhh {
    public static int power = 1;
/*
    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String datapoints = "{\n" +
                        "\n" +
                        "    \"datastreams\": [\n" +
                        "\n" +
                        "        {\n" +
                        "\n" +
                        "            \"id\": \"Test\",\n" +
                        "\n" +
                        "            \"datapoints\": [\n" +
                        "\n" +
                        "               {\n" +
                        "                    \"value\": \"Test:" + power + "!@\"\n" +
                        "\n" +
                        "                }\n" +
                        "\n" +
                        "            ]\n" +
                        "\n" +
                        "        }\n" +
                        "   ]\n" +
                        "}";
                System.out.println(datapoints);
                String reString = sendPost("2P8ijDorD8YuYDA6QU=mV9GbQUw=", "http://api.heclouds.com/devices/26959051/datapoints", datapoints);
                System.out.println(reString);
                if(reString.equals("{\"errno\":0,\"error\":\"succ\"}")) {
                    Toast.makeText(MainActivity.this, "成功",Toast.LENGTH_SHORT).show();
                    if(power == 1) {
                        power = 0;
                    } else {
                        power = 1;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @JavascriptInterface
    public void getPower() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                SimpleDateFormat dateFormatFirst = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatLast = new SimpleDateFormat("HH:mm:ss");
                String dateString1 = dateFormatFirst.format(date);
                String dateString2 = dateFormatLast.format(date);
                String dateString = dateString1 + "T" + dateString2;
                String url = "http://api.heclouds.com/devices/26959051/datapoints?datastream_id=电量&duration=60&end=" + dateString;
                String reString = sendGet("2P8ijDorD8YuYDA6QU=mV9GbQUw=", url);
                System.out.println(reString);
                webView.loadUrl("javascript:doChart('" + reString + "')");
            }
        });
    }
*/

    /**
     * 向指定 URL 发送POST方法的请求
     * @param apikey 在OneNET申请的APIKey
     *
     * @param url
     * 发送请求的 URL
     * @param param
     * 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String apikey,String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
// 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
// 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("api-key", apikey);
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
// 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
// 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
// 发送请求参数
            out.print(param);
// flush输出流的缓冲
            out.flush();
// 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
//使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String apikey,String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
// 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
// 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("api-key", apikey);
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
// 发送POST请求必须设置如下两行
            //conn.setDoOutput(true);
            //conn.setDoInput(true);
// 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
// 发送请求参数
            //out.print(param);
// flush输出流的缓冲
            //out.flush();
// 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
//使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
