package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;

@Service
public class MainService {
	
	@Autowired
	private BlogDao blogDao;

	public boolean searchBlog(String id) {
		int count = blogDao.existBlogCount(id);
		return 1 == count;
	}
	
	
	
}
