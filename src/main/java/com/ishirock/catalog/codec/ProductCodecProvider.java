package com.ishirock.catalog.codec;

import com.ishirock.catalog.Product;
import com.ishirock.catalog.codec.ProductCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class ProductCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, final CodecRegistry registry) {
        if (clazz == Product.class) {
            return (Codec<T>) new ProductCodec();
        }
        return null;
    }

}