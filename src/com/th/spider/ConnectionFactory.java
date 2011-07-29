/* 
 * 创建日期 2011-5-3
 *
 * 成都天和软件公司
 * 电话：028-85425861 
 * 传真：028-85425861-8008 
 * 邮编：610041 
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001 
 * 版权所有
 */
package com.th.spider;

import org.jsoup.Connection;

import com.th.spider.connection.DefaultConnection;
import com.th.spider.connection.JsoupConnection;

/**
 * Connection创建工厂
 * 
 * @author 王文成
 * @version 1.0
 * @since 2011-5-3
 */
public class ConnectionFactory {

	public static Connection getConnection(String url) throws Exception {
		return getConnection(url, 3000);
	}

	public static Connection getConnection(String url, int timeout) throws Exception {
		String className = Config.getConnectionClass();
		if (className == null) {
			return new DefaultConnection().getConnection(url, timeout);
		} else {
			Class<?> cls = Class.forName(className);
			JsoupConnection connection = (JsoupConnection) cls.newInstance();
			return connection.getConnection(url, timeout);
		}
	}
}
