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
public class InternalServerErrorExceptionHandler implements ExceptionHandler<InternalServerErrorException, HttpResponse> { 

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, InternalServerErrorException exception) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.InternalServerError, "Something went wrong in the server");
        return HttpResponse.serverError(errorResponse); 
    }
}