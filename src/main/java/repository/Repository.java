package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import model.Event;
import model.Ticket;
import model.User;
import model.impl.EventImpl;
import model.impl.UserImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Repository implements BeanPostProcessor {

    private String usersFilePath;

    private String eventFilePath;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<Long, Event> events;
    private Map<Long, Ticket> tickets;
    private Map<Long, User> users;

    public Map<Long, Event> getEvents() {
        return events;
    }

    public Map<Long, Ticket> getTickets() {
        return tickets;
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public String getUsersFilePath() {
        return usersFilePath;
    }

    public void setUsersFilePath(String usersFilePath) {
        this.usersFilePath = usersFilePath;
    }

    public String getEventFilePath() {
        return eventFilePath;
    }

    public void setEventFilePath(String eventFilePath) {
        this.eventFilePath = eventFilePath;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            List<UserImpl> userList = objectMapper.readValue(getJsonString(usersFilePath), new TypeReference<List<UserImpl>>() {
            });
            List<EventImpl> eventList  = objectMapper.readValue(getJsonString(eventFilePath), new TypeReference<List<EventImpl>>() {
            });
            users = userList.stream().collect(Collectors.toMap(UserImpl::getId, Function.identity()));
            events = eventList.stream().collect(Collectors.toMap(EventImpl::getId, Function.identity()));
            tickets = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private String getJsonString(final String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
