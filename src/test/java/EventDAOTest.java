import DAO.DataAccessException;                     //
import DAO.Database;                                //
import DAO.EventDAO;                                //
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;            //
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event bestEvent;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Event compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.InsertEvent(bestEvent);
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        Assertions.assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        Assertions.assertEquals(bestEvent, compareTest);

    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //if we call the method the first time it will insert it successfully
            eDao.InsertEvent(bestEvent);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            eDao.InsertEvent(bestEvent);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        Assertions.assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our event is not in the database
        //Set our compareTest to an actual event
        Event compareTest = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        Assertions.assertNull(compareTest);
    }

    @Test
    public void queryPass() throws Exception {
        Event compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.InsertEvent(bestEvent);
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        Assertions.assertNotNull(compareTest);

        Assertions.assertEquals(bestEvent, compareTest);                //confirms that the query brought back the right event
    }

    @Test
    public void queryFail() throws Exception {

        boolean didItWork = true;
        Event compareTest = bestEvent;

        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //We are calling for a query of an event that hasn't been added to the db yet. Should change it to a null object
            compareTest = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Confirms that it couldn't pull an event that doesn't exist.
        Assertions.assertNull(compareTest);


        //Going to compare the event with a different event.
        //Initialize the compare test to be equal with bestEvent
        compareTest = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //Checks to see if it will pull an event that doesn't exist. It should be populated with the "bestEvent"
            //And fail to pull something. Therefore, causing it to become Null;
            //Random eventID 33
            compareTest = eDao.QueryEvent("33");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        Assertions.assertNull(compareTest);
        Assertions.assertNotEquals(compareTest, bestEvent);
    }

    @Test
    public void clear() throws Exception{

        Event compareTest = bestEvent;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            //Inserts an event into the db to see if it will be cleared
            eDao.InsertEvent(bestEvent);
            db.clearTables();       //clears the tables
            compareTest = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //The compare test should become null when trying to pull an event from the db
        //That doesn't exist.
        Assertions.assertNull(compareTest);

        Assertions.assertNotEquals(bestEvent, compareTest);

    }
}
