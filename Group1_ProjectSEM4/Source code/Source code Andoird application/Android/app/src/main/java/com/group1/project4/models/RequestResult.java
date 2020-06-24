package com.group1.project4.models;

public class RequestResult {
    private int statusCode;
    private String content;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RequestResult(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }
}
