package com.smis.domain;


public class Class {

	private String clid;

	private String clname;

	private Integer oid;

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid == null ? null : clid.trim();
	}

	public String getClname() {
		return clname;
	}

	public void setClname(String clname) {
		this.clname = clname == null ? null : clname.trim();
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

}