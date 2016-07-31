package animeta.resource;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import animeta.dao.RecordDAO;
import animeta.dao.WorkDAO;
import animeta.dto.WorkDTO;
import animeta.model.Work;
import animeta.model.WorkIndex;
import animeta.security.AuthContext;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/works")
@Produces(MediaType.APPLICATION_JSON)
public class WorkResource {
    private final WorkDAO workDAO;
    private final RecordDAO recordDAO;

    @Inject public WorkResource(WorkDAO workDAO, RecordDAO recordDAO) {
        this.workDAO = workDAO;
        this.recordDAO = recordDAO;
    }

    @GET
    @Path("/_/{id}")
    @UnitOfWork
    public Response get(@PathParam("id") int id, @Context AuthContext authContext) {
        return buildResponse(workDAO.find(id), authContext);
    }

    @GET
    @Path("/{title}")
    @UnitOfWork
    public Response getByTitle(@PathParam("title") String title, @Context AuthContext authContext) {
        return buildResponse(workDAO.findByTitle(title), authContext);
    }

    private Response buildResponse(@Nullable Work work, AuthContext authContext) {
        if (work == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        WorkDTO dto = new WorkDTO(work);
        dto.setEpisodes(workDAO.findEpisodes(work));
        WorkIndex index = workDAO.findIndex(work);
        if (index != null) {
            dto.setRecordCount(index.getRecordCount());
            dto.setRank(index.getRank());
        } else {
            dto.setRecordCount(recordDAO.count(work));
        }
        if (authContext.isAuthenticated()) {
            dto.setRecord(recordDAO.find(authContext.getUser(), work));
        }
        return Response.ok(dto).build();
    }
}
