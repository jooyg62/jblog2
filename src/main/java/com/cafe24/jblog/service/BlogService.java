package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.FileVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
	
	public Map<String, Object> getMyBlogMain(String id, Long cateNo, Long postNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 1. 블로그 정보 세팅( 카테고리 리스트, 포스트 리스트 )
		BlogVo blogVo = blogDao.get(id);
		
		List<CategoryVo> categoryVoList = blogVo.getCategoryVoList();
		List<PostVo> postVoList = blogVo.getPostVoList();
		
		// 1-1. 카테고리 리스트 조회
		List<CategoryVo> cateSelectList = categoryDao.select(id);
		
		if(cateSelectList != null) {
			categoryVoList.addAll(cateSelectList);
		}
		
		// 1-2. 포스트 리스트 조회
		List<PostVo> postSelectList = null;
				
		if(cateNo == 0) {
			// 첫 화면 진입일 경우
			// : 첫번째 카테고리에 대한 포스트 리스트를 조회한다.
			CategoryVo categoryVo = cateSelectList.get(0);
			postSelectList = postDao.selectTitleList(categoryVo.getNo());
		} else {
			postSelectList = postDao.selectTitleList(cateNo);			
		}
		
		if(postSelectList != null) {
			postVoList.addAll(postSelectList);
		}
		
		map.put("blogVo", blogVo);
		
		PostVo postVo = null;
		
		// 2. 포스트 첫 화면 조회
		if(postNo != 0L) {
			// 1-1. 포스트 선택.
			postVo = postDao.getPost(postNo);
		} else if(postSelectList != null && postSelectList.size() != 0) {
			// 2-2. 화면 첫 진입이나 카테고리를 선택 했을 때
			PostVo selectedPostVo = postSelectList.get(0);
			postVo = postDao.getPost(selectedPostVo.getNo());
		} else {
			// 2-3. 선택한 카테고리에 포스트가 없는 경우.
			postVo = new PostVo();
		}
	
		map.put("postVo", postVo);
		
		return map;
	}
	
	public BlogVo getMyBlogInfo(String id) {
		return blogDao.get(id);
	}

	public boolean updateAdminBasic(String id, String title, MultipartFile multipartFile) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("title", title);
		
		if(multipartFile != null && multipartFile.getSize() != 0) {
			FileVo fileVo = fileuploadService.restore(multipartFile);
			map.put("logo", fileVo.getSaveFileName());			
		}
		
		return 1 == blogDao.updateAdminBasic(map);
	}

	public boolean regCategory(String id, String cateName, String desc) {
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(id);
		categoryVo.setName(cateName);
		categoryVo.setDiscription(desc);
		
		return 1 == categoryDao.insert(categoryVo);
	}

	public List<CategoryVo> getAllCategory(String id) {
		return categoryDao.select(id);
	}

	public boolean regPost(String id, PostVo postVo) {
		postVo.setUserId(id);
		return 1 == postDao.insert(postVo);
	}

	public boolean deleteCategory(long categoryNo) {
		postDao.delete(categoryNo);
		return 1 == categoryDao.delete(categoryNo);
	}

	/**
	 * 블로그 타이틀 가져오기
	 */
	public String getTitle(String id) {
		return blogDao.getTitle(id);
	}
	
}
