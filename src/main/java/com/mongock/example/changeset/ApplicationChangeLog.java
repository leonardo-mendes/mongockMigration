package com.mongock.example.changeset;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongock.example.domain.Product;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@ChangeLog
public class ApplicationChangeLog {

    private static final String COLLECTION_NAME = "product";

    @ChangeSet(id = "mailApplicationParameters", order = "001", author = "Mongock")
    public void changeSet1(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(Product.builder()
                .name("test")
                .build()));
    }

    private Document createMongoDocument(Product product) {
        return new Document()
                .append("name", product.getName());
    }
}
