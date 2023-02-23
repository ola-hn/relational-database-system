package com.aol.backend.controllers;



import com.aol.models.Database;
import com.aol.models.Table;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

import static com.aol.backend.Utils.Utils.toTable;

public class TableController {
  private final Vertx vertx;
  private static Map<String, Table> tables = new HashMap<>();
  public TableController(Vertx vertx){
    this.vertx = vertx;
    this.tables = new HashMap<>();
  }
  public static void createTable(RoutingContext context){
    String jsonTable = context.getBodyAsString();
    Table table = toTable(jsonTable);
    String tableName = table.getName();
    tables.put(tableName,table);
    context.response().setStatusCode(201).end(table.toString());
  }

  public static void getTable(RoutingContext context){
    String tableName = context.pathParam("name");
    Table table = tables.get(tableName);
    if(table==null){
      context.response().setStatusCode(404).end("Table not found");
    }
    else{
      context.response().setStatusCode(200).end(table.toString());
    }
  }
}
