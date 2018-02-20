package com.serli.oracle.of.bacon.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbRepository {
    private final String HOST = "localhost";
    private final String WORKSHOP = "workshop";
    private final String ACTORS = "actors";
    private final String NAME = "name";
    private final MongoCollection<Document> actorCollection;

    public MongoDbRepository() {
        this.actorCollection = new MongoClient(HOST, 27017).getDatabase(WORKSHOP).getCollection(ACTORS);
    }

    public Optional<Document> getActorByName(String name) {
        return Optional.ofNullable(collection.find(eq(NAME, name)).first());
    }
}
