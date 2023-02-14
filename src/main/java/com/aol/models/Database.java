package com.aol.models;

import java.util.List;

public class Database {
  private String name;
  private List<Table> tables;

  public Database(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("I'm a database, my current name is ");
    sb.append(name);
    return sb.toString();
  }
}
