package com.admin.portal.config;
import com.mongodb.client.MongoClient;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    private final MongoClient mongoClient;

    public MongoConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    public void verifyMongoConnection() {
        try {
            Document ping = mongoClient
                    .getDatabase("Portal")
                    .runCommand(new Document("ping", 1));

            System.out.println("✅ MongoDB Connected Successfully: " + ping.toJson());
        } catch (Exception e) {
            System.err.println("❌ MongoDB Connection Failed");
            e.printStackTrace();
        }
    }
}


