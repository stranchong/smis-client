package com.smis.model;

import java.util.Map;

import android.os.Bundle;

import com.lidroid.xutils.http.RequestParams;
import com.smis.util.CustomHttpUtils;

public class StudentModel extends BaseModel {

	/**
	 * 根据sid获取单个学生信息
	 * 
	 * @param sid
	 * @return
	 */
	public void getStudentInfoBySid(String sid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/" + sid + "/info.html", StudentModel.this);
	}

	/**
	 * 根据sid获取单个学生的分页课程成绩信息
	 * 
	 * @param sid
	 */
	public void getPageCourseScoresBySid(String sid, int currRecordIndex, int pageSize) {
		String urlSuffix = "student/" + sid + "/score/page/" + currRecordIndex + "/" + pageSize + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, StudentModel.this);
	}

	/** 重载 */
	public void getPageCourseScoresBySid(String sid) {
		getPageCourseScoresBySid(sid, INITIAL_CURR_RECORD_INDEX, INITIAL_PAGE_SIZE);
	}

	/**
	 * 加载更多单个学生的分页课程成绩信息
	 * 
	 * @param sid
	 * @param currRecordIndex
	 */
	public void getMoreCourseScoresBySid(String sid, int currRecordIndex) {
		String urlSuffix = "student/" + sid + "/score/page/" + currRecordIndex + "/" + INITIAL_PAGE_SIZE + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, StudentModel.this, GET_MORE_DATA);
	}

	/**
	 * 根据sid获取单个学生所有课程成绩的总记录数
	 * 
	 * @param sid
	 * @return
	 */
	public void getCourseScoreTotalCountBySid(String sid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/" + sid + "/score/total-count.html", StudentModel.this, GET_TOTAL_COUNT);
	}

	/**
	 * 根据sid修改学生的密码
	 * 
	 * @param sid
	 * @param pwd
	 */
	public void updatePwdBySid(String sid, String pwd) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("sid", sid);
		requestParams.addBodyParameter("password", pwd);
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("student/update.html", requestParams, StudentModel.this);
	}

	/**
	 * 根据sid获取管理该学生的中队长
	 * 
	 * @param sid
	 * @return
	 */
	public void getManageTeachersBySid(String sid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/" + sid + "/manage-teacher.html", StudentModel.this, GET_MANAGE_TEACHER);
	}

	public void addApplication(Map<String, String> params) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("sid", params.get("sid"));
		requestParams.addBodyParameter("tid", params.get("tid"));
		requestParams.addBodyParameter("startDate", params.get("startDate"));
		requestParams.addBodyParameter("overDate", params.get("overDate"));
		requestParams.addBodyParameter("leavePlace", params.get("leavePlace"));
		requestParams.addBodyParameter("leaveReason", params.get("leaveReason"));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("student/application/add.html", requestParams, StudentModel.this);
	}

	/**
	 * 根据sid获取单个学生分页的请假信息
	 * 
	 * @param sid
	 */
	public void getPageApplicationsBySid(String sid, int currRecordIndex, int pageSize) {
		String urlSuffix = "student/" + sid + "/application/page/" + currRecordIndex + "/" + pageSize + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, StudentModel.this);
	}

	/** 重载 */
	public void getPageApplicationsBySid(String sid) {
		getPageApplicationsBySid(sid, INITIAL_CURR_RECORD_INDEX, INITIAL_PAGE_SIZE);
	}

	/**
	 * 加载更多单个学生的分页课程成绩信息
	 * 
	 * @param sid
	 * @param currRecordIndex
	 */
	public void getMoreApplicationsBySid(String sid, int currRecordIndex) {
		String urlSuffix = "student/" + sid + "/application/page/" + currRecordIndex + "/" + INITIAL_PAGE_SIZE + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, StudentModel.this, GET_MORE_DATA);
	}

	/**
	 * 根据sid获取单个学生请假信息的总记录条数
	 * 
	 * @param sid
	 */
	public void getApplicationTotalCountBySid(String sid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/" + sid + "/application/total-count.html", StudentModel.this, GET_TOTAL_COUNT);
	}

	/**
	 * 根据aid删除请假信息
	 * 
	 * @param aid
	 */
	public void deleteApplicationByAid(String aid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/application/" + aid + "/delete.html", StudentModel.this, DELETE_APPLICATION);
	}

	/**
	 * 根据aid修改请假信息
	 * 
	 * @param params
	 */
	public void updateApplicationByAid(Bundle params) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("aid", params.getString("aid"));
		requestParams.addBodyParameter("startDate", params.getString("startDate"));
		requestParams.addBodyParameter("overDate", params.getString("overDate"));
		requestParams.addBodyParameter("leavePlace", params.getString("leavePlace"));
		requestParams.addBodyParameter("leaveReason", params.getString("leaveReason"));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("student/application/update.html", requestParams, StudentModel.this);
	}
	
}