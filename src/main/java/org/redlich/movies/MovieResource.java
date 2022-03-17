package org.redlich.movies;

import org.apache.commons.collections.CollectionUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.*;

@Path("/movies")
@RequestScoped
public class MovieResource {
    private static final Logger LOGGER = Logger.getLogger(MovieResource.class.getName());

    private final MovieRepository movies;
    private final Validator validator;

    @Context
    ResourceContext resourceContext;

    @Context
    UriInfo uriInfo;

    @Inject
    public MovieResource(MovieRepository movies) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.movies = movies;
        }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() {
        return ok(this.movies.all()).build();
        }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePost(Movie movie) {
        Set<ConstraintViolation<Movie>> validate = validator.validate(movie);
        if (CollectionUtils.isNotEmpty(validate)){

        }
        Movie saved = this.movies.save(Movie.of(movie.getTitle(),movie.getYear()));
        return created(
                uriInfo.getBaseUriBuilder()
                        .path("/movies/{id}")
                        .build(saved.getId())
        ).build();
        }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") final int id) {
        Movie movie = this.movies.getById(id);
        if (movie == null) {
            throw new MovieNotFoundException(id);
            }
        return ok(movie).build();
        }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") final int id, @Valid Movie movie) {
        Movie existed = this.movies.getById(id);
        existed.setTitle(movie.getTitle());
        existed.setYear(movie.getYear());

        this.movies.save(existed);
        return noContent().build();
        }

    @Path("{id}")
    @DELETE
    public Response deleteMovie(@PathParam("id") final int id) {
        this.movies.deleteById(id);
        return noContent().build();
        }
    }
