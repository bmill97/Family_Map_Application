package services;

import services.Request.RegisterRequest;
import services.Response.Response;

/**
 * This class is used to be the Register service for the server.
 */

public class Register {

    private Response Register(RegisterRequest r){

        /**
         * Creates a new user account, generagtes 4 generations of ancestor data for the new user,
         * logs the user in, and returns an auth token.
         * @param r A RegisterRequest POJO used to create a user to register in the database.
         *          This function will have to use the Fill and Login
         *          services to help set up the new user.
         * @return A Response object of either success or error
         */
        return null;
    }
}
