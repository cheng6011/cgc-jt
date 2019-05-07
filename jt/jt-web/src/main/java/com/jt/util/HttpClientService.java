package com.jt.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {

	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;
	
	/**
	 * 为了用户请求方便封装doGet方法
	 * 参数说明:
	 * 	1.String url地址
	 * 	2.map<String,String>集合封装参数
	 * 	3.字符集编码
	 */
	public String doGet(String url,Map<String, String> params,String charset) {
		//1.判断字符集编码是否为空
		if(StringUtils.isEmpty(charset)) {
			//如果为null,则设定默认的字符集
			charset = "UTF-8";
		}
		
		//2.判断用户是否传递参数
		if(params != null) {
			//如果参数不为空,则需要url地址拼串?id=1&....
			url = url + "?";
			//需要获取参数变量map集合.
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url += entry.getKey() + "=" + entry.getValue() + "&";
				url = url.substring(0,url.length()-1);//去除&
			}
		}
		//封装请求对象httpclent
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		
		//4.发起http请求
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode()==200) {
				//如果返回值的状态信息是200表示请求正确
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String doGet(String url) {
		return doGet(url, null,null);
	}
	
	public String doGet(String url,String charset) {
		return doGet(url, null,charset);
	}
	
	public String doGet(String url,Map<String, String> params) {
		return doGet(url, params,null);
	}
	
	
	public String doPost(String url,Map<String, String> params,String charset) {
		//1.判断字符集编码是否为空
		if(StringUtils.isEmpty(charset)) {
			//如果为null,则设定默认的字符集
			charset = "UTF-8";
		}
		
		//2.判断用户是否传递参数
		if(params != null) {
			//如果参数不为空,则需要url地址拼串?id=1&....
			url = url + "?";
			//需要获取参数变量map集合.
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url += entry.getKey() + "=" + entry.getValue() + "&";
				url = url.substring(0,url.length()-1);//去除&
			}
		}
		//封装请求对象httpclent
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		
		//4.发起http请求
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode()==200) {
				//如果返回值的状态信息是200表示请求正确
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String doPost(String url) {
		return doPost(url, null,null);
	}
	
	public String doPost(String url,String charset) {
		return doPost(url, null,charset);
	}
	
	public String doPost(String url,Map<String, String> params) {
		return doPost(url, params,null);
	}
	
}
