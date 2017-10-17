package com.fhr.httpclientdemo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
/**
 * HttpClient组件封装
 * 单例优化
 * @author fhr
 * @since 2017/10/16
 */
public class HttpUtils {  
	//json_content_type常量
	private static final String JSON_CONTENT_TYPE = "application/json";
	//content_type header常量
	private static final String CONTENT_TYPE_HEADER = "Content-type";
	//UTF-8常量
	private static final String UTF_8_CHARSET="UTF-8";
	//从连接池获取连接的超时时间
	private static final int CONNECTION_REQUEST_TIMEOUT=1000;
	//客户端和服务端建立连接的超时时间
	private static final int CONNECTION_TIMEOUT=2000;
	//指客户端从服务器读取数据的超时时间
	private static final int SOCKET_TIMEOUT=4000;
	/**
	 * 普通get请求 返回返回正文(字符串形式)
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		final HttpGet httpGet=new HttpGet(url);
		return sendHttpAndReturnContent(httpGet);
	}
	/**
	 * 表单post请求 返回返回正文(字符串形式)
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postForm(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
		final  HttpPost httpPost=createFormHttpPost(url, params);
		return sendHttpAndReturnContent(httpPost);
	}
	/**
	 * json post请求 返回返回正文(字符串形式)
	 * @param url
	 * @param jsonContent
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postJson(String url,String jsonContent) throws ClientProtocolException, IOException{
        final HttpPost httpPost=createJsonHttpPost(url, jsonContent);
		return sendHttpAndReturnContent(httpPost);
     }
	
	/**
	 * 创建json的httppost方法
	 * @param url
	 * @param jsonContent
	 * @return
	 */
	private static HttpPost createJsonHttpPost(String url, String jsonContent) {
		//创建httpPost对象  
		 HttpPost httpPost = new HttpPost(url);  
		 //给httpPost设置JSON格式的参数  
		 StringEntity requestEntity = new StringEntity(jsonContent,UTF_8_CHARSET);  
		 requestEntity.setContentEncoding(UTF_8_CHARSET);                
		 httpPost.setHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE);  
		 httpPost.setEntity(requestEntity);
		return httpPost;
	}
    /**
     * 创建表单的httppost方法
     * @param url
     * @param params
     * @return
     */
	private static HttpPost createFormHttpPost(String url,List<NameValuePair> params){
		//创建httpPost对象  
		 HttpPost httpPost = new HttpPost(url);  
		 //给httpPost设置JSON格式的参数  
		 StringEntity requestEntity;
		try {
			requestEntity = new UrlEncodedFormEntity(params,UTF_8_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("UnsupportedEncoding!");
		}  
		// requestEntity.setContentEncoding(UTF_8_CHARSET);                
		 httpPost.setEntity(requestEntity);
		return httpPost;
	}
	/**
	 * 功能核心 发送http请求，返回响应体
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String sendHttpAndReturnContent(final HttpUriRequest request) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = getHttpClient();
		try (CloseableHttpResponse response = httpClient.execute(request)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return entity == null ? null : EntityUtils.toString(entity, UTF_8_CHARSET);
			} else {
				return null;
			}
		}
	}
	
	private static CloseableHttpClient getHttpClient(){
		return NestedClass.INSTANCE;
	}
	/**
	 * 嵌套类实现单例模式
	 */
	private static class NestedClass {
		/**
		 * 线程安全和调优后的HttpClient
		 */
		private static final CloseableHttpClient INSTANCE;
		static {
			// 请求超时设置
			RequestConfig requestConfig =RequestConfig.custom()
										.setConnectTimeout(CONNECTION_TIMEOUT)
										.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
										.setSocketTimeout(SOCKET_TIMEOUT)
										.build();
		   //将该请求超时设置作为HttpClient的默认设置 将会作用在每个请求上
		   //其实也可以对每个请求单独设置
			INSTANCE = HttpClients.custom()
								  .setDefaultRequestConfig(requestConfig)
								  .setConnectionManager(new BasicHttpClientConnectionManager())
								  .build();
		}
	}
}