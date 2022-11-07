import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import DAO.UserDAO;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDAOTest {
    private Database db;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new user with random data
        bestUser = new User("cougs3", "goBYU123", "jimmertime32@gmail.com", "James",
                "Smith", "m", "abc123");
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
        //First lets create a User that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        User compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            uDao.InsertUser(bestUser);
            //So lets use a find method to get the event that we just put in back out
            compareTest = uDao.QueryUser(bestUser.getUsername());
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
        Assertions.assertEquals(bestUser, compareTest);

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
            UserDAO uDao = new UserDAO(conn);
            //if we call the method the first time it will insert it successfully
            uDao.InsertUser(bestUser);
            //but our sql table is set up so that "personID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            uDao.InsertUser(bestUser);
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
        User compareTest = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = uDao.QueryUser(bestUser.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        Assertions.assertNull(compareTest);
    }

    @Test
    public void queryPass() throws Exception {
        User compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            uDao.InsertUser(bestUser);
            //So lets use a find method to get the event that we just put in back out
            compareTest = uDao.QueryUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        Assertions.assertNotNull(compareTest);

        Assertions.assertEquals(bestUser, compareTest);                //confirms that the query brought back the right event
    }

    @Test
    public void queryFail() throws Exception {
        boolean didItWork = true;
        User compareTest = bestUser;

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //We are calling for a query of a user that hasn't been added to the db yet. Should change it to a null object
            compareTest = uDao.QueryUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Confirms that it couldn't pull the user that doesn't exist.
        Assertions.assertNull(compareTest);


        //Going to compare the event with a different event.
        //Initialize the compare test to be equal with bestEvent
        compareTest = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //Checks to see if it will pull an event that doesn't exist. It should be populated with the "bestUser"
            //And fail to pull something. Therefore, causing it to become Null;
            //Random userName "doggo"
            compareTest = uDao.QueryUser("doggo");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        Assertions.assertNull(compareTest);
        Assertions.assertNotEquals(compareTest, bestUser);
    }

    @Test
    public void clear() throws Exception{

        User compareTest = bestUser;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            //Inserts a user into the db to see if it will be cleared
            uDao.InsertUser(bestUser);
            db.clearTables();       //clears the tables
            compareTest = uDao.QueryUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //The compare test should become null when trying to pull an event from the db
        //That doesn't exist.
        Assertions.assertNull(compareTest);

        Assertions.assertNotEquals(bestUser, compareTest);

    }
}
