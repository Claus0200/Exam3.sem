package org.example.config;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.example.exceptions.ApiException;
import org.example.routes.Routes;
import org.example.routes.TripRoutes;
import org.example.security.controllers.AccessController;
import org.example.security.routes.SecurityRoutes;
import org.example.utils.Utils;

public class ApplicationConfig {

    private static Routes routes = new Routes();
    private static AccessController accessController = new AccessController();

    public static void configuration(JavalinConfig config) {
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api"; // base path for all endpoints
        config.router.apiBuilder(routes.getRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }

    public static Javalin startServer(int port) {
        Javalin app = Javalin.create(ApplicationConfig::configuration);

        app.beforeMatched(accessController::accessHandler);

        app.beforeMatched(ctx -> accessController.accessHandler(ctx));
        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);

        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    public static void apiExceptionHandler(ApiException e, Context ctx) {
        String errorMessage = String.format("Error in %s %s: %s", ctx.method(), ctx.path(), e.getMessage());
        ctx.status(e.getStatus());
        ctx.json(Utils.convertToJsonMessage(e.getStatus(), errorMessage, e.getTimestamp()));
    }

}
