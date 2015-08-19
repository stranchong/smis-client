package com.smis.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @描述 业务处理层的基类，所有业务类都可以继承此类，这里可以负责处理一些通用的问题。比如联网超时，找不到服务器等。
 * 
 */
public class BaseModel implements BusinessResponse {

	// 存放响应的列表
	private List<BusinessResponse> responseList = new ArrayList<BusinessResponse>();

	/**
	 * 将响应加到响应列表当中
	 * 
	 * @param response
	 */
	public void addResponseListener(BusinessResponse response) {
		if (!responseList.contains(response)) {
			responseList.add(response);
		}
	}

	@Override
	public void OnRequestFailure() {
		for (BusinessResponse response : responseList) {
			response.OnRequestFailure();
		}
	}

	@Override
	public void OnMessageResponse(Object... results) {
		// 迭代将列表中的响应回调到activity中，回调之前可以先处理一下，比如判断回调的状态值等等
		for (BusinessResponse response : responseList) {
			// 根据CustomHttpUtils回调方法onSuccess传来的第一个参数（方法代号），来判断调用Activity的哪个方法
			switch ((Integer) results[0]) {
			case DEFAULT_METHOD_CODE:
				response.OnMessageResponse(results[1]);
				break;
			case LOGIN:
				response.OnLoginResponse(results[1]);
				break;
			case GET_TOTAL_COUNT:
				response.OnGetTotalCountResponse(results[1]);
				break;
			case GET_MORE_DATA:
				response.OnGetMoreDataResponse(results[1]);
				break;
			case GET_MANAGE_TEACHER:
				response.OnGetManageTeacherResponse(results[1]);
				break;
			case DELETE_APPLICATION:
				response.OnDeleteApplicationResponse(results[1]);
				break;
			case GET_SUPERIOR_LEVEL:
				response.OnGetSuperiorLevelResponse(results[1]);
				break;
			case GET_TEACH_COURSE:
				response.OnGetTeachCourseResponse(results[1]);
				break;
			case GET_TEACH_CLASS:
				response.OnGetTeachClassponse(results[1]);
				break;
			case GET_TEACH_STUDENT:
				response.OnGetTeachStudentResponse(results[1]);
				break;
			case UPDATE_STUDENT_SCORE:
				response.OnUpdateStudentScoreResponse(results[1]);
				break;
			case UPDATE_STUDENT_APPLICATION:
				response.OnUpdateStudentApplicationResponse(results[1]);
				break;
			}
		}
	}

	@Override
	public void OnLoginResponse(Object... results) {

	}

	@Override
	public void OnGetTotalCountResponse(Object... results) {

	}

	@Override
	public void OnGetMoreDataResponse(Object... results) {

	}

	@Override
	public void OnGetManageTeacherResponse(Object... results) {

	}

	@Override
	public void OnDeleteApplicationResponse(Object... results) {

	}

	@Override
	public void OnGetSuperiorLevelResponse(Object... results) {

	}

	@Override
	public void OnGetTeachCourseResponse(Object... results) {

	}

	@Override
	public void OnGetTeachClassponse(Object... results) {

	}

	@Override
	public void OnGetTeachStudentResponse(Object... results) {

	}

	@Override
	public void OnUpdateStudentScoreResponse(Object... results) {

	}

	@Override
	public void OnUpdateStudentApplicationResponse(Object... results) {

	}

}