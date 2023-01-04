package com.example.store.response;

import com.example.store.exception.ServiceException;
import lombok.Data;

@Data
public class ResponseJson<T> {
	private Integer state;//业务返回码
	private String message;//错误消息

	public enum State {
		OK(2000),
		ERR_FORBIDDEN(4030),
		ERR_BAO_REQUEST(4000),
		ERR_CONFLICT(4090),  //ERR_CONFLICT表示用户名冲突
		ERR_INTERNAL_ERROR(5000),//ERR_INTERNAL_ERROR表示代码里面报错
		ERR_NOT_ACCEPTABLE(50200);

		final Integer value;

		State(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	private T data;//返回的数据

	public static <T> ResponseJson<T> ok(T data) {
		ResponseJson<T> responseJson = new ResponseJson<>();
		responseJson.state = State.OK.getValue();
		responseJson.data = data;
		return responseJson;
	}

	public static <T> ResponseJson<T> fail(State state, String message) {
		ResponseJson<T> responseJson = new ResponseJson<>();
		responseJson.state = state.getValue();
		responseJson.message = message;
		return responseJson;
	}

	public static ResponseJson<Void> fail(ServiceException e) {
		return fail(e.getState(), e.getMessage());
	}
}
