package dao.impl;

import dao.EventDao;
import model.Event;
import repository.Repository;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


public class EventDaoImpl implements EventDao {

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Event> getAllEvents() {
        return repository.getEvents().values();
    }

    @Override
    public Event getEventById(final long eventId) {
        return repository.getEvents()
                .get(eventId);
    }

    @Override
    public Event addEvent(final Event event) {
        long id = getMaxId() + 1;
        event.setId(id);
        repository.getEvents()
                .put(event.getId(), event);
        return repository.getEvents().get(id);
    }

    @Override
    public Event updateEvent(final Event event) {
        return repository.getEvents()
                .computeIfPresent(event.getId(), (k, v) -> event);
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        return Objects.nonNull(repository.getEvents()
                .remove(eventId));
    }

    @Override
    public Long getMaxId() {
        return repository.getEvents().keySet().isEmpty()
                ? 0L
                : Collections.max(repository.getEvents().keySet());
    }
}
