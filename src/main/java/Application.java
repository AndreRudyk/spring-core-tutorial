import facade.BookingFacade;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String [] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml"
        );

        BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);

        System.out.println(bookingFacade.getUserById(1));

        bookingFacade.deleteUser(1);

        System.out.println(bookingFacade.getUserById(1));
    }
}
