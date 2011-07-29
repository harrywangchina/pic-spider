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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 爬虫主程序
 * 
 * @author Fantasy
 * @version 1.0
 * @since 2011-5-3
 */
public class Main {

	private static final Log log = LogFactory.getLog(Main.class);
	
	/**
	 * 抓取开始
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//从第起始页抓到结束页的图片
		int start = Config.getPageStart();
		int end = Config.getPageEnd();
		log.info("文件保存路径[" + Config.getSaveDir() + "]");
		start(start,end);
	}
	
	/**
	 * 开始
	 * @param pageStart
	 * @param pageEnd
	 * @throws Exception
	 */
	public static void start(int pageStart , int pageEnd) throws Exception {
		List<ListPage> pages = getPages(pageStart, pageEnd);
		for (ListPage page : pages) {
			final String pageId = page.getPageId();
			for (final Link link : page.getLinks()) {
				// 不能请求的太快了... 小心被服务器拒绝
				Thread.sleep(3000);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new Spider(pageId, link).find();
						} catch (Exception e) {
							log.error("Sprider Fail! message=[" + e.getMessage() + "]");
						}
					}
				}).start();
			}
		}
	}

	/**
	 * 取得列表页面
	 * 
	 * @param listUrl
	 * @return
	 * @throws Exception
	 */
	private static List<ListPage> getPages(int start, int end) throws Exception {
		List<ListPage> pages = new ArrayList<ListPage>();
		for (int i = start; i <= end; i++) {
			Thread.sleep(1000);
			final String pageId = String.valueOf(i);
			String url = Config.getPageUrl().replace("#page#", pageId);
			ListPage page = getListPage(pageId, url);
			pages.add(page);
			log.info("\n" + page);
		}
		return pages;
	}

	/**
	 * 取得列表页面
	 * 
	 * @param listUrl
	 * @return
	 * @throws Exception
	 */
	private static ListPage getListPage(String pageId, String listUrl) throws Exception {
		Connection conn = ConnectionFactory.getConnection(listUrl, 5000);
		Document doc = conn.get();
		Elements elements = doc.select(Config.getPostPattern());
		List<Link> links = new ArrayList<Link>();
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			String name = getPostName(element);
			String href = getPostUrl(element);
			links.add(new Link(name, href));
		}
		return new ListPage(pageId, links);
	}
	
	/**
	 * 取得主题URL
	 * @param element
	 * @return
	 */
	private static String getPostUrl(Element element){
		return element.attr(Config.getPostUrlAttr());
	}
	
	/**
	 * 取得主题名称
	 * @param element
	 * @return
	 */
	private static String getPostName(Element element){
		String postNameAttr = Config.getPostNameAttr();
		if( postNameAttr != null ){
			return element.attr(postNameAttr);
		}
		return element.text();
	}
}
