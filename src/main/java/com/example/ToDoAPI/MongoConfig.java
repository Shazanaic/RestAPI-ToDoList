package com.example.ToDoAPI;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String CONNECTION_STRING;

    @Override
    protected String getDatabaseName() {
        return "to-do-list-api";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {

        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();

        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).serverApi(serverApi).build();

        return MongoClients.create(settings);
    }
}
