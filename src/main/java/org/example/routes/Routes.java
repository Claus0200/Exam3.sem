package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.example.controllers.TripController;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final TripRoutes tripRoutes = new TripRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/trips", tripRoutes.getRoutes());
        };
    }
}
