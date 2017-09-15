package common.test;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import common.po.Blog;
import common.po.WeiBo;

@SuppressWarnings("unused")
public class BlogTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2();
//		String csn =  Charset.defaultCharset().name(); 
//		System.out.println(csn); 
	}
	
	private static void test1(){
		List<Blog> lists = new ArrayList<Blog>();
		String urlString = "http://www.cnblogs.com/acm-bingzi/article/rss";
		try {
			URL url = new URL(urlString);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = null;
			URLConnection conn;
			conn = url.openConnection();
			String content_encoding = conn.getHeaderField("Content-Encoding");
			if (content_encoding != null && content_encoding.contains("gzip")) {
				GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());
				feed = input.build(new XmlReader(gzin));
			} else {
				feed = input.build(new XmlReader(conn.getInputStream()));
			}

			@SuppressWarnings("unchecked")
			List<SyndEntry> entries = feed.getEntries();// 得到所有的标题<title></title>
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = (SyndEntry) entries.get(i);
				Blog tmp = new Blog();
				tmp.setTitle(entry.getTitle());
				System.out.println("标题： "+entry.getTitle());
				tmp.setDescription(entry.getDescription().getValue());
				System.out.println("描述： "+entry.getDescription().getValue());
				tmp.setUpdateDate(entry.getUpdatedDate());
				System.out.println("更新时间： "+entry.getUpdatedDate());
				tmp.setUri(entry.getUri());
				System.out.println("原文： "+entry.getUri());
				lists.add(tmp);
				System.out.println("");
			}
		} catch (Exception e) {
//			System.out.println("出错了！");
		}
	}
	
	private static void test2(){
		List<WeiBo> lists = new ArrayList<WeiBo>();
			String urlString = "http://rss.weibodangan.com/weibo/rss/3225300442/";
		try {
			URL url = new URL(urlString);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = null;
			URLConnection conn;
			conn = url.openConnection();
			String content_encoding = conn.getHeaderField("Content-Encoding");
			if (content_encoding != null && content_encoding.contains("gzip")) {
				GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());
				feed = input.build(new XmlReader(gzin));
			} else {
				feed = input.build(new XmlReader(conn.getInputStream()));
			}
			
			@SuppressWarnings("unchecked")
			List<SyndEntry> entries = feed.getEntries();// 得到所有的标题<title></title>
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = (SyndEntry) entries.get(i);
				WeiBo tmp = new WeiBo();
				tmp.setTitle(entry.getTitle());
				System.out.println("标题： "+entry.getTitle());
//				tmp.setDescription(entry.getDescription().getValue());
				System.out.println("描述： "+entry.getDescription().getValue());
//				tmp.setUri(entry.getUri());
				System.out.println("链接： "+entry.getUri());
//				lists.add(tmp);
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("出错了！");
		}
	}

}
