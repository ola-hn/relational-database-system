package com.aol.backend;

import com.aol.backend.controllers.TableController;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){
    Router router = Router.router(vertx);
    TableController databaseController = new TableController(vertx);
    router.get("/api/db/hello").handler(ctx ->{
      ctx.request().response().end("Hello");
    });
    router.post("/api/table/:name").handler(TableController::createTable);

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
