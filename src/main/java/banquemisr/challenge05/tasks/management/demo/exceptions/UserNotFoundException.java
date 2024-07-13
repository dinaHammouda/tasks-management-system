package banquemisr.challenge05.tasks.management.demo.exceptions;




public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
