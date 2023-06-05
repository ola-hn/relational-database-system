package com.aol.models;

import java.util.ArrayList;
import java.util.List;

public class Query {
  private String tableName;
  private List<String> columnNames = new ArrayList<>();

  private List<String> conditions = new ArrayList<>();

  private List<String> groupByColumns = new ArrayList<>();
  private String count ;
  private String sum;
  public Query(String tableName, List<String> columnNames, List<String> conditions, List<String> groupByColumns, String count, String sum){
    this.tableName = tableName;
    this.columnNames = columnNames;
    this.conditions = conditions;
    this.groupByColumns = groupByColumns;
    this.count = count;
    this.sum = sum;
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public List<String> getConditions(){
    return conditions;
  }

  public List<String> getGroupByColumns() {
    return groupByColumns;
  }

  public String getCount() {
    return count;
  }

  public String getSum() {
    return sum;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ");
    sb.append(columnNames);
    if(!count.equals("")){
      sb.append(" COUNT(");
      sb.append(count);
      sb.append(")");
    }
    if(!sum.equals("")){
      sb.append(" SUM(");
      sb.append(sum);
      sb.append(")");
    }
    sb.append("\n");
    sb.append(" FROM "+tableName);
    sb.append("\n");
    if(conditions.size()>0){
      sb.append(" WHERE ");
      sb.append(conditions);
    }
    if(groupByColumns.size()>0){
      sb.append("\n");
      sb.append(" GROUP BY ");
      for(String col: groupByColumns){
        sb.append(col);
        sb.append(",");
      }

    }
    sb.append("\n");
    return sb.toString();
  }
}
