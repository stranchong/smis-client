package com.smis.model;

import java.util.Map;

import com.lidroid.xutils.http.RequestParams;
import com.smis.util.CustomHttpUtils;

public class TeacherModel extends BaseModel {

	/**
	 * 根据tid获取单个教职工信息
	 * 
	 * @param tid
	 * @return
	 */
	public void getTeacherInfo(String tid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/" + tid + "/info.html", TeacherModel.this);
	}

	/**
	 * 根据tid获取所教课程的学生成绩信息
	 * 
	 * @param tid
	 */
	public void getStudentScoresByTid(String tid, int currRecordIndex, int pageSize) {
		String urlSuffix = "teacher/" + tid + "/student-score/page/" + currRecordIndex + "/" + pageSize + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, TeacherModel.this);
	}

	/** 重载 */
	public void getStudentScoresByTid(String tid) {
		getStudentScoresByTid(tid, INITIAL_CURR_RECORD_INDEX, INITIAL_PAGE_SIZE);
	}

	/**
	 * 分页情况下加载更多所教课程的学生成绩信息
	 * 
	 * @param tid
	 * @param currRecordIndex
	 */
	public void getMoreStudentScoresByTid(String tid, int currRecordIndex) {
		String urlSuffix = "teacher/" + tid + "/student-score/page/" + currRecordIndex + "/" + INITIAL_PAGE_SIZE + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, TeacherModel.this, GET_MORE_DATA);
	}

	/**
	 * 根据tid获取所教课程的学生成绩的总记录数
	 * 
	 * @param tid
	 * @return
	 */
	public void getStudentScoreTotalCountByTid(String tid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/" + tid + "/student-score/total-count.html", TeacherModel.this, GET_TOTAL_COUNT);
	}

	/**
	 * 根据tid修改教职工的密码
	 * 
	 * @param tid
	 * @param pwd
	 */
	public void updatePwdByTid(String tid, String pwd) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("tid", tid);
		requestParams.addBodyParameter("password", pwd);
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("teacher/update.html", requestParams, TeacherModel.this);
	}

	/**
	 * 根据tid获取教职工所教的课程
	 * 
	 * @param tid
	 */
	public void getTeachCoursesByTid(String tid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/" + tid + "/course.html", TeacherModel.this, GET_TEACH_COURSE);
	}

	/**
	 * 根据cid获取修了此课程的所有班级
	 * 
	 * @param cid
	 */
	public void getTeachClassesByCid(String cid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/course/" + cid + "/class.html", TeacherModel.this, GET_TEACH_CLASS);
	}

	/**
	 * 根据clid获取该班级上的所有学生
	 * 
	 * @param clid
	 */
	public void getTeachStudentsByClid(String clid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/class/" + clid + "/student.html", TeacherModel.this, GET_TEACH_STUDENT);
	}

	/**
	 * 录入学生成绩
	 * 
	 * @param params
	 */
	public void addStudentScore(Map<String, String> params) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("cid", params.get("cid"));
		requestParams.addBodyParameter("sid", params.get("sid"));
		requestParams.addBodyParameter("score", params.get("score"));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("teacher/student-score/add.html", requestParams, TeacherModel.this);
	}

	/**
	 * 修改学生成绩
	 * 
	 * @param params
	 */
	public void updateStudentScore(Map<String, String> params) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("cid", params.get("cid"));
		requestParams.addBodyParameter("sid", params.get("sid"));
		requestParams.addBodyParameter("score", params.get("score"));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("teacher/student-score/update.html", requestParams, TeacherModel.this,
				UPDATE_STUDENT_SCORE);
	}

	/**
	 * 根据tid获取高一层次管理该登录教职工的所有教职工
	 * 
	 * @param sid
	 * @return
	 */
	public void getSuperiorLevelTeachersByTid(String tid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/" + tid + "/superior.html", TeacherModel.this, GET_MANAGE_TEACHER);
	}

	/**
	 * 根据tid获取所管理学生的分页请假信息
	 * 
	 * @param tid
	 */
	public void getPageStudentApplicationsByTid(String tid, int currRecordIndex, int pageSize) {
		String urlSuffix = "teacher/" + tid + "/application/page/" + currRecordIndex + "/" + pageSize + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, TeacherModel.this);
	}

	/** 重载 */
	public void getPageStudentApplicationsByTid(String tid) {
		getPageStudentApplicationsByTid(tid, INITIAL_CURR_RECORD_INDEX, INITIAL_PAGE_SIZE);
	}

	/**
	 * 加载更多的所管理学生的分页请假信息
	 * 
	 * @param tid
	 * @param currRecordIndex
	 */
	public void getMoreStudentApplicationsByTid(String tid, int currRecordIndex) {
		String urlSuffix = "teacher/" + tid + "/application/page/" + currRecordIndex + "/" + INITIAL_PAGE_SIZE + ".html";
		// 发送Get请求
		CustomHttpUtils.sendGetRequest(urlSuffix, TeacherModel.this, GET_MORE_DATA);
	}

	/**
	 * 根据tid获取所管理学生请假信息的总记录条数
	 * 
	 * @param tid
	 */
	public void getStudentApplicationTotalCountByTid(String tid) {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/" + tid + "/application/total-count.html", TeacherModel.this, GET_TOTAL_COUNT);
	}

	/**
	 * 根据aid审批请假信息
	 * 
	 * @param params
	 */
	public void updateApplicationByAid(Map<String, String> params) {
		// 设置请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("aid", params.get("aid"));
		requestParams.addBodyParameter("startDate", params.get("startDate"));
		requestParams.addBodyParameter("countDay", params.get("countDay"));
		requestParams.addBodyParameter("confirmTid", params.get("confirmTid"));
		requestParams.addBodyParameter("confirmMark", params.get("confirmMark"));
		requestParams.addBodyParameter("confirmReply", params.get("confirmReply"));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest("teacher/application/update.html", requestParams, TeacherModel.this,
				UPDATE_STUDENT_APPLICATION);
	}

}