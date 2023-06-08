package com.aol.models;

import java.util.Collection;

public class Storage {
  private Database database;

  private static Storage instance;

  private Storage() {
    database = Database.getInstance();
  }

  public static Storage getInstance() {
    if (instance == null) {
      instance = new Storage();
    }
    return instance;
  }

  public Table getTable(String tableName) {
    return database.getTable(tableName);
  }

  public void removeTable(String tableName) {
    database.removeTable(tableName);
  }

  public void addTable(String tableName, Table table) {
    database.addTable(tableName, table);
  }

  public void removeAllTables() {
    database.removeAllTables();
  }

  public Collection<Table> getTables() {
    return database.getTables();
  }
}
