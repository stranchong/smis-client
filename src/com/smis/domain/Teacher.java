package com.smis.domain;

import java.util.List;

public class Teacher {

	private String tid;

	private String password;

	private String tname;

	private Boolean isManager;

	private Boolean isTeacher;

	private Long rid;

	/** 关联属性 */
	private Faculty faculty;
	private Level level;

	private List<Course> courses;
	private List<Class> classes;

	public List<Class> getClasses() {
		return classes;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public Level getLevel() {
		return level;
	}

	public String getPassword() {
		return password;
	}

	public Long getRid() {
		return rid;
	}

	public String getTid() {
		return tid;
	}

	public String getTname() {
		return tname;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setTid(String tid) {
		this.tid = tid == null ? null : tid.trim();
	}

	public void setTname(String tname) {
		this.tname = tname == null ? null : tname.trim();
	}

}