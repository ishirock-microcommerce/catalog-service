package com.ishirock.catalog;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.List;

import javax.inject.Inject;

import java.util.concurrent.CompletionStage;


@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogResource {

    @Inject ProductService productService;

    @GET
    public CompletionStage<List<Product>> list() {
        return productService.list();
    }

    @POST
    public CompletionStage<List<Product>>  add(Product product) {
        productService.add(product);
        return list();
    }
}