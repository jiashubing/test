package common.service.Impl;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import common.po.WeiBo;
import common.service.WeiBoService;

@Service
@Transactional
public class WeiBoServiceImpl implements WeiBoService{
	
	@PersistenceContext 
	protected EntityManager em;

	@Override
	public void saveWeiBo(WeiBo weiBo) {
		em.persist(weiBo);
	}

	@Override
	public void deleteAllWeiBo() {
		Query query = em.createQuery("delete from WeiBo where 1=1");
		query.executeUpdate();
	}

	@Override
	public void saveWeiBoList(List<WeiBo> weiBoList) {
		for(WeiBo weibo:weiBoList){
			saveWeiBo(weibo);
		}
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<WeiBo> findWeiBoList(int pageSize, int pageNo) {
		String hql="from WeiBo";
		Query query = em.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<WeiBo> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}


	@Override
	public List<WeiBo> updateWeiBoList(URL url) {
		List<WeiBo> lists = new ArrayList<WeiBo>();
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
			int len = entries.size()>10 ? 10 : entries.size();
			for (int i = 0; i < len; i++) {
				SyndEntry entry = (SyndEntry) entries.get(i);
				WeiBo tmp = new WeiBo();
				tmp.setTitle(entry.getTitle());
				String description = entry.getDescription().getValue();
				description = description.replaceAll("img src=","img style=\"max-width:100%\" src=");
				tmp.setDescription(description);
				tmp.setUri(entry.getUri());
				lists.add(tmp);
			}
			if(lists != null && lists.size()>0){
				deleteAllWeiBo();
				try{
					saveWeiBoList(lists);
				} catch (Exception e){
					//do nothing
				}
			}
			return lists;
		} catch (Exception e) {
			return null;
		}
	}	

}
