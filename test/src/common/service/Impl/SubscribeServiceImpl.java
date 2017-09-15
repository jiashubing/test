package common.service.Impl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.stereotype.Service;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import common.service.SubscribeService;
import common.vo.BlogVo;

@Service
public class SubscribeServiceImpl implements SubscribeService{

	@Override
	public List<BlogVo> parseXml(URL url) throws IllegalArgumentException,
	FeedException {
		List<BlogVo> lists = new ArrayList<BlogVo>();
		try {
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
				BlogVo tmp = new BlogVo();
				tmp.setTitle(entry.getTitle());
				tmp.setDescription(entry.getDescription().getValue());
				tmp.setUpdateDate(entry.getUpdatedDate());
				tmp.setUri(entry.getUri());
				lists.add(tmp);
			}
			return lists;
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return null;
	}
}
