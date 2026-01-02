package com.example.ToDoAPI;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final String CONNECTION_STRING =
            "mongodb+srv://orlevicu_db_user:yBqYpUP5W9sGCncX@to-do-list-api.ut4dknd.mongodb.net";

    @Override
    protected String getDatabaseName() {
        return "to-do-list-api";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        return MongoClients.create(settings);
    }
}
