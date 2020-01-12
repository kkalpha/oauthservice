package com.tech.oauth.resource.model;

public class SimpleUser {
    private String userId;
    private String status;
    
    
    public SimpleUser() {
    }
    public SimpleUser(String userId) {
        this.userId = userId;
        this.status = "active";
    }
    public SimpleUser(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static SimpleUser unKnonwnUser() {
        return new SimpleUser("unknown","error");
    }
}
