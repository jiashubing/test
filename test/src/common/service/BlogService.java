package common.service;

import java.net.URL;
import java.util.List;

import common.po.Blog;

public interface BlogService {
	
	void saveBlog(Blog blog);
	void deleteAllBlog();
	void saveBlogList(List<Blog> blogList);
	List<Blog> findBlogList(int pageSize,int pageNo);
	List<Blog> updateBlogList(URL url);

}
