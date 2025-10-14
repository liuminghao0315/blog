package com.lmh.blog_project.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Result<D> implements Serializable {

    private static final long serialVersionUID = -2749274033748301886L;
    
    // 执行成功或失败
    @JsonProperty("isSuccess")
    private Boolean success = false;
    
    //用于出错时的简短描述的数字码
    private String code;
    
    //用于出错时的详细描述错误
    private String message;
    
    //具体数据
    private D data;

    public Result() {
    }

    public Result(Boolean success, String code, String message, D data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Boolean success, D data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
