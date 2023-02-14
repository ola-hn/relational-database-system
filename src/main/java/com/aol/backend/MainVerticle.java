package com.aol.backend;

import com.aol.backend.controllers.DatabaseController;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){
    Router router = Router.router(vertx);
    DatabaseController databaseController = new DatabaseController(vertx);
    router.get("/api/db/hello").handler(ctx ->{
      ctx.request().response().end("Hello");
    });
    router.get("/api/db/:name").handler(databaseController::createDatabase);
    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
