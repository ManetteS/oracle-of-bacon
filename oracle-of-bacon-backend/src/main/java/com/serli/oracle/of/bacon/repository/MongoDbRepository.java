package com.serli.oracle.of.bacon.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

/*
 * Dans un premier Shell, dans le bin de mongodb :
 *   mongod --dbpath="<...>\oracle-of-bacon\imdb-data"
 * Dans un second Shell, toujours dans le bin :
 *   ./mongoimport -d actors -c actors --type csv --file "<...>\oracle-of-bacon\imdb-data\actors.csv" --headerline
 *   mongo
 * Dans le Shell mongo qui vient de s'ouvrir :
 *   use actors
 * Pour tester :
 *   db.actors.find({"name:ID" : "Bacon, Kevin (I)"})
 *     -> on recupere bien Kevin Bacon
 */
public class MongoDbRepository {
    private final String HOST = "localhost";
    private final String WORKSHOP = "workshop";
    private final String ACTORS = "actors";
    private final String NAME = "name:ID";
    private final MongoCollection<Document> actorCollection;

    public MongoDbRepository() {
        this.actorCollection = new MongoClient(HOST, 27017).getDatabase(WORKSHOP).getCollection(ACTORS);
    }

    public Optional<Document> getActorByName(String name) {
        return Optional.ofNullable(collection.find(eq(NAME, name)).first());
    }
}
