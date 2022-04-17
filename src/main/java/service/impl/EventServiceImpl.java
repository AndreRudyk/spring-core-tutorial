package service.impl;

import dao.EventDao;
import model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.EventService;
import validator.ValidatorService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ValidatorService validatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Override
    public Event createEvent(final Event event) {
        LOGGER.info("createEvent method is called with event {}", event);
        return eventDao.addEvent(event);
    }

    @Override
    public Event getEventById(final long eventId) {
        LOGGER.info("getEventById method is called with eventId {}", eventId);
        return eventDao.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(final String title,
                                        final int pageSize,
                                        final int pageNum) {
        LOGGER.info("getEventByTitle method has been called");
        return eventDao.getAllEvents().stream()
                .filter(event -> event.getTitle().contains(title))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(final Date day,
                                       final int pageSize,
                                       final int pageNum) {
        LOGGER.info("getEventsForDay method has been called");
        return eventDao.getAllEvents().stream()
                .filter(event -> isDateEqual(event.getDate(), day))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public Event updateEvent(final Event event) {
        LOGGER.info("updateEvent method is being called with event {}", event);
        validatorService.validateEvent(event.getId());
        return eventDao.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        LOGGER.info("deleteEvent is being called with id {}", eventId);
        validatorService.validateEvent(eventId);
        return eventDao.deleteEvent(eventId);
    }

    private boolean isDateEqual(final Date date1, final Date date2) {
        return  date1.equals(date2);
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void setValidatorService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }
}
