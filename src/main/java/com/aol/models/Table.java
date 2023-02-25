package com.aol.models;

import java.util.List;

public class Table {
  private String name;
  private List<Column> columnNames;
  private List<Row> rows;

  public Table(String name,List<Column> columnNames) {
    this.name = name;
    this.columnNames = columnNames;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("I'm a table, my current name is ");
    sb.append(name);
    sb.append(" and the columns I'm comprised of are ");
    sb.append(columnNames);
    return sb.toString();
  }
  public String getName(){
    return name;
  }
}
