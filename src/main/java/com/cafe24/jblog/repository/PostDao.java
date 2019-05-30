package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;

	public int insert(PostVo postVo) {
		int count = sqlSession.insert("post.insert", postVo);
		return count;
	}

	public List<PostVo> selectTitleList(Long categoryNo) {
		List<PostVo> selectList = sqlSession.selectList("post.selectTitleList", categoryNo);
		return selectList;
	}

	public PostVo getPost(Long postNo) {
		return sqlSession.selectOne("post.selectPost", postNo);
	}

	public int delete(long categoryNo) {
		return sqlSession.delete("post.deleteThisCategoryPost", categoryNo);
	}

	public int insertDefaultPost(String id) {
		return sqlSession.insert("post.insertDefaultPost", id);
		
	}
}
