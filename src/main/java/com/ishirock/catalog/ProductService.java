package com.ishirock.catalog;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ProductService{

    @Inject ReactiveMongoClient mongoClient;

    public Uni<List<Product>> list(){
        return getCollection().find().map(
            doc -> {
            Product product = new Product();
            product = (Product) doc;
            return product;
        }).collectItems().asList();

    }

    public Multi<Product> getProduct(String id){
        Bson filter = Filters.and(Filters.eq("id", id));
        return getCollection().find(filter);
    }

    public Uni<Void> add(Product product){
        return getCollection().insertOne(product).onItem().ignore().andContinueWithNull();
    }


    private ReactiveMongoCollection<Product> getCollection(){
        return mongoClient.getDatabase("store").getCollection("products",Product.class);
    }
}