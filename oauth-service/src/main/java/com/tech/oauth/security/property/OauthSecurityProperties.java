package com.tech.oauth.security.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author YK
 */
@PropertySource(value = {"classpath:base-config.properties"})
@Component
@ConfigurationProperties(prefix = "base.security")
public class OauthSecurityProperties {

	private Boolean loginNeedCaptcha = false;

    private String ldapUrl;

    private String whiteUserFile = "classpath:user-list";
    private Integer maxLoginAttempt = 5;

    private Integer lockHours = 2;

    private String systemUrl;
    
    private String logoutUrl;

	public Boolean getLoginNeedCaptcha() {
		return loginNeedCaptcha;
	}

	public void setLoginNeedCaptcha(Boolean loginNeedCaptcha) {
		this.loginNeedCaptcha = loginNeedCaptcha;
	}

	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getWhiteUserFile() {
		return whiteUserFile;
	}

	public void setWhiteUserFile(String whiteUserFile) {
		this.whiteUserFile = whiteUserFile;
	}

	public Integer getMaxLoginAttempt() {
		return maxLoginAttempt;
	}

	public void setMaxLoginAttempt(Integer maxLoginAttempt) {
		this.maxLoginAttempt = maxLoginAttempt;
	}

	public Integer getLockHours() {
		return lockHours;
	}

	public void setLockHours(Integer lockHours) {
		this.lockHours = lockHours;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	@Override
	public String toString() {
		return "SysSecurityProperties [loginNeedCaptcha=" + loginNeedCaptcha + ", ldapurl=" + ldapUrl
				+ ", whiteUserFile=" + whiteUserFile + ", maxLoginAttempt=" + maxLoginAttempt + ", lockHours="
				+ lockHours + ", systemUrl=" + systemUrl + ", logoutUrl=" + logoutUrl + "]";
	}
    
    
    
}
