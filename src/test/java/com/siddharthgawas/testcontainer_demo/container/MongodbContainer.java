package com.siddharthgawas.testcontainer_demo.container;

import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.startupcheck.IsRunningStartupCheckStrategy;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;

/**
 * The type Mongodb container.
 */
public class MongodbContainer extends GenericContainer<MongodbContainer> {

    /**
     * The constant MONGO_DB_CONTAINER.
     */
    private static final String MONGO_DB_CONTAINER = "mongo:6.0.4";
    /**
     * The constant ROOT_USERNAME.
     */
    private static final String ROOT_USERNAME = "root_user";
    /**
     * The constant ROOT_PASSWORD.
     */
    private static final String ROOT_PASSWORD = "root_password";
    /**
     * The constant DB.
     */
    private static final String DB = "testcontainer_db";
    /**
     * The constant INTERNAL_PORT.
     */
    private static final Integer INTERNAL_PORT = 27017;

    /**
     * The constant INSTANCE.
     */
    private static MongodbContainer INSTANCE;


    /**
     * Instantiates a new Mongodb container.
     */
    private MongodbContainer() {
        super(DockerImageName.parse(MONGO_DB_CONTAINER));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MongodbContainer getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new MongodbContainer()
                    .withExposedPorts(INTERNAL_PORT)
                    .withClasspathResourceMapping("mongo-init.js",
                            "/docker-entrypoint-initdb.d/mongo-init.js", BindMode.READ_ONLY)
                    .withEnv("MONGO_INITDB_ROOT_USERNAME", ROOT_USERNAME)
                    .withEnv("MONGO_INITDB_ROOT_PASSWORD", ROOT_PASSWORD)
                    .withEnv("MONGO_INITDB_DATABASE", DB)
                    .withStartupCheckStrategy(new IsRunningStartupCheckStrategy());
        }
        return INSTANCE;
    }

    /**
     * Start.
     */
    @Override
    public void start() {
        super.start();
        System.setProperty("SPRING_DATA_MONGODB_PORT", this.getMappedPort(INTERNAL_PORT).toString());
    }

    /**
     * Stop.
     */
    @Override
    public void stop() {
        super.stop();
    }
}
