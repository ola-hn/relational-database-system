package com.aol.models;

import java.util.List;

public class Table {
  private String name;
  private List<Column> columns;
  private List<Row> rows;

  public Table(String name) {
    this.name = name;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("I'm a table, my current name is ");
    sb.append(name);
    return sb.toString();
  }
}
