package com.mongock.example;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;

@ContextConfiguration(initializers = {MongoDbContainerInitializer.Initializer.class})
public class MongoDbContainerInitializer extends GenericContainer<MongoDbContainerInitializer> {

    private static final int MONGODB_PORT = 27017;

    private static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.2";
    private static final String MONGO_INITDB_ROOT_USERNAME = "MongoDbContainer_username";
    private static final String MONGO_INITDB_ROOT_PASSWORD = "MongoDbContainer_password";
    private static final String MONGO_INITDB_DATABASE = "MongoDbContainer_database";
    private static final MongoDbContainerInitializer MONGO_DB_CONTAINER =
            new MongoDbContainerInitializer();

    public MongoDbContainerInitializer() {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    public MongoDbContainerInitializer(@NotNull String image) {
        super(image);
        setPortBindings(Collections.singletonList(String.format("%s:%s", MONGODB_PORT, MONGODB_PORT)));
        setEnv(
                Arrays.asList(
                        String.format("%s=%s", "MONGO_INITDB_ROOT_USERNAME", MONGO_INITDB_ROOT_USERNAME),
                        String.format("%s=%s", "MONGO_INITDB_ROOT_PASSWORD", MONGO_INITDB_ROOT_PASSWORD),
                        String.format("%s=%s", "MONGO_INITDB_DATABASE", MONGO_INITDB_DATABASE)));
        addExposedPort(MONGODB_PORT);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            MONGO_DB_CONTAINER.start();
            TestPropertyValues.of("spring.data.mongodb.uri=" + getConnectURI())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    static String getConnectURI() {
        return String.format(
                "mongodb://%s:%s@localhost:%s/mongobee?authSource=admin",
                MONGO_INITDB_ROOT_USERNAME, MONGO_INITDB_ROOT_PASSWORD, MONGODB_PORT);
    }
}
