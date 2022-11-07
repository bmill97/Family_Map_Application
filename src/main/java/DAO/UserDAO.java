package DAO;

import model.User;

import java.sql.*;

/**
 * Handles the data access of Users from the server
 * */
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }





    /**
     * Creates a user in the database from the User object passed in.
     * Should also create 4 generations of ancestor data for the new user
     * @param u A user object created from a JSON string
     * @return  A string signifying a success or error when inserting the user.
     */
    public String CreateUser(User u){ return "Returns error string"; }



    /**
     * Inserts an update to the user object in the Server
     * @param u A user that needs to be updated in the server
     * @return A string signifying a success or error when inserting the user.
     */
    public String InsertUser(User u) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Users (Username, Password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return "Returns error string";                  //should this be here?
    }

    /**
     * Deletes a user from the server
     * @param u The user that needs to be deleted
     * @return  A string signifying a success or error when inserting the user.
     */
    public String DeleteUser(User u) throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        return "Returns error string";                                  //DO i need this here?
    }


    /**
     * Obtains the user from the server
     * @param userName The ID of the user desired to be retrieved from the server
     * @return  a model version of that user from the server
     */
    public User QueryUser(String userName) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("UserName"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("First Name"), rs.getString("Last Name"),
                        rs.getString("Gender"), rs.getString("Person ID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
