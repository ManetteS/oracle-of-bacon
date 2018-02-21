package com.serli.oracle.of.bacon.repository;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElasticSearchRepository {

    private final RestHighLevelClient client;

    public ElasticSearchRepository() {
        client = createClient();

    }

    public static RestHighLevelClient createClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );
    }

    public List<String> getActorsSuggests(String searchQuery) throws IOException {
        SearchRequest sreq = new SearchRequest("actors"); 
        sreq.types("actor");

        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.query(QueryBuilders.matchQuery("name", searchQuery));

        sreq.source(ssb);

        SearchResponse sres = client.search(sreq);
        SearchHits hits = sres.getHits();
        List<String> actors = new ArrayList<String>();
        for (SearchHit h : hits) {
           String nomDuHit = h.getSourceAsMap().get("name").toString();
           actors.add(nomDuHit);
        }
        
        return actors;
    }
}
