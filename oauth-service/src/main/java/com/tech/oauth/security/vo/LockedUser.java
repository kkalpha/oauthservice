 package com.tech.oauth.security.vo;

 public class LockedUser {
    private String userId;
    private int failedTimes;

    public LockedUser(String userId, int failedTimes) {
        super();
        this.userId = userId;
        this.failedTimes = failedTimes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(int failedTimes) {
        this.failedTimes = failedTimes;
    }

}
