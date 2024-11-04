package org.example.controllers;

import io.javalin.http.Context;

public interface IController {

    void create(Context ctx);

    void read(Context ctx);

    void readAll(Context ctx);

    void update(Context ctx);

    void delete(Context ctx);

}
