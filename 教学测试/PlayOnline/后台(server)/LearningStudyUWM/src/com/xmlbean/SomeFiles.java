package com.xmlbean;

import java.util.Date;

public class SomeFiles {
	
	private String fileId; 
	private String fileGroupId; //视频组id
	private String title;
	private String fileSrc; //文件链接
	private Date uploadTime; //上传时间
	
	
	public SomeFiles() {
	}
	
	@Override
	public String toString() {
		return "标题:" + this.title + "文件链接：" + this.fileSrc + "时间: " + this.uploadTime;
	}
	
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileGroupId() {
		return fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileSrc() {
		return fileSrc;
	}
	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
