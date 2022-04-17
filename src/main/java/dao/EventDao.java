package dao;


import model.Event;
import java.util.Collection;

public interface EventDao {

    Collection<Event> getAllEvents();

    Event getEventById(long eventId);

    Event addEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);

    public Long getMaxId();
}
