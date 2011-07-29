/* 
 * 创建日期 2011-7-4
 *
 * 成都天和软件公司
 * 电话：028-85425861 
 * 传真：028-85425861-8008 
 * 邮编：610041 
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001 
 * 版权所有
 */
package com.th.spider.connection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * HD1080登陆
 * 
 * @author 王文成
 * @version 1.0
 * @since 2011-7-4
 */
public class HD1080Connection implements JsoupConnection {

	@Override
	public Connection getConnection(String url, int timeout) {
		Connection conn = Jsoup
				.connect(url)
				.data("query", "Java")
				.header("Accept", "*/*")
				.header("Referer", "http://www.ihd1080.org/forum.php?mod=forumdisplay&fid=66")
				.header("Accept-Encoding", "gzip, deflate")
				.header("If-Modified-Since", "Thu, 25 Nov 2010 06:51:00 GMT")
				.header("If-None-Match", "1b0cb91-ee1-495db07517d00")
				.userAgent(
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)")
				.header("Host", "www.ihd1080.org").header("Connection", "Keep-Alive")
				.cookie("oI4T_2132_auth", "37fcafwz%2B2XEIcBFkSIxTGqym7%2FkP%2Fm0gTXVRgl3bpA0KBbkYY8XE6fycTDuJsnVAn4g1So617eyU55xodShNt4").timeout(timeout);
		return conn;
	}
}
