package mx.com.anzen.urban.beans;

public class RegisterForm {
    private String appId;
    private String userId;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAppId() {
	return appId;
    }
    public void setAppId(String appId) {
	this.appId = appId;
    }
}
