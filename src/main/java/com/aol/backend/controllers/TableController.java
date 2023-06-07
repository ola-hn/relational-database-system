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
    context.response().setStatusCode(201).end("Table created successfully!\n\n"+table.toString());
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
        Table table1 = database.getTable(query.getTableName());

        if(query.getJoinTable() != null && database.getTable(query.getJoinTable().getTableName()) != null
          && database.getTable(query.getJoinTable().getTableName()).tableContainsColumns(query.getJoinTable().getColumnNames())){

          Table table2 = database.getTable(query.getJoinTable().getTableName());

          List<Row> joinedRows = table1.innerJoin(table2, query.getJoinCondition());

          List<Row> rowsList = table1.getRowsMatchingConditions(joinedRows, query.getConditions());

          if(query.getGroupByColumns().size()>0) {
            filteredRows = table1.groupByCols(rowsList, query.getGroupByColumns(), query.getCount(), query.getSum());
          }else{
            filteredRows= table1.getRowsByIndex(query.getColumnNames(),rowsList);
          }

          sb.append("\n");
          sb.append(query.getColumnNames());
          sb.append("\n");
          sb.append(table1.showRows(filteredRows));
          context.response().setStatusCode(200).end(sb.toString());
        } else {
          List<Row> rowsList = table1.getRowsMatchingConditions(table1.getRows(), query.getConditions());

          if(query.getGroupByColumns().size()>0) {
            filteredRows = table1.groupByCols(rowsList, query.getGroupByColumns(), query.getCount(), query.getSum());
          }else{
            filteredRows= table1.getRowsByIndex(query.getColumnNames(),rowsList);
          }

          sb.append("\n");
          sb.append(query.getColumnNames());
          sb.append("\n");
          sb.append(table1.showRows(filteredRows));
          context.response().setStatusCode(200).end(sb.toString());
        }
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

      // skip row listing column names
      reader.readNext();

      while ((row = reader.readNext()) != null) {
       csvData.add(row);
      }
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
    }
    if(csvData.size()>0){
      List <Row> rows = toRows(csvData,cols);
      table.setRows(rows);
      database.addTable(tableName, table);
      context.response().setStatusCode(200).end(table.showRows(table.getRows()));
    }

  }
}
