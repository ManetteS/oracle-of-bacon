package com.serli.oracle.of.bacon.repository;


import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.util.List;

/*
 * Command line to import the database :
 * bin/neo4j-admin import
 *  --nodes:Actor ../oracle-of-bacon/imdb-data/actors.csv
 *  --nodes:Movie ../oracle-of-bacon/imdb-data/movies.csv
 *  --relationships ../oracle-of-bacon/imdb-data/roles.csv
 *  --ignore-missing-nodes=true
 *  --ignore-duplicate-nodes=true
 */
public class Neo4JRepository {
    private final Driver driver;

    public Neo4JRepository() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    public List<?> getConnectionsToKevinBacon(String actorName) {
        Session session = driver.session();

        // TODO implement Oracle of Bacon
        // Oracle request : MATCH (kevin {name:"Bacon, Kevin (I)"}) MATCH (actor {name:<actor name>}) MATCH path = shortestPath( (kevin)-[:PLAYED_IN*]-(actor) ) RETURN path
        return null;
    }

    public static abstract class GraphItem {
        public final long id;

        private GraphItem(long id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GraphItem graphItem = (GraphItem) o;

            return id == graphItem.id;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }
    }

    private static class GraphNode extends GraphItem {
        public final String type;
        public final String value;

        public GraphNode(long id, String value, String type) {
            super(id);
            this.value = value;
            this.type = type;
        }
    }

    private static class GraphEdge extends GraphItem {
        public final long source;
        public final long target;
        public final String value;

        public GraphEdge(long id, long source, long target, String value) {
            super(id);
            this.source = source;
            this.target = target;
            this.value = value;
        }
    }
}
