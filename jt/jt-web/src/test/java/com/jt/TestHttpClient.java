package com.jt;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

public class TestHttpClient {

	/**
	 * 实现步骤:
	 * 	1.创建httpClient对象
	 * 	2.指定url请求地址
	 * 	3.指定请求的方式  get/post
	 * 		规则:
	 * 			一般查询操作使用get请求
	 * 			如果涉及数据库入库/更新并且数据量大使用post提交
	 * 			涉密操作使用post提交
	 *  4.发起请求获取response对象
	 *  5.判断请求是否正确 检查状态码200
	 *  6.从返回值中获取数据(JSON/html).之后进行转化对象
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet(MultipartFile multipartFile) throws ClientProtocolException, IOException {
		//CloseableHttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://item.jd.com/6082651.html";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse execute = httpClient.execute(httpGet);
		if(execute.getStatusLine().getStatusCode()==200) {
			System.out.println("请求正确!!");
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println(result);
			multipartFile.transferTo(new File("E:/CGB1812JT/jt-upload/get.html"));
		}
	}
	
	@Test
	public void testPost() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://item.jd.com/6082651.html";
		HttpPost httpGet = new HttpPost(url);
		CloseableHttpResponse execute = httpClient.execute(httpGet);
		if(execute.getStatusLine().getStatusCode()==200) {
			System.out.println("请求正确!!");
			String result = EntityUtils.toString(execute.getEntity());
			System.out.println(result);
		}
	}
}
