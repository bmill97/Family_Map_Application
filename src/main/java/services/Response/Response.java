package services.Response;

import model.Event;
import model.Person;


/**
 * This class is used as a parent class to create objects for each of the responses made by the services
 */
public class Response {
    private boolean success;
    private String message;

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
}
