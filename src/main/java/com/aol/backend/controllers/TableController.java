package com.aol.backend.controllers;



import com.aol.models.Database;
import com.aol.models.Table;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

import static com.aol.backend.Utils.Utils.toTable;

public class TableController {
  public void createTable(RoutingContext context){
    String jsonTable = context.getBodyAsString();
    Table table = toTable(jsonTable);
    String tableName = table.getName();
    Database database = Database.getInstance();
    database.addTable(tableName,table);
    context.response().setStatusCode(201).end(table.toString());
  }

  public void getTable(RoutingContext context){
    String tableName = context.pathParam("name");
    Database database = Database.getInstance();
    Table table = database.getTable(tableName);
    if(table==null){
      context.response().setStatusCode(404).end("Table not found");
    }
    else{
      context.response().setStatusCode(200).end(table.toString());
    }
  }


  public static void addToTable(RoutingContext context){

  }
}
