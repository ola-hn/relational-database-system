package com.aol.backend.controllers;



import com.aol.models.*;
import com.opencsv.exceptions.CsvValidationException;
import io.vertx.ext.web.RoutingContext;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

import javax.xml.crypto.Data;

import static com.aol.backend.Utils.Utils.*;

public class TableController {
  private Storage storage;

  public TableController() {
    storage = Storage.getInstance();
  }
  public void createTable(RoutingContext context) {
    String name = context.pathParam("name");
    String jsonTable = context.getBodyAsString();
    Table table = toTable(jsonTable);
    String tableName = table.getName();
    Table existingTable = storage.getTable(tableName);
    if (existingTable != null) {
      context.response().setStatusCode(201).end("Table already exists!\n\n" + existingTable.toString());
      return;
    }
    storage.addTable(tableName, table);
    context.response().setStatusCode(201).end("Table successfully created!\n\n" + table.toString());
  }

  public void dropTable(RoutingContext context) {
    String tableName = context.pathParam("name");
    Table existingTable = storage.getTable(tableName);
    if (existingTable == null) {
      context.response().setStatusCode(202).end("Table not found");
      return;
    }
    storage.removeTable(tableName);
    context.response().setStatusCode(202).end("Table successfully dropped!");
  }

  public Query createQuery(RoutingContext context) {
    String jsonQuery = context.getBodyAsString();
    Query query = toQuery(jsonQuery);
    return query;
  }

  public void selectQuery(RoutingContext context) {
    Query query = createQuery(context);
    StringBuilder sb = new StringBuilder();
    List<Row> filteredRows;
    sb.append(query.toString());
    Table table = storage.getTable(query.getTableName());
    if (table != null) {
      if (table.tableContainsColumns(query.getColumnNames())) {
        if (query.getJoinTable() != null && storage.getTable(query.getJoinTable().getTableName()) != null
          && storage.getTable(query.getJoinTable().getTableName()).tableContainsColumns(query.getJoinTable().getColumnNames())) {

          Table joinTable = storage.getTable(query.getJoinTable().getTableName());
          List<Row> joinedRows = table.innerJoin(joinTable, query.getJoinCondition());
          List<Row> rowsList = table.getRowsMatchingConditions(joinedRows, query.getConditions());

          if (query.getGroupByColumns().size() > 0) {
            filteredRows = table.groupByCols(rowsList, query.getGroupByColumns(), query.getCount(), query.getSum());
          } else {
            filteredRows = table.getRowsByIndex(query.getColumnNames(), rowsList);
          }

          sb.append("\n");
          sb.append(query.getColumnNames());
          sb.append("\n");
          sb.append(table.showRows(filteredRows));
          context.response().setStatusCode(200).end(sb.toString());
        } else {
          List<Row> rowsList = table.getRowsMatchingConditions(table.getRows(), query.getConditions());

          if (query.getGroupByColumns().size() > 0) {
            filteredRows = table.groupByCols(rowsList, query.getGroupByColumns(), query.getCount(), query.getSum());
          } else {
            filteredRows = table.getRowsByIndex(query.getColumnNames(), rowsList);
          }

          sb.append("\n");
          sb.append(query.getColumnNames());
          sb.append("\n");
          sb.append(table.showRows(filteredRows));
          context.response().setStatusCode(200).end(sb.toString());
        }
      } else {
        sb.append("column not found");
        context.response().setStatusCode(404).end(sb.toString());
      }
    } else {
      sb.append("Table not found");
      context.response().setStatusCode(404).end(sb.toString());
    }
  }

  public void deleteFromTable(RoutingContext context) {
    Query query = createQuery(context);
    Table table = storage.getTable(query.getTableName());

    if (table == null) {
      context.response().setStatusCode(404).end("Table not found");
      return;
    }

    List<String> columnNames = query.getColumnNames();
    if (!table.tableContainsColumns(columnNames)) {
      context.response().setStatusCode(404).end("Column not found");
      return;
    }
    List<Row> rowsList = table.getRowsMatchingConditions(table.getRows(), query.getConditions());
    table.removeRows(rowsList);
    storage.addTable(table.getName(), table);

    context.response().setStatusCode(200).end("Successfully deleted from table!");
  }

  public void getTable(RoutingContext context) {
    String tableName = context.pathParam("name");
    Table table = storage.getTable(tableName);
    if (table == null) {
      context.response().setStatusCode(404).end("Table not found");
    } else {
      context.response().setStatusCode(200).end(table.toString());
    }
  }

  public void showTables(RoutingContext context) {
    List<Table> tables = new ArrayList<>(storage.getTables());

    if (tables.size() == 0) {
      context.response().setStatusCode(201).end("Empty database");
      return;
    }

    StringBuilder sb = new StringBuilder();
    int size = tables.size();
    sb.append(size);
    sb.append(" table");
    if (size > 1)
      sb.append("s");
    sb.append("\n");

    for (Table table : tables) {
      sb.append(table.toString());
      sb.append("\n\n");
    }
    context.response().setStatusCode(201).end(sb.toString());
  }

  public void dropTables(RoutingContext context) {
    List<Table> tables = new ArrayList<>(storage.getTables());

    if (tables.size() == 0) {
      context.response().setStatusCode(201).end("Empty database");
      return;
    }
    storage.removeAllTables();
    context.response().setStatusCode(201).end("All tables successfully dropped");
  }


  public void insertToTable(RoutingContext context) {
    String tableName = context.pathParam("name");
    Table table = storage.getTable(tableName);
    if (table == null) {
      context.response().setStatusCode(404).end("Table not found");
      return;
    }

    List<Column> cols = table.getColumns();

    List<String[]> csvData = new ArrayList<>();

    String fileContent = context.getBodyAsString().trim();
    if (fileContent.equals("")) {
      context.response().setStatusCode(200).end("Insertion completed!\n\n" + table.showRows(table.getRows()));
      return;
    }
    try (CSVReader reader = new CSVReader(new StringReader(fileContent))) {
      String[] row;
      reader.readNext(); // Skip row listing column names
      while ((row = reader.readNext()) != null) {
        csvData.add(row);
      }
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
    }
    if (csvData.size() > 0) {
      List<Row> rows = toRows(csvData, cols);
      table.setRows(rows);
      storage.addTable(tableName, table);
      context.response().setStatusCode(200).end("Insertion completed!\n\n" + table.showRows(table.getRows()));
    }
  }
}
