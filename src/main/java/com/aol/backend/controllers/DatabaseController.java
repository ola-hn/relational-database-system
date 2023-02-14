package com.aol.backend.controllers;

import com.aol.models.Database;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public class DatabaseController {
  private final Vertx vertx;
  public DatabaseController(Vertx vertx){
    this.vertx = vertx;
  }
  public void createDatabase(RoutingContext context){
    String name = context.pathParam("name");
    Database db = new Database(name);
    context.response().end(db.toString());
  }
}
