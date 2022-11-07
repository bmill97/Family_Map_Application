package DAO;

import model.Event;

import java.sql.*;

/**
     * Handles the data access of Events from the server
     * */
public class EventDAO {
        private final Connection conn;

        public EventDAO(Connection conn)
        {
            this.conn = conn;
        }

        /**
         * Creates an event in the database from the Event object passed in.
         * @param e An Event object created from a JSON string
         * @return  A string signifying a success or error when inserting the event.
         */
    public String CreateEvent(Event e){
        return "Returns error string";
    }

        /**
         * Inserts an update to the event object in the Server
         * @param event An event that needs to be updated in the server
         * @return A string signifying a success or error when inserting the event.
         */
    public String InsertEvent(Event event) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return "Returns error string";              //is this needed here?
    }

        /**
         * Deletes an event from the server
         * @param event The Event that needs to be deleted
         * @return  A string signifying a success or error when inserting the event.
         */
    public String DeleteEvent(Event event) throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        return "Returns error string";
    }

        /**
         * Obtains an event from the server
         * @param eventID the ID of the Event desired to be retrieved from the server
         * @return  a model version of that event from the server
         */
    public Event QueryEvent(String eventID) throws DataAccessException{
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
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
         * Obtains every event for the user and their family that is in the server
         * @param userID ID of the user who needs all of the events from their family tree
         * @return  An Event Array filled with all of the events in this family
         */
    public Event[] familyEvent(String userID){
        return null;
    }
}
