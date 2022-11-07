package services.Request;

import model.Event;
import model.Person;
import model.User;

/**
 * This class is used to create a loading requests for the server
 */

public class LoadRequest{
    User[] users = null;
    Person[] persons = null;
    Event[] events = null;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
