package com.example.auth.member.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class ApiResult<T> {
	
	private final boolean success;
	
	private final T data;
	
	private final ApiError error;
	
	private ApiResult(boolean success, T data, ApiError error) {
		this.success = success;
		this.data = data;
		this.error = error;
	}
	
	public static <T> ApiResult<T> OK(T response) {
		return new ApiResult<>(true, response, null);
	}
	
	public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(throwable, status));
	}
	
	public static ApiResult<?> ERROR(String errorMessage, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(errorMessage, status));
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public ApiError getError() {
		return error;
	}
	
	public T getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("success", success)
			.append("response", data)
			.append("error", error)
			.toString();
	}
	
}