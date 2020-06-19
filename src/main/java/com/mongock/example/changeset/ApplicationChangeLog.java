package com.mongock.example.changeset;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongock.example.domain.Product;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.model.Updates;
import lombok.extern.slf4j.Slf4j;
import org.bson.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ChangeLog
@Slf4j
public class ApplicationChangeLog {

    private static final String PRODUCT_COLLECTION = "product";
    private static final String NIKE_BRAND = "nike";
    private static final String ADDIDAS_BRAND = "addidas";

    @ChangeSet(id = "createAProduct", order = "001", author = "Mongock")
    public void changeSet1(MongoDatabase mongoDatabase) {
        log.info("Loaded changeSet 001, createAProduct.");
        mongoDatabase.getCollection(PRODUCT_COLLECTION).insertMany(List.of(
                createMongoDocument("Nike SB"),
                createMongoDocument("Nike Shocks"),
                createMongoDocument("Addidas Easi")
        ));
    }

    private Document createMongoDocument(String name) {
        log.info("Created document with name {}.", name);
        return new Document()
                .append("name", name);
    }

    @ChangeSet(id = "updateAllProductsWithBrand", order = "002", author = "Mongock")
    public void changeSet2(MongoDatabase mongoDatabase) {
        log.info("Loaded changeSet 002, updateAllProductsWithBrand.");
        // https://api.mongodb.com/java/3.0/?com/mongodb/client/model/Filters.html Documentation about Filters
        mongoDatabase.getCollection(PRODUCT_COLLECTION).updateMany(Filters.exists("name"), Updates.addToSet("brand", NIKE_BRAND));
    }

    @ChangeSet(id = "updateAllProductsThatDoNotBelongToNike", order = "004", author = "Mongock")
    public void changeSet3(MongoDatabase mongoDatabase) {
        log.info("Loaded changeSet 003, updateAllProductsThatDoNotBelongToNike.");
        mongoDatabase.getCollection(PRODUCT_COLLECTION)
                .updateMany(Filters.in("name","Addidas Easi"),
                        Updates.set("brand", ADDIDAS_BRAND));
    }
}
