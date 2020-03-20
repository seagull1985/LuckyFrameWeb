package com.luckyframe.common.utils.client;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.netty.NettyServer;
import com.luckyframe.common.netty.Result;
import com.luckyframe.project.system.client.domain.Client;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

public class HttpRequest {

	private static String file_dir = System.getProperty("user.dir")+"/tmp";


    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	/**
	 * 使用HttpClient以JSON格式发送post请求
	 * @param urlParam 请求地址
	 * @param jsonparams 请求内容
	 * @param socketTimeout 超时时间
	 * @author Seagull
	 * @date 2019年5月14日
	 */
	public static String httpClientPost(String urlParam, Client client, String jsonparams, Integer socketTimeout) throws IOException{
		//测试netty同步等待
		if(Objects.equals(client.getClientType(), 1))
		{
			JSONObject tmp=new JSONObject();
			String uuid= UUID.randomUUID().toString();
			//封装调度参数
			String subStr= ":"+ClientConstants.CLIENT_MONITOR_PORT;
			String tmpMethod=urlParam.substring(urlParam.lastIndexOf(subStr)+subStr.length());
			tmp.put("method","run");
			tmp.put("data",jsonparams);
			tmp.put("uuid",uuid);
			tmp.put("url",tmpMethod);
			tmp.put("getOrPost","post");
			tmp.put("socketTimeout",socketTimeout);
			Result re= null;
			try {
				re = NettyServer.write(tmp.toString(),client.getClientIp(), uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assert re != null;
			if(1==re.getCode())
			{
				//请求成功，返回结果
				return (String)re.getMessage();
			}
			else
			{
				throw new RuntimeException();
			}
		}
		StringBuffer resultBuffer;
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
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
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
					log.error("关闭链接出现异常，请检查...",e);
				}
			}
		}		
		return resultBuffer.toString();
	}
	
	/**
	 * 使用HttpClient以JSON格式发送get请求
	 * @param urlParam 请求地址
	 * @param socketTimeout 超时时间
	 * @author Seagull
	 * @date 2019年5月14日
	 */
	public static String httpClientGet(String urlParam,  Client client, Map<String, Object> params,Integer socketTimeout) {
		//测试netty同步等待
		if(Objects.equals(client.getClientType(), 1))
		{
			JSONObject tmp=new JSONObject();
			String uuid= UUID.randomUUID().toString();
			//封装调度参数
			String subStr= ":"+ClientConstants.CLIENT_MONITOR_PORT;
			String tmpMethod=urlParam.substring(urlParam.lastIndexOf(subStr)+subStr.length());
			tmp.put("method","run");
			tmp.put("data",params);
			tmp.put("uuid",uuid);
			tmp.put("url",tmpMethod);
			tmp.put("getOrPost","get");
			tmp.put("socketTimeout",socketTimeout);
			Result re= null;
			try {
				re = NettyServer.write(tmp.toString(),client.getClientIp(), uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assert re != null;
			if(1==re.getCode())
			{
				//请求成功，返回结果
				return (String)re.getMessage();
			}
			else
			{
				throw new RuntimeException();
			}
		}
		StringBuffer resultBuffer;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		BufferedReader br = null;
		// 构建请求参数
		StringBuilder sbParams = new StringBuilder();
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
		if (sbParams.length() > 0) {
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
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
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
				} catch (IOException ignored) {

				}
			}
		}
		return resultBuffer.toString();
	}
	

	/**
	 * 上传文件
	 * @param urlParam 请求URl
	 * @param loadpath 文件路径
	 * @param file 文件对象
	 * @author Seagull
	 * @date 2019年3月15日
	 */
	public static String httpClientUploadFile(String urlParam, Client client, String loadpath, File file) {
		//测试netty同步等待
		if(Objects.equals(client.getClientType(), 1))
		{
			JSONObject tmp=new JSONObject();
			String uuid= UUID.randomUUID().toString();
			//封装调度参数
			String subStr= ":"+ClientConstants.CLIENT_MONITOR_PORT;
			String tmpMethod=urlParam.substring(urlParam.lastIndexOf(subStr)+subStr.length());
			tmp.put("method","download");
			tmp.put("path",loadpath);
			tmp.put("fileName",file.getName());
			tmp.put("uuid",uuid);
			tmp.put("url",tmpMethod);
			Result re= null;
			try {
				re = NettyServer.write(tmp.toString(),client.getClientIp(), uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assert re != null;
			if(1==re.getCode())
			{
				//请求成功，返回结果
				return "上传成功";
			}
			else
			{
				throw new RuntimeException();
			}
		}
		StringBuffer resultBuffer;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(urlParam);
		// 构建请求参数
		BufferedReader br = null;
		try {
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			//设置请求的编码格式  
			entityBuilder.setCharset(StandardCharsets.UTF_8);
			entityBuilder.addBinaryBody("jarfile", file);
			entityBuilder.addTextBody("loadpath", loadpath);
		    HttpEntity reqEntity =entityBuilder.build();
		    httpPost.setEntity(reqEntity);
		    
			CloseableHttpResponse response = httpclient.execute(httpPost);
			 //从状态行中获取状态码  
		     String responsecode = String.valueOf(response.getStatusLine().getStatusCode());
			// 读取服务器响应数据
			resultBuffer = new StringBuffer();

			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			if(resultBuffer.length()==0){
				resultBuffer.append("上传文件异常，响应码：").append(responsecode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ignored) {
				}
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * 获取文件流
	 * @param urlParam 请求地址
	 * @param params 请求参数
	 * @author Seagull
	 * @date 2019年3月15日
	 */
	public static byte[] getFile(String urlParam, Client client, Map<String, Object> params) throws IOException {
		//测试netty同步等待
		if(Objects.equals(client.getClientType(), 1))
		{
            try {
                JSONObject tmp = new JSONObject();
                String uuid = UUID.randomUUID().toString();

                tmp.put("method", "upload");
                tmp.put("data", params);
                tmp.put("uuid", uuid);
                tmp.put("start", 0);
                Result re;
                re = NettyServer.write(tmp.toString(), client.getClientIp(), uuid);
                if (1 == re.getCode()) {
                    //请求成功，返回结果
                    String fileName = params.get("imgName").toString();
                    File file = new File(file_dir + File.separator + fileName);
                    //获取输入流
                    FileInputStream fis = new FileInputStream(file);
                    //新的 byte 数组输出流，缓冲区容量1024byte
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                    //缓存
                    byte[] b = new byte[1024];
                    int n;
                    while ((n = fis.read(b)) != -1) {
                        bos.write(b, 0, n);
                    }
                    fis.close();
                    //改变为byte[]
                    byte[] data = bos.toByteArray();
                    //
                    bos.close();
                    try {
                        file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("删除截图失败，截图文件名为：" + fileName);
                        return new byte[0];
                    }
                    return data;
                } else {
                    log.error("获取截图失败");
                    return new byte[0];

                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取截图失败");
                return new byte[0];
            }
		}
		// 构建请求参数
		StringBuilder sbParams = new StringBuilder();
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
		if (sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
        URL urlConet = new URL(urlParam);
        HttpURLConnection con = (HttpURLConnection)urlConet.openConnection();    
        con.setRequestMethod("GET");    
        con.setConnectTimeout(4 * 1000);    
        InputStream inStream = con .getInputStream();    //通过输入流获取图片数据    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[2048];    
        int len;
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();
		return outStream.toByteArray();
    }

}
