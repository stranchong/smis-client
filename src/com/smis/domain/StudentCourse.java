package com.smis.domain;

public class StudentCourse extends StudentCourseKey {

	private Integer score;

	private Course course;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}