package com.aol.models;

import com.aol.backend.network.Node;
import com.hazelcast.map.IMap;

import java.util.Collection;
import java.util.List;
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

  public void removeTable(String tableName) {
    iTables.remove(tableName);
    tables.remove(tableName);
  }

  public void addTable(String tableName,Table table){
    iTables.put(tableName, table);
    tables.put(tableName,table);
  }

  public Collection<Table> getTables() {
    return iTables.values();
  }

  public static Database getInstance(){
    if(database ==null){
      database = new Database();
    }
    return database;
  }
}
