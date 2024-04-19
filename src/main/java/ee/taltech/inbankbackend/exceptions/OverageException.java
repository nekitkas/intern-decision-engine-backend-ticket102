package ee.taltech.inbankbackend.exceptions;

public class OverageException extends Throwable{
    private final String message;
    private final Throwable cause;

    public OverageException(String message){
        this(message,null);
    }

    public OverageException(String message, Throwable cause){
        this.message = message;
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
