package com.ntg.JobHunter.domain;

public class RestResponse<T> {
    private int statusCode;
    private String error;
    private Object message;
    // Ví dụ về message
    /*
      "message": {
         "email": "Email không hợp lệ",
         "password": "Mật khẩu quá ngắn"
      }
    */
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
