package httpclients;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author zhayangtao
 * 2017/12/5
 * 测试httpclients工具类
 */
public class TestHttpClients {
    private static final String URL = "http://www.baidu.com";

    public void get() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        System.out.println("executing request " + httpGet.getURI());
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            // 获取响应实体
            /*try {
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println(EntityUtils.toString(entity));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            Optional.ofNullable(response.getEntity()).ifPresent(httpEntity -> {
                try {
                    System.out.println(EntityUtils.toString(httpEntity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // 打印响应状态
            System.out.println(response.getStatusLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(closeableHttpClient).ifPresent(closeableHttpClient1 -> {
                try {
                    closeableHttpClient1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void post() {
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        //创建参数队列，以键值对方式存储
        List<NameValuePair> pairs = Lists.newArrayList();
        pairs.add(new BasicNameValuePair("phone", "1"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");
            // 设置post清楚参数
            httpPost.setEntity(entity);
            System.out.println("executing request " + httpPost.getURI());
            CloseableHttpResponse response = httpClient.execute(httpPost);
            Optional.ofNullable(response.getEntity()).ifPresent(httpEntity -> {
                try {
                    System.out.println(EntityUtils.toString(httpEntity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(httpClient).ifPresent(closeableHttpClient1 -> {
                try {
                    closeableHttpClient1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
