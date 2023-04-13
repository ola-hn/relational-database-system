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
    TableController tableController = new TableController();
    router.get("/api/table/:name").handler(tableController::getTable);
    router.post("/api/table/:name").handler(tableController::createTable);
    router.post("/api/table/insertTo/:name").handler(tableController::insertToTable);
    router.get("/api/query").handler(tableController::selectQuery);

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
