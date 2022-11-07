package DAO;

import model.Person;
import model.User;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.*;

/**
 * Handles the data access of Persons from the server
 * */
public class PersonDAO {

    private final Connection conn;

    public PersonDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Creates a person in the database from the person object passed in.
     * @param p A Person object created from a JSON string
     * @return  A string signifying a success or error when inserting the person.
     */
    public String CreatePerson(Person p){ return "Returns error string"; }

    /**
     * Inserts an update to the person object in the Server
     * @param p A person that needs to be updated in the server
     * @return A string signifying a success or error when inserting the person.
     */
    public String InsertPerson(Person p) throws DataAccessException{    //update
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Users (PersonID, AssociatedUserName, firstName, lastName, " +
                "gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, p.getPersonID());
            stmt.setString(2, p.getAssociatedUserName());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFatherID());
            stmt.setString(7, p.getMotherID());
            stmt.setString(8, p.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return "Returns error string";                          //should this be here?
    }

    /**
     * Deletes a person from the server
     * @param p The person that needs to be deleted
     * @return  A string signifying a success or error when inserting the person.
     */
    public String DeletePerson(Person p) throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        return "Returns error string";
    }

    /**
     * Obtains an person from the server
     * @param personID The ID of the user desired to be retrieved from the server
     * @return  a model version of that person from the server
     */
    public Person QueryPerson(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("Person ID"), rs.getString("Associated Username"),
                        rs.getString("First Name"), rs.getString("Last Name"), rs.getString("Gender"),
                        rs.getString("Father ID"), rs.getString("Mother ID"), rs.getString("Spouse ID"));
                return person;
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

    /**
     * Obtains every family member for the user and their family that is in the server
     * @param userName ID of the user who needs all of the people from their family tree
     * @return  A Person Array filled with all of the people in this family
     */
    public Person[] familyPerson(String userName){
        return null;
    }
}
