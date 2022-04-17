package service.impl;

import dao.TicketDao;
import model.Event;
import model.Ticket;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.TicketService;
import validator.ValidatorService;
import java.util.List;
import java.util.stream.Collectors;


public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private ValidatorService validatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public Ticket bookTicket(final long userId,
                             final long eventId,
                             final int place,
                             final Ticket.Category category) {
        LOGGER.info("bookTicket method has been called");
        validatorService.validatePlace(place);
        validatorService.validateUser(userId);
        validatorService.validateEvent(eventId);
        return ticketDao.createTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(final User user,
                                         final int pageSize,
                                         final int pageNum) {
        LOGGER.info("getBookedTickets method has been called");
        return ticketDao.getAllTickets().stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event,
                                         final int pageSize,
                                         final int pageNum) {
        LOGGER.info("getBookedTickets method has been called");
        return ticketDao.getAllTickets().stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(final long ticketId) {
        LOGGER.info("cancelTicket method has been called with ticket id {}", ticketId);
        validatorService.validateTicket(ticketId);
        return ticketDao.deleteTicket(ticketId);
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void setValidatorService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }
}
