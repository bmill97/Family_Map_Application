package services;

import services.Request.LoadRequest;
import services.Response.Response;

/**
 * This class is used to help with the unit testing of the database
 */

public class Load {

    /**
     * Used to facilitate testing, this will clear all information stored in the database to begin.
     * After clearing, It will take the information from users and create every user
     * included in the array. It will do the same for persons and events.
     * @param r A LoadRequest object with user, person and event arrays filled with appropriate objects.
     * @return A Response object of either success or failure
     */
    private Response load(LoadRequest r){
        return null;
    }
}
