package com.aol.models;

import java.util.List;

public class Table {
  private String name;
  private List<Column> columns;
  private List<String> columnNames;
  private List<Row> rows;

  public Table(String name,List<String> columnNames) {
    this.name = name;
    this.columnNames = columnNames;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("I'm a table, my current name is ");
    sb.append(name);
    sb.append("and the columns i'm comprised of are ");
    sb.append(columnNames);
    return sb.toString();
  }
}
