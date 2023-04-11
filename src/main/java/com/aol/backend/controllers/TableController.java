package com.aol.backend.controllers;



import com.aol.models.Column;
import com.aol.models.Database;
import com.aol.models.Table;
import com.aol.models.Row;
import com.opencsv.exceptions.CsvValidationException;
import io.vertx.ext.web.RoutingContext;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import static com.aol.backend.Utils.Utils.toTable;
import static com.aol.backend.Utils.Utils.toRows;

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


  public void insertToTable(RoutingContext context){
    //get table
    String tableName = context.pathParam("name");
    Database database = Database.getInstance();
    Table table = database.getTable(tableName);
    List <Column> cols = table.getColumns();

    //list of lines
    List<String[]> csvData = new ArrayList<>();

    //read csv file
    try (CSVReader reader = new CSVReader(new StringReader(context.getBodyAsString()))) {
      //line of file
      String[] row;

      while ((row = reader.readNext()) != null) {
       csvData.add(row);
      }
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
    }
    if(csvData.size()>0){
      List <Row> rows = toRows(csvData,cols);
      table.setRows(rows);
      context.response().setStatusCode(200).end(table.showRows());
    }

  }
}
