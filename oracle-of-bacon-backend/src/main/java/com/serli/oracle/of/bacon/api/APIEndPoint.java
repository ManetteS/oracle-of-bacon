package com.serli.oracle.of.bacon.api;

import com.serli.oracle.of.bacon.repository.ElasticSearchRepository;
import com.serli.oracle.of.bacon.repository.MongoDbRepository;
import com.serli.oracle.of.bacon.repository.Neo4JRepository;
import com.serli.oracle.of.bacon.repository.RedisRepository;
import net.codestory.http.annotations.Get;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import java.util.Optional;

public class APIEndPoint {
    private final Neo4JRepository neo4JRepository;
    private final ElasticSearchRepository elasticSearchRepository;
    private final RedisRepository redisRepository;
    private final MongoDbRepository mongoDbRepository;

    public APIEndPoint() {
        neo4JRepository = new Neo4JRepository();
        elasticSearchRepository = new ElasticSearchRepository();
        redisRepository = new RedisRepository();
        mongoDbRepository = new MongoDbRepository();
    }

    private String writeJson(List<Neo4JRepository.GraphItem> graph) {
        String json = "[\n";
        for (Neo4JRepository.GraphItem item : graph) {
            json += "{\n\"data\": " + item.toJson() + "\n},\n";
        }
        return json.substring(0, json.length()-2) + "\n]";
    }

    @Get("bacon-to?actor=:actorName")
    public String getConnectionsToKevinBacon(String actorName) {
        redisRepository.addLastResearch(actorName);
        return writeJson(neo4JRepository.getConnectionsToKevinBacon(actorName));
    }

    @Get("suggest?q=:searchQuery")
    public List<String> getActorSuggestion(String searchQuery) throws IOException {
        return elasticSearchRepository.getActorsSuggests(searchQuery);
    }

    @Get("last-searches")
    public List<String> last10Searches() {
        return redisRepository.getLastTenSearches();
    }

    @Get("actor?name=:actorName")
    public String getActorByName(String actorName) {
        Optional<Document> opt = mongoDbRepository.getActorByName(actorName);
        if (opt.isPresent()) {
            Document doc = opt.get();
            try {
                return doc.toJson();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return "";
    }
}
