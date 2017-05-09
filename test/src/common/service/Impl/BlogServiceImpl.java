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

import common.po.Blog;
import common.service.BlogService;

@Service
@Transactional
public class BlogServiceImpl implements BlogService{

	@PersistenceContext 
	protected EntityManager em;

	@Override
	public void saveBlog(Blog blog) {
		em.persist(blog);
	}

	@Override
	public void deleteAllBlog() {
		Query query = em.createQuery("delete from Blog where 1=1");
		query.executeUpdate();
	}

	@Override
	public void saveBlogList(List<Blog> blogList) {
		for(Blog blog:blogList){
			saveBlog(blog);
		}
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Blog> findBlogList(int pageSize, int pageNo) {
		String hql="from Blog";
		Query query = em.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Blog> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}

	@Override
	public List<Blog> updateBlogList(URL url){
		List<Blog> lists = new ArrayList<Blog>();
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
				Blog tmp = new Blog();
				tmp.setTitle(entry.getTitle());
				tmp.setDescription(entry.getDescription().getValue());
				tmp.setUpdateDate(entry.getUpdatedDate());
				tmp.setUri(entry.getUri());
				lists.add(tmp);
			}
			if(lists != null && lists.size()>0){
				deleteAllBlog();
				saveBlogList(lists);
			}
			return lists;
		} catch (Exception e) {
			return null;
		}
	}	

}
