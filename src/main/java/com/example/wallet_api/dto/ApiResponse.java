package com.example.wallet_api.dto;

import java.util.Collection;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private Integer count;
    private T data;

    public ApiResponse() {}


    public ApiResponse(boolean success, String message, Integer count, T data) {
		super();
		this.success = success;
		this.message = message;
		this.count = count;
		this.data = data;
	}


	public static <T> ApiResponse<T> success(T data) {
    	 int count = 0;
         if (data instanceof Collection<?> collection) {
             count = collection.size();
         }
        return new ApiResponse<>(true, "success",count, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message,0, null);
    }

    // Getter - Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
