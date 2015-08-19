package com.smis.domain;

import java.util.Date;

public class Application {

	private Long aid;

	private String sid;

	private Date createDate;

	private Date startDate;

	private Date overDate;

	private Integer countDay;

	private String leaveReason;

	private String leavePlace;

	private String confirmTid;

	private Integer confirmMark;

	private String confirmName;

	private String confirmReply;

	private Date confirmDate;

	/** 学生选择的审核教师tid */
	private String tid;

	/** 关联表属性，返回给客户端的 */
	private String sname;
	private String clname;

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public Integer getCountDay() {
		return countDay;
	}

	public void setCountDay(Integer countDay) {
		this.countDay = countDay;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getLeavePlace() {
		return leavePlace;
	}

	public void setLeavePlace(String leavePlace) {
		this.leavePlace = leavePlace;
	}

	public String getConfirmTid() {
		return confirmTid;
	}

	public void setConfirmTid(String confirmTid) {
		this.confirmTid = confirmTid;
	}

	public Integer getConfirmMark() {
		return confirmMark;
	}

	public void setConfirmMark(Integer confirmMark) {
		this.confirmMark = confirmMark;
	}

	public String getConfirmName() {
		return confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	public String getConfirmReply() {
		return confirmReply;
	}

	public void setConfirmReply(String confirmReply) {
		this.confirmReply = confirmReply;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getClname() {
		return clname;
	}

	public void setClname(String clname) {
		this.clname = clname;
	}

}