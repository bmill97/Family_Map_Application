package services.Response;

import model.Event;

/**
 * This class is used to create a response with appropriate events for the request
 */

public class EventsResponse extends Response{
    private Event[] dataEvent;

    public Event[] getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(Event[] dataEvent) {
        this.dataEvent = dataEvent;
    }
}
