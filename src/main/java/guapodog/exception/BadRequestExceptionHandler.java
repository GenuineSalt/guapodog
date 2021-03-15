package guapodog.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

import guapodog.error.ErrorResponse;
import guapodog.error.ErrorType;

@Produces
@Singleton 
@Requires(classes = {NotFoundException.class, ExceptionHandler.class})  
public class BadRequestExceptionHandler implements ExceptionHandler<BadRequestException, HttpResponse> { 

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, BadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BadRequest, exception.getMessage());
        return HttpResponse.badRequest(errorResponse); 
    }
}