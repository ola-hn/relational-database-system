package com.aol.backend;

import com.aol.backend.controllers.TableController;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    TableController databaseController = new TableController(vertx);
    router.get("/api/table/:name").handler(TableController::getTable);
    router.post("/api/table/:name").handler(TableController::createTable);

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
