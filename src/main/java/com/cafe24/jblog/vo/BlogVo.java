package com.cafe24.jblog.vo;

import java.util.ArrayList;
import java.util.List;

public class BlogVo {
	private String userId;
	private String title;
	private String logo;
	private List<PostVo> postVoList = new ArrayList<PostVo>();
	private List<CategoryVo> CategoryVoList = new ArrayList<CategoryVo>();
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<PostVo> getPostVoList() {
		return postVoList;
	}
	public void setPostVoList(List<PostVo> postVoList) {
		this.postVoList = postVoList;
	}
	public List<CategoryVo> getCategoryVoList() {
		return CategoryVoList;
	}
	public void setCategoryVoList(List<CategoryVo> categoryVoList) {
		CategoryVoList = categoryVoList;
	}
	@Override
	public String toString() {
		return "BlogVo [userId=" + userId + ", title=" + title + ", logo=" + logo + ", postVoList=" + postVoList
				+ ", CategoryVoList=" + CategoryVoList + "]";
	}
}
