package com.univtln.univTlnLPS.net.server;

import lombok.extern.java.Log;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log
public class LPSServer {

    static final public Properties properties = new Properties();

    static {
        properties.setProperty("pu", "mypu");
    }

    public static final String BASE_URI = "http://0.0.0.0:9998/LPS";

    public static HttpServer startServer() {
        Logger logger = Logger.getLogger(LPSServer.class.getName());

        final ResourceConfig rc = new ResourceConfig()
                .packages(true, "com.univtln.univTlnLPS")
                .register(new LoggingFeature(logger, Level.INFO, null, null));

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws InterruptedException {
        log.info("Rest server starting..." + BASE_URI);
        final HttpServer server = startServer();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        log.info(String.format("Application started.%n" +
                "Stop the application using CTRL+C"));

        Thread.currentThread().join();
        server.shutdown();
    }
}