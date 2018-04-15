package com.xmlbean;

import java.util.Date;

public class Comment {
	
	private String commentId;
	private String fileId;
	private String userId;
	private String content;
	private Date date;
	
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", fileId=" + fileId + ", userId=" + userId + ", content=" + content
				+ ", date=" + date + "]";
	}
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
