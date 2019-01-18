package com.leadmap.environmentalrotection.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/26 11:34
 */
public class HttpClientUtils {

    public static String doPost(String url,String jsonBean) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(jsonBean);
            httpPost.setHeader("Content-Type" , "application/json");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String doPost(String url, Map<String, Object> map) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            String jsonParam = JSONObject.toJSON(map).toString();
//            String jsonParam = JSONObject.fromObject(map).toString();
            StringEntity entity = new StringEntity(jsonParam,"UTF-8");
            entity.setContentType("application/json");
//            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
//            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
//            }
//            if (list.size() > 0) {
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
//                httpPost.setEntity(entity);
//            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 带参数的get请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, Object> map) throws Exception {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        JSONObject jsonResult = null;
        try {
            // 声明URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            // 判断参数map是否为非空
            if (map != null) {
                // 遍历参数
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 设置参数
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            // 2 创建httpGet对象，相当于设置url请求地址
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
            // 状态码
//         response.getStatusLine().getStatusCode();
            // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
//         EntityUtils.toString(response.getEntity(), "UTF-8");
            // 解析数据封装HttpResult
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
