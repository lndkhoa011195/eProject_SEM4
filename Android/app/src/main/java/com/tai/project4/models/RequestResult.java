package com.tai.project4.models;

public class RequestResult {
    private int errorCode;
    private String content;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RequestResult(int errorCode, String content) {
        this.errorCode = errorCode;
        this.content = content;
    }
}
