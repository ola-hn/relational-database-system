package com.aol.models;

import java.util.ArrayList;
import java.util.List;

public class Query {
  private String tableName;
  private List<String> columnNames = new ArrayList<>();

  private List<String> conditions = new ArrayList<>();
  public Query(String tableName, List<String> columnNames, List<String> conditions){
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.conditions = conditions;
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public List<String> getConditions(){
    return conditions;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ");
    sb.append(columnNames);
    sb.append("\n");
    sb.append(" FROM "+tableName);
    sb.append("\n");
    return sb.toString();
  }
}
