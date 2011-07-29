/* 
 * 创建日期 2011-6-29
 *
 * 成都天和软件公司
 * 电话：028-85425861 
 * 传真：028-85425861-8008 
 * 邮编：610041 
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001 
 * 版权所有
 */
package com.th.spider;

import java.util.Vector;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/**
 * 功能说明
 * 
 * @author 王文成
 * @version 1.0
 * @since 2011-6-29
 */
public class HttpClientPool {

	private static ObjectPool pool = new StackObjectPool(new HttpClientFactory(),50);

	public static HttpClient getHttpClient() throws Exception {
		return (HttpClient) pool.borrowObject();
	}

	public static void reuturnHttpClient(HttpClient httpClient) throws Exception {
		pool.returnObject(httpClient);
	}

	static class HttpClientFactory extends BasePoolableObjectFactory {

		Vector<HttpClient> vector = new Vector<HttpClient>();

		public Object makeObject() {
			BasicHttpParams httpParams = new BasicHttpParams();
			// 设置连接超时10秒
			HttpConnectionParams.setConnectionTimeout(httpParams, 10* 1000);
			// 设置等待数据超时10秒
			HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
			HttpClient client = new DefaultHttpClient(httpParams);
			return client;
		}
	}
}
