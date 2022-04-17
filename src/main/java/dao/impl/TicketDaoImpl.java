package dao.impl;

import dao.TicketDao;
import model.Ticket;
import model.impl.TicketImpl;
import repository.Repository;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class TicketDaoImpl implements TicketDao {

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Ticket createTicket(final long userId,
                               final long eventId,
                               final int place,
                               final Ticket.Category category) {
        long ticketId = getMaxId() + 1;
        return repository.getTickets()
                .put(ticketId, new TicketImpl(ticketId, eventId, userId, category, place));
    }

    @Override
    public Collection<Ticket> getAllTickets() {
        return repository.getTickets().values();
    }

    @Override
    public boolean deleteTicket(final long ticketId) {
        return Objects.nonNull(repository.getTickets()
                .remove(ticketId));
    }

    @Override
    public Ticket getTicketById(final long ticketId) {
        return getAllTickets().stream()
                .filter(ticket -> ticket.getId() == ticketId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long getMaxId() {
        return repository.getTickets().keySet().isEmpty()
                ? 0L
                : Collections.max(repository.getTickets().keySet());
    }
}
