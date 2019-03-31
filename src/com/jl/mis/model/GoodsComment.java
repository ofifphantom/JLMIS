package com.jl.mis.model;

import java.util.Date;

public class GoodsComment {
	//评论id
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//用户头像
	private String personPicture;
	//用户名
	private String personName;
	//星级
	private double score;
	//内容
	private String content;
	//图片
	private String commentUrl;
	//时间
	private Date time;
	//评价图片标签
	private String imgUrl;
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getPersonPicture() {
		return personPicture;
	}
	public void setPersonPicture(String personPicture) {
		this.personPicture = personPicture;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "GoodsComment [id=" + id + ", personPicture=" + personPicture + ", personName=" + personName + ", score="
				+ score + ", content=" + content + ", commentUrl=" + commentUrl + ", time=" + time + ", imgUrl=" + imgUrl
				+ "]";
	}
	
	
}
