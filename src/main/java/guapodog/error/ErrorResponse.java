package guapodog.error;

public class ErrorResponse {

    private ErrorType errorType;

    private String message;

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
