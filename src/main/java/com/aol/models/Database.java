package com.aol.models;

import java.util.List;

public class Database {
  private String name;
  private List<Table> tables;

  public Database(String name) {
    this.name = name;
  }
}
