package com.aol.models;

import java.util.List;

public class JoinTable {
  private String tableName;
  private List<String> columnNames;

  // Default constructor is needed for JSON deserialization
  public JoinTable() {}

  // Additional constructor for creating instances manually
  public JoinTable(String tableName, List<String> columnNames) {
    this.tableName = tableName;
    this.columnNames = columnNames;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }
}

