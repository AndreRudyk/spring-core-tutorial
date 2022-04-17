package exception;

public class EventNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Event is not found";

    public EventNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
