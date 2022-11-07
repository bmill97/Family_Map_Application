package services.Response;

/**
 * This class is used to form a response object to a LoginRequest
 */

public class LoginResponse extends Response{
    private String authToken;
    private String userName;
    private String personID;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
