package com.aol.backend.controllers;



import com.aol.models.Database;
import com.aol.models.Table;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import static com.aol.backend.Utils.Utils.toTable;

public class TableController {
  private final Vertx vertx;
  public TableController(Vertx vertx){
    this.vertx = vertx;
  }
  public static void createTable(RoutingContext context){
    String jsonTable = context.getBodyAsString();
    Table table = toTable(jsonTable);
    context.response().end(table.toString());
  }
}
