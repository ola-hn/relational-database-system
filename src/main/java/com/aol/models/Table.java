package com.aol.models;

import java.util.ArrayList;
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

  //print rows of a table
  public String showRows(List<Row> rows){
    StringBuilder sb = new StringBuilder();
    for (Row row:rows) {
      sb.append(row.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
  public String getName(){
    return name;
  }
  public List<Row> getRows(){
    return rows;
  }
  public List<Column> getColumns(){return columns;}
  public int getColumnIndexByName(String name){
    for (Column col: columns) {
      if(col.getName().equalsIgnoreCase(name)){
        return columns.indexOf(col);
      }
    }
    return -1;
  }

  public boolean tableContainsColumns(List<String> columnNames){
    for(String name: columnNames){
      if(getColumnIndexByName(name) == -1){
        return false;
      }
    }
    return true;
  }
  public List<Row> getRowsByIndex(List<String> columnsList){
    List<Row> filteredRows = new ArrayList<>();
    for(Row row : rows){
      Row filteredRow = new Row();
      for(String col : columnsList){
        if(getColumnIndexByName(col) != -1){
          filteredRow.addCell(row.getCell(getColumnIndexByName(col)));
        }
      }
      filteredRows.add(filteredRow);
    }
    return filteredRows;
  }

  public void setRows(List<Row> list){
    this.rows = list;
  }
}
