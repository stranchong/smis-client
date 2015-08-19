package com.smis.model;

/**
 * 
 * @描述 响应回调接口
 * 
 */
public interface BusinessResponse {
	/** 公用的常量 */
	public final static String DEBUG = "debug";
	public final static String SUCCESS = "success";
	public final static String TRUE = "true";
	public final static String FALSE = "false";
	public final static String MSG = "msg";
	public final static String OBJ = "obj";

	public final static int DEFAULT_METHOD_CODE = 1000;
	public final static int LOGIN = 0;
	public final static int GET_TOTAL_COUNT = 1;
	public final static int GET_MORE_DATA = 2;
	public final static int GET_MANAGE_TEACHER = 3;
	public final static int DELETE_APPLICATION = 4;
	public final static int GET_SUPERIOR_LEVEL = 5;
	public final static int GET_TEACH_COURSE = 6;
	public final static int GET_TEACH_CLASS = 7;
	public final static int GET_TEACH_STUDENT = 8;
	public final static int UPDATE_STUDENT_SCORE = 9;
	public final static int UPDATE_STUDENT_APPLICATION = 10;

	public final static int INITIAL_CURR_RECORD_INDEX = 0;
	public final static int INITIAL_PAGE_SIZE = 3;

	/** LoginActivity和LoginModel用的常量 */
	/** 键值对的键的名称 */
	public final static String USERNAME_KEY = "username key";
	public final static String USERNAME_VALUE = "username value";
	public final static String PASSWORD = "password";
	public final static String URL_SUFFIX = "url_Suffix";
	public final static String IDENTITY = "identity";
	/** 字符串常量值 */
	public final static String REQUEST_SEND_FAIL = "服务器连接失败";
	public final static String STUDENT = "student";
	public final static String SID = "sid";
	public final static String STUDENT_LOGIN_URL_SUFFIX = "student/login.html";
	public final static String TEACHER = "teacher";
	public final static String TID = "tid";
	public final static String TEACHER_LOGIN_URL_SUFFIX = "teacher/login.html";

	
	/** 下面的方法都是回调方法 */
	public abstract void OnRequestFailure();

	public abstract void OnMessageResponse(Object... results);

	public abstract void OnLoginResponse(Object... results);
	
	public abstract void OnGetTotalCountResponse(Object... results);

	public abstract void OnGetMoreDataResponse(Object... results);

	public abstract void OnGetManageTeacherResponse(Object... results);

	public abstract void OnDeleteApplicationResponse(Object... results);

	public abstract void OnGetSuperiorLevelResponse(Object... results);

	public abstract void OnGetTeachCourseResponse(Object... results);

	public abstract void OnGetTeachClassponse(Object... results);

	public abstract void OnGetTeachStudentResponse(Object... results);

	public abstract void OnUpdateStudentScoreResponse(Object... results);

	public abstract void OnUpdateStudentApplicationResponse(Object... results);

}