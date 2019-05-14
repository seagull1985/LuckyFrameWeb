package com.luckyframe.common.utils.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	/**
	 * 使用HttpClient以JSON格式发送post请求
	 * @param urlParam
	 * @param jsonparams
	 * @param socketTimeout
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @author Seagull
	 * @date 2019年5月14日
	 */
	public static String httpClientPost(String urlParam,String jsonparams,Integer socketTimeout) throws NoSuchAlgorithmException, KeyManagementException, UnsupportedEncodingException, IOException{
		StringBuffer resultBuffer = null;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(urlParam);
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(3000).setConnectionRequestTimeout(1500)  
		        .setSocketTimeout(socketTimeout).build();  
		httpPost.setConfig(requestConfig);
	    httpPost.setHeader("Content-Type", "application/json");
		// 构建请求参数
		BufferedReader br = null;
		try {
		if(null!=jsonparams&&jsonparams.length()>0){
				StringEntity entity = new StringEntity(jsonparams,"utf-8");
				httpPost.setEntity(entity);
		}
       
		 CloseableHttpResponse response = httpclient.execute(httpPost);

		// 读取服务器响应数据
		resultBuffer = new StringBuffer();
		if(null!=response.getEntity()){
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}	
		}
		} catch (RuntimeException e) {
			log.error("网络链接出现异常，请检查...",e);
			throw new RuntimeException(e);
		} catch (ConnectException e) {
			log.error("客户端网络无法链接，请检查...",e);
			throw new ConnectException();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}		
		return resultBuffer.toString();
	}
	
	/**
	 * 使用HttpClient以JSON格式发送get请求
	 * @param urlParam
	 * @param params
	 * @param socketTimeout
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws NoHttpResponseException
	 * @author Seagull
	 * @date 2019年5月14日
	 */
	public static String httpClientGet(String urlParam, Map<String, Object> params,Integer socketTimeout) throws NoSuchAlgorithmException, KeyManagementException,NoHttpResponseException {
		StringBuffer resultBuffer = null;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		BufferedReader br = null;
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		HttpGet httpGet = new HttpGet(urlParam);
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(3000).setConnectionRequestTimeout(1500)  
		        .setSocketTimeout(socketTimeout).build(); 
		httpGet.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			
			// 读取服务器响应数据
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			String temp;
			resultBuffer = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}
	

	/**
	 * 上传文件
	 * @param urlParam
	 * @param loadpath
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws HttpHostConnectException
	 * @author Seagull
	 * @date 2019年3月15日
	 */
	public static String httpClientUploadFile(String urlParam, String loadpath, File file) throws NoSuchAlgorithmException, KeyManagementException, HttpHostConnectException {
		StringBuffer resultBuffer = null;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(urlParam);
		// 构建请求参数
		BufferedReader br = null;
		try {
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			//设置请求的编码格式  
			entityBuilder.setCharset(Charset.forName("utf-8"));
			entityBuilder.addBinaryBody("jarfile", file);
			entityBuilder.addTextBody("loadpath", loadpath);
		    HttpEntity reqEntity =entityBuilder.build();
		    httpPost.setEntity(reqEntity);
		    
			CloseableHttpResponse response = httpclient.execute(httpPost);
			 //从状态行中获取状态码  
		     String responsecode = String.valueOf(response.getStatusLine().getStatusCode());
			// 读取服务器响应数据
			resultBuffer = new StringBuffer();

			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			if(resultBuffer.length()==0){
				resultBuffer.append("上传文件异常，响应码："+responsecode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * 获取文件流
	 * @param urlParam
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws HttpHostConnectException
	 * @author Seagull
	 * @date 2019年3月15日
	 */
	public static byte[] getFile(String urlParam, Map<String, Object> params) throws IOException, HttpHostConnectException{
		// 构建请求参数
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
        URL urlConet = new URL(urlParam);
        HttpURLConnection con = (HttpURLConnection)urlConet.openConnection();    
        con.setRequestMethod("GET");    
        con.setConnectTimeout(4 * 1000);    
        InputStream inStream = con .getInputStream();    //通过输入流获取图片数据    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[2048];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();
        byte[] data =  outStream.toByteArray(); 
        return data;
    }

}
