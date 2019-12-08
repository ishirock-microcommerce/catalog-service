package com.ishirock.catalog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import io.quarkus.mongodb.ReactiveMongoClient;
import io.quarkus.mongodb.ReactiveMongoCollection;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class ProductService{

    @Inject ReactiveMongoClient mongoClient;

    public CompletionStage<List<Product>> list(){
        return getCollection().find().map(
            doc -> {
            Product product = new Product();
            product = (Product) doc;
            return product;
        }).toList().run();
    }

    public CompletionStage<Void> add(Product product){
        return getCollection().insertOne(product);
    }


    private ReactiveMongoCollection<Product> getCollection(){
        return mongoClient.getDatabase("store").getCollection("products",Product.class);
    }
}