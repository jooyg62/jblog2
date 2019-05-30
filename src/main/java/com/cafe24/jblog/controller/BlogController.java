package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.security.Auth;
import com.cafe24.jblog.security.AuthUser;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;

@RequestMapping("/{id:(?:(?!assets)(?!images)).*}")
@Controller
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@RequestMapping(value= {"", "/{categoryNoUrl}", "/{categoryNoUrl}/{postNoUrl}"}, method=RequestMethod.GET)
	public String main(
			@PathVariable String id,
			@PathVariable Optional<Long> categoryNoUrl,
			@PathVariable Optional<Long> postNoUrl,
			Model model
	) {
		Long cateNo = categoryNoUrl.isPresent() ?  categoryNoUrl.get() : 0L;
		Long postNo = postNoUrl.isPresent() 	?  postNoUrl.get() : 0L;
		
		Map<String, Object> map = blogService.getMyBlogMain(id, cateNo, postNo);
		model.addAllAttributes(map);
		model.addAttribute("id", id);
		
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			Model model) {
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		BlogVo blogVo = blogService.getMyBlogInfo(id);
		model.addAttribute("vo", blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic/update", method=RequestMethod.POST)
	public String updateAdminBasic(
			@PathVariable String id,
			@RequestParam String title,
			@AuthUser UserVo authUser,
			@RequestParam(value="logo-file", required=false) MultipartFile multipartFile) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		blogService.updateAdminBasic(id, title, multipartFile);
		
		return "redirect:/"+id+"/admin/basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			Model model) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		List<CategoryVo> categoryVoList = blogService.getAllCategory(id);
		model.addAttribute("categoryVoList", categoryVoList);
		model.addAttribute("blogTitle", blogService.getTitle(id));
		
		return "blog/blog-admin-category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			Model model) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		List<CategoryVo> categoryVoList = blogService.getAllCategory(id);
		model.addAttribute("categoryVoList", categoryVoList);
		model.addAttribute("blogTitle", blogService.getTitle(id));
		
		return "blog/blog-admin-write";
	}
	
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String insertAdminCategory(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			@RequestParam(value="name", required=false, defaultValue="새로운 카테고리") String cateName,
			@RequestParam(value="desc", required=false, defaultValue="새로운 카테고리 생성") String desc) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		blogService.regCategory(id, cateName, desc);
		
		return "redirect:/"+id+"/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String insertAdminWrite(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			@ModelAttribute PostVo postVo) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		if(postVo.getTitle().length() == 0 || postVo.getContents().length() == 0) {
			return "redirect:/"+id+"/admin/write";
		}
		
		blogService.regPost(id, postVo);
		
		return "redirect:/"+id+"/admin/write";
	}
	
	@Auth
	@RequestMapping(value="/admin/category/delete/{categoryNo}", method=RequestMethod.POST)
	public String deleteAdminCategory(
			@PathVariable String id,
			@AuthUser UserVo authUser,
			@PathVariable long categoryNo) {
		
		if(!id.equals(authUser.getId())) {
			return "redirect:/user/login";
		}
		
		blogService.deleteCategory(categoryNo);
		
		return "redirect:/"+id+"/admin/category";
	}
}
