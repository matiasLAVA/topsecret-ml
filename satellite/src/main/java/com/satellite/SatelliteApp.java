package com.satellite;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class SatelliteApp {

    @Autowired
    private Environment env;

    @Bean
    public MongoDatabase dataSource() {
        log.info("Creating a Mongo client");
        MongoClient mongo = new MongoClient(new MongoClientURI(env.getProperty("mongodb.host")));
        log.info("Accessing the database");
        MongoDatabase database = mongo.getDatabase(env.getProperty("mongodb.database"));
        log.info("Connected to the database successfully");
        return database;
    }

    public static void main(String[] args) {
        SpringApplication.run(SatelliteApp.class, args);
    }
}
