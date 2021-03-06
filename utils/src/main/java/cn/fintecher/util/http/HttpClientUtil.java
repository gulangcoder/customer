package cn.fintecher.util.http;

import cn.fintecher.util.SignatureRemindUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : Http所有的get、post请求</strong><br>
 * <strong>Create on : 2018年05月14日 17:17<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:程锐 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */

public class HttpClientUtil {

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    private static HttpClientUtil instance = new HttpClientUtil();

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        return instance;
    }

    /**
     * @Description: 发送HttpPost请求(无参)
     * @Param: [请求地址: httpUrl]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带一个参数, 参数以json格式传入
     * @Param: [请求地址: httpUrl, 请求参数: param]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public String sendHttpPost(String httpUrl, String param) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(param, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带一个参数,参数以json格式传入, 且有请求头信息
     * @Param: [请求地址: httpUrl, 请求参数: params, 请求头信息: token]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public String sendHttpPostWithToken(String httpUrl, String params, String token) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带一个参数, 发送失败是否重试
     * @Param: [请求地址: httpUrl, 请求参数: params, 是否重试(true/false): flag]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    public String sendHttpPost(String httpUrl, String params, boolean flag) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, flag);
    }

    /**
     * @Description: 发送HttpPost请求, 带一个参数, 指定请求头中媒体类型信息
     * @Param: [请求地址: httpUrl, 请求参数: params, 信息类型: contentType]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    public String sendHttpPost(String httpUrl, String params, String contentType) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带多个参数, 指定请求头中媒体类型信息
     * @Param: [请求地址: httpUrl, 请求参数: maps, 信息类型: contentType]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    public String sendHttpPost(String httpUrl, Map<String, Object> maps,
                               String contentType) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key,
                    maps.get(key) == null ? null : maps.get(key).toString()));
        }
        try {
            StringEntity stringEntity = new UrlEncodedFormEntity(
                    nameValuePairs, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带多个参数
     * @Param: [请求地址: httpUrl, 请求参数: maps]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * @Description: 发送HttpPost请求, 带多个参数, 带文件
     * @Param: [请求地址: httpUrl, 请求参数: maps, 文件列表: fileLists]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps,
                               List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (String key : maps.keySet()) {
            meBuilder.addPart(key, new StringBody(maps.get(key),
                    ContentType.TEXT_PLAIN));
        }
        for (File file : fileLists) {
            FileBody fileBody = new FileBody(file);
            meBuilder.addPart("file", fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }


    /**
     * @Description: 下载文件
     * @Param: [请求地址: url, 存储在本地的路径: destFileName]
     * @return: void
     * @Date: 2018/5/15
     */
    public void getFile(String url, String destFileName) throws Exception {
        // 生成一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        File file = new File(destFileName);
        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
            }
            fout.flush();
            fout.close();
        } finally {
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }

    /**
     * @Description: 发送HttpPost请求, 如果失败，是否重试
     * @Param: [请求对象: httpPost, 是否重试(true/false): retryHandler]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    private String sendHttpPost(HttpPost httpPost, boolean retryHandler) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            if (retryHandler) {
                HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
                    @Override
                    public boolean retryRequest(IOException arg0, int retryTimes, HttpContext arg2) {
                        return false;
                    }
                };
                httpClient = HttpClients.custom().setRetryHandler(handler).build();
            }

            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Authorization",
                    SignatureRemindUtil.createSign());
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * @Description: 实际发送HttpPost请求的方法
     * @Param: [HttpPost对象: httpPost]
     * @return: java.lang.String
     * @Date: 2018/5/14
     */
    private String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * @Description: 发送HttpGet请求, 不带参数
     * @Param: [请求地址: httpUrl]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    public String sendHttpGet(String httpUrl) throws ConnectTimeoutException {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet);
    }

    /**
     * @Description: 发送HttpGet请求, 不带参数
     * @Param: [请求对象: httpGet]
     * @return: java.lang.String
     * @Date: 2018/5/15
     */
    private String sendHttpGet(HttpGet httpGet) throws ConnectTimeoutException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.addHeader("Authorization", SignatureRemindUtil.createSign());
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectTimeoutException();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public static void setRequestConfig(RequestConfig requestConfig1) {
        requestConfig = requestConfig1;
    }
}
