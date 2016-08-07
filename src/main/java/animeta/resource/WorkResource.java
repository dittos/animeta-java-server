package animeta.resource;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import animeta.dao.WorkDAO;
import animeta.dto.WorkDTO;
import animeta.dto.WorkFields;
import animeta.model.Work;
import animeta.security.AuthContext;
import animeta.serializer.WorkSerializer;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/works")
@Produces(MediaType.APPLICATION_JSON)
public class WorkResource {
    private final WorkDAO dao;
    private final WorkSerializer serializer;

    @Inject public WorkResource(WorkDAO dao, WorkSerializer serializer) {
        this.dao = dao;
        this.serializer = serializer;
    }

    @GET
    @UnitOfWork
    public WorkDTO getByTitle(
            @QueryParam("title") String title,
            @QueryParam("fields") @DefaultValue("{}") WorkFields fields,
            @Context AuthContext authContext
    ) {
        Work work = dao.getByTitle(title);
        if (work == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return serializer.serialize(work, fields, authContext);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public WorkDTO getById(
            @PathParam("id") int id,
            @QueryParam("fields") @DefaultValue("{}") WorkFields fields,
            @Context AuthContext authContext
    ) {
        Work work = dao.get(id);
        if (work == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return serializer.serialize(work, fields, authContext);
    }
}
