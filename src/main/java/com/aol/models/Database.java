package com.aol.models;

import com.aol.backend.network.Node;
import com.hazelcast.map.IMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
  private static Database database = null;

  // TODO: remove tables field?
  private static final Map<String,Table> tables = new ConcurrentHashMap<>();
  IMap<String, Table> iTables = Node.getHazelcastInstance().getMap("tables");

  private Database(){
  }

  public Table getTable(String name){
    return iTables.get(name);
  }

  public void addTable(String tableName,Table table){
    tables.put(tableName,table);
    iTables.put(tableName, table);
  }

  public static Database getInstance(){
    if(database ==null){
      database = new Database();
    }
    return database;
  }
}
