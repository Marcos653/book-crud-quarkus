package application.resource.contract;

import application.dto.BookRequest;
import application.dto.BookResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/books")
@Tag(name = "Book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IBookResource {

    @GET
    List<BookResponse> getAll();

    @GET
    @Path("/{id}")
    BookResponse getById(@PathParam("id") Long id);

    @POST
    void create(BookRequest bookRequest);

    @PUT
    @Path("/{id}")
    BookResponse update(@PathParam("id") Long id, BookRequest bookRequest);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}
