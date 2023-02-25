package com.aol.models;

public class Column {
  private String name;
  private String type;

  public Column(String name, String type) {
    this.name = name;
    this.type = type;
  }
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("COLUMN: name: ");
    sb.append(name);
    sb.append(" type: ");
    sb.append(type);
    sb.append(" ");
    return sb.toString();
  }
}
