package com.xmlBean;

public class Block {
	
	private String blockId; //板块Id
	private String subject;  //类型
	
	@Override
	public String toString() {
		return "id: " + this.blockId + "类型： " + this.subject;
	}
	
	public Block() {
	}
	
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
