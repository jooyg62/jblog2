package com.cafe24.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;

	public BlogVo get(String id) {
		BlogVo blogVo = sqlSession.selectOne("blog.getMyBlogInfo", id);
		return blogVo;
	}
	
	public Boolean insertMyInitBlog(String id) {
		int count = sqlSession.insert("blog.insertMyInitBlog", id);
		return 1 == count;
	}

	public int updateAdminBasic(Map<String, Object> map) {
		int count = sqlSession.update("blog.updateAdminBasic", map);
		return count;
	}

	public int existBlogCount(String id) {
		return sqlSession.selectOne("blog.existBlogCount", id);
	}

	public String getTitle(String id) {
		return sqlSession.selectOne("blog.getBlogTitle", id);
	}
}
