package com.ishirock.catalog.codec;

import com.mongodb.MongoClientSettings;
import com.ishirock.catalog.Product;
import org.bson.Document;
import org.bson.BsonWriter;
import org.bson.BsonValue;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class ProductCodec implements CollectibleCodec<Product> {

    private final Codec<Document> documentCodec;

    public ProductCodec() {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public void encode(BsonWriter writer, Product product, EncoderContext encoderContext) {
        Document doc = new Document();
        doc.put("id", product.getId());
        doc.put("name", product.getName());
        doc.put("desc", product.getDesc());
        doc.put("price", product.getPrice());
        doc.put("imageUrl", product.getImageUrl());
        documentCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Product> getEncoderClass() {
        return Product.class;
    }

    @Override
    public Product generateIdIfAbsentFromDocument(Product document) {
        if (!documentHasId(document)) {
            document.setId(UUID.randomUUID().toString());
        }
        return document;
    }

    @Override
    public boolean documentHasId(Product document) {
        return document.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Product document) {
        return new BsonString(document.getId());
    }

    @Override
    public Product decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        Product product = new Product();
        if (document.getString("id") != null) {
            product.setId(document.getString("id"));
        }        
        product.setName(document.getString("name"));
        product.setDesc(document.getString("desc"));
        product.setPrice(document.getDouble("price"));
        product.setImageUrl(document.getString("imageUrl"));
        return product;
    }
}