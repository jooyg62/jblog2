package com.cafe24.jblog.vo;

public class FileVo {
	String originalFilename;
	String extName;
	String saveFileName;
	String url;
	
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "FileVo [originalFilename=" + originalFilename + ", extName=" + extName + ", saveFileName="
				+ saveFileName + ", url=" + url + ", getOriginalFilename()=" + getOriginalFilename() + ", getExtName()="
				+ getExtName() + ", getSaveFileName()=" + getSaveFileName() + ", getUrl()=" + getUrl() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
