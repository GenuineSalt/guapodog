package guapodog.exception;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;
import guapodog.error.ErrorResponse;
import guapodog.error.ErrorType;

@Singleton
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Requires(classes = {Exception.class, ExceptionHandler.class})  
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse> { 

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, Exception exception) {
        System.err.println(exception.getMessage()); // Log the exception

        ErrorResponse response;
        if (exception instanceof BadRequestException || exception instanceof ConstraintViolationException) {
            response = new ErrorResponse(ErrorType.BadRequest, exception.getMessage());
            return HttpResponse.badRequest(response);
        }
        else if (exception instanceof NotFoundException) {
            response = new ErrorResponse(ErrorType.NotFound, "No developer found");
            return HttpResponse.notFound(response);
        }
        else {
            response = new ErrorResponse(ErrorType.InternalServerError, "Something went wrong in the server");
            return HttpResponse.serverError(response);
        }
    }
}