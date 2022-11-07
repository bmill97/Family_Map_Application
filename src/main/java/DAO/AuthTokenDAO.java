package DAO;

import model.AuthToken;
import model.User;

/**
 * Handles the data access of AuthTokens from the server
 * */
public class AuthTokenDAO {
    /**
     * Creates an AuthToken in the database from the AuthToken object passed in.
     * @param t A AuthToken object created from a JSON string
     * @return  A string signifying a success or error when inserting the AuthToken.
     */
    private String createToken(AuthToken t){
        return null;
    }

    /**
     * Inserts an update to the AuthToken object in the Server
     * @param t The AuthToken that needs to be updated in the server
     * @return A string signifying a success or error when inserting the AuthToken.
     */
    private String insertToken(AuthToken t){
        return null;
    }

    /**
     * Deletes the AuthToken from the server
     * @param t The AuthToken that needs to be deleted
     * @return  A string signifying a success or error when inserting the AuthToken.
     */
    private String deleteToken(AuthToken t){
        return null;
    }

    /**
     * Checks the user and the associated AuthToken
     * @param u the user that is being checked
     * @return A string signifying if the user and AuthToken are associated.
     */
    private String VerifyToken(User u){
        //Should get a user and make a AuthToken accordingly
        return null;
    }
}
