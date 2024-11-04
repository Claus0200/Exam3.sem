package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.example.controllers.TripController;
import org.example.security.enums.Role;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    TripController controller;

    public TripRoutes() {
        this.controller = new TripController();
    }

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", controller::readAll, Role.ANYONE);
            get("/{id}", controller::read, Role.ANYONE);
            post("/", controller::create);
            put("/{id}", controller::update);
            delete("/{id}", controller::delete);
            put("/{tripId}/guides/{guideId}", controller::addGuideToTrip);
            post("/populate", controller::populate);
            get("/category/{category}", controller::sortByCategory, Role.ANYONE);
            get("/guides/totalprice", controller::getTotalPriceOfGuides, Role.ANYONE);
            get("/{tripId}/totalweight", controller::getTotalPriceOfPacking, Role.ANYONE);
        };
    }
}
