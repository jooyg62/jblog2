package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
	
	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getId(), userVo.getPassword()); 
	}
	
	public Boolean existId(String id) {
		UserVo userVo = userDao.get(id);
		return userVo != null;
	}
	
	public Boolean join(UserVo userVo) {
		Boolean result = false;
		Boolean userInsertSuccess = userDao.insert(userVo);
		if(userInsertSuccess) {
			blogDao.insertMyInitBlog(userVo.getId());
			
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setName("미분류");
			categoryVo.setDiscription("미분류");
			categoryVo.setBlogId(userVo.getId());
			
			// Default 카테고리 등록
			categoryDao.insert(categoryVo);
			
			// Default 포스트 등록
			postDao.insertDefaultPost(userVo.getId());
			
			result = true;
		}
		
		return result;
	}
}
