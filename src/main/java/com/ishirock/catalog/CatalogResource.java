package com.ishirock.catalog;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

import javax.inject.Inject;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogResource {

    @Inject ProductService productService;

    @GET
    public Uni<List<Product>> list() {
        return productService.list();
    }

    @GET
    @Path("{id}")
    public Multi<Product> findProduct(@PathParam("id") String id) {
        return productService.getProduct(id);
    }

    @POST
    public Uni<List<Product>>  add(Product product) {
        return productService.add(product).
            onItem().ignore().andSwitchTo(this::list);

    }
}