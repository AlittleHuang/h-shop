package com.shengchuang.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public abstract class HttpClientUtil {

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

	public static String sendHttpRequest(HttpRequestBase request, String charset) {
		CloseableHttpResponse response = null;
		try {
			response = HTTP_CLIENT.execute(request);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					return EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static String post(String url, Map<String, String> map, String charset) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<String, String> e : map.entrySet()) {
			list.add(new BasicNameValuePair(e.getKey().trim(), e.getValue().trim()));
		}
		return post(url, list, charset);
	}

	public static String post(
								String url,
								List<? extends NameValuePair> parameters)
	{
		return post(url, parameters, DEFAULT_CHARSET);
	}

	public static String post(String url, String parameters) {
		return post(url, parameters, DEFAULT_CHARSET);
	}

	public static String post(
								String url,
								List<? extends NameValuePair> parameters,
								String charset)
	{
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		if (parameters != null && parameters.size() > 0) {
			UrlEncodedFormEntity entity;
			try {
				entity = new UrlEncodedFormEntity(parameters, charset);
				httpPost.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sendHttpRequest(httpPost, charset);
	}

	public static String post(String url, String parameters, String charset) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			StringEntity entity = new StringEntity(parameters);
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return sendHttpRequest(httpPost, charset);
	}

	public static String get(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
		return sendHttpRequest(httpGet, charset);
	}

	public static String get(String url) {
		return get(url, DEFAULT_CHARSET);
	}

	public static String post(String url) {
		return post(url, (List<NameValuePair>) null, DEFAULT_CHARSET);
	}

}