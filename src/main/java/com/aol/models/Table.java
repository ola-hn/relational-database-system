package com.aol.models;

import java.util.List;

public class Table {
  private String name;
  private List<Column> columns;
  private List<Row> rows;

  public Table(String name,List<Column> columns) {
    this.name = name;
    this.columns = columns;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("I'm a table, my current name is ");
    sb.append(name);
    sb.append(" and the columns I'm comprised of are ");
    sb.append(columns);
    return sb.toString();
  }
  public String getName(){
    return name;
  }
}
