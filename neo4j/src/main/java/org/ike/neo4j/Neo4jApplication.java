package org.ike.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @Author ike
 */
@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "org.ike.neo4j.repository")
@EntityScan(basePackages = "org.ike.neo4j.model")
public class Neo4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }
}
