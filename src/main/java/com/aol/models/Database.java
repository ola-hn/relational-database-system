package com.aol.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
  private static Database database = null;
  private static final Map<String,Table> tables = new ConcurrentHashMap<>();

  private Database(){
  }

  public Table getTable(String name){
    return tables.get(name);
  }

  public void addTable(String tableName,Table table){
    tables.put(tableName,table);
  }

  public static Database getInstance(){
    if(database ==null){
      database = new Database();
    }
    return database;
  }
}
