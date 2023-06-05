package com.aol.backend.controllers;



import com.aol.models.*;
import com.opencsv.exceptions.CsvValidationException;
import io.vertx.ext.web.RoutingContext;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

import static com.aol.backend.Utils.Utils.*;

public class TableController {
  public void createTable(RoutingContext context){
    String jsonTable = context.getBodyAsString();
    Table table = toTable(jsonTable);
    String tableName = table.getName();
    Database database = Database.getInstance();
    database.addTable(tableName,table);
    context.response().setStatusCode(201).end(table.toString());
  }

  public Query createQuery(RoutingContext context){
    String jsonQuery = context.getBodyAsString();
    Query query = toQuery(jsonQuery);
    //context.response().setStatusCode(201).end(query.toString());
    return query;
  }

  public void selectQuery(RoutingContext context){
    Database database = Database.getInstance();
    Query query = createQuery(context);
    StringBuilder sb = new StringBuilder();
    List<Row> filteredRows;
    sb.append(query.toString());
    if(database.getTable(query.getTableName()) !=null){
          if(database.getTable(query.getTableName()).tableContainsColumns(query.getColumnNames())){
            Table tab = database.getTable(query.getTableName());
            List<Row> rowsList = tab.getRowsMatchingConditions(query.getConditions());

            if(query.getGroupByColumns().size()>0) {
              filteredRows = tab.groupByCols(rowsList, query.getGroupByColumns(), query.getCount(), query.getSum());
            }else{
              filteredRows= tab.getRowsByIndex(query.getColumnNames(),rowsList);
            }

            sb.append("\n");
            sb.append(query.getColumnNames());
            sb.append("\n");
            sb.append(tab.showRows(filteredRows));
            context.response().setStatusCode(200).end(sb.toString());
          }else{
            sb.append("column not found");
            context.response().setStatusCode(404).end(sb.toString());
          }
    }else{
      sb.append("Table not found");
      context.response().setStatusCode(404).end(sb.toString());
    }

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
    // don't forget about the first line that might contain the columns of the table
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
      context.response().setStatusCode(200).end(table.showRows(rows));
    }

  }
}
