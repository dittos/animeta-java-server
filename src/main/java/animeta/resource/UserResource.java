package animeta.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import animeta.security.AuthContext;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    public UserResource() {}

    @GET
    @Path("/me")
    @UnitOfWork
    public Response getCurrentUser(@Context AuthContext authContext) {
        if (!authContext.isAuthenticated()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(authContext.getUser()).build();
    }
}
