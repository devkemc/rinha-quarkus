package br.com.devkemc.infrastructure.web.config;

import br.com.devkemc.domain.exception.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundMapper implements ExceptionMapper<CustomerNotFoundException> {

    @Override
    public Response toResponse(CustomerNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
