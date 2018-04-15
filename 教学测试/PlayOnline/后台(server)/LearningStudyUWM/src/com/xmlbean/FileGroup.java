package com.xmlbean;

import java.util.Date;


public class FileGroup {
	
	private String fileGroupId;
	private Integer fileGroupStatus;
	private String title;
	private String discribe;
	private String imgSrc;
	
	private String blockId;
	private String adminId;
	private Integer flag;
	private Integer recomNum;
	private Date updateDate;
	


	@Override
	public String toString() {
		return "id:" + this.fileGroupId + "连接：" + this.imgSrc + "作者id:" + this.adminId
				+ "title : " + this.title + "flag : " + this.flag;
	}
	
	public FileGroup() {
	}
	

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public Integer getFileGroupStatus() {
		return fileGroupStatus;
	}

	public void setFileGroupStatus(Integer fileGroupStatus) {
		this.fileGroupStatus = fileGroupStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscribe() {
		return discribe;
	}

	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getRecomNum() {
		return recomNum;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setRecomNum(Integer recomNum) {
		this.recomNum = recomNum;
	}
	

}
