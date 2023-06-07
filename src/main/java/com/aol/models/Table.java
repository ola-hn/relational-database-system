package com.aol.models;

import java.util.ArrayList;
import com.aol.backend.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.util.List;

public class Table implements IdentifiedDataSerializable {
  private String name;
  private List<Column> columns;
  private List<Row> rows = new ArrayList<>();

  public Table() {}

  public Table(String name,List<Column> columns) {
    this.name = name;
    this.columns = columns;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TABLE: ");
    sb.append(name);
    sb.append("\n\nCOLUMNS\n");
    sb.append(columns);
    sb.append("\n\n"+rows.size()+" rows");
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
    if(columnNames.get(0).equals("*")){
      return true;
    }
    for(String name: columnNames){
      if(getColumnIndexByName(name) == -1){
        return false;
      }
    }
    return true;
  }
  public List<Row> getRowsByIndex(List<String> columnsList, List<Row> rows){
    List<Row> filteredRows = new ArrayList<>();
    if(columnsList.get(0).equals("*")){
      columnsList = getAllColumnNames();
    }
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

  public List<String> getAllColumnNames(){
    List<String> columnsNames = new ArrayList<>();
    for(Column col : columns){
      columnsNames.add(col.getName());
    }
    return columnsNames;
  }
  public String getOperation(String condition){
    if(condition.contains(">=")) return ">=";
    if(condition.contains("<=")) return "<=";
    if(condition.contains("!=")) return "!=";
    if(condition.contains("=")) return "=";
    if(condition.contains(">")) return ">";
    if(condition.contains("<")) return "<";

    return "=";
  }

  public List<Row> getRowsMatchingConditions(List<Row> inputRows, List<String> conditions) {
    List<Row> filteredRows = new ArrayList<>();
    for(Row row : inputRows){
      boolean match = true;
      for(String condition : conditions){
        if(condition.contains("AND") || !condition.contains("OR")){
          if(!checkCondition(row,condition)){
            match = false;
            break;
          }
        } else if(condition.contains("OR")){
          if(checkCondition(row,condition)){
            match = true;
            break;
          }
        }
      }
      if(match){
        filteredRows.add(row);
      }
    }
    return  filteredRows;
  }


  public String removeConnector(String condition){
    if(condition.contains("AND")){
      return condition.replace("AND","");
    }
    if(condition.contains("OR")){
      return condition.replace("OR","");
    }
    return condition;
  }

  public boolean checkCondition(Row row,String condition){
    condition = removeConnector(condition);
    String operation = getOperation(condition);
    String[] conditionParts = condition.split(operation);
    String column = conditionParts[0].trim();
    String value = conditionParts[1].trim();
    int columnIndex = getColumnIndexByName(column);
    Cell cell = row.getCell(columnIndex);
    Column col = columns.get(columnIndex);
    if(col.getType().equals("integer") || col.getType().equals("float")){
      if(operation.equals("=")){
        return  Float.parseFloat(cell.getValue().toString()) == Float.parseFloat(value);
      }
      if(operation.equals("<=")){
        return  Float.parseFloat(cell.getValue().toString()) <= Float.parseFloat(value);
      }
      if(operation.equals("<")){
        return  Float.parseFloat(cell.getValue().toString()) < Float.parseFloat(value);
      }
      if(operation.equals("!=")){
        return  Float.parseFloat(cell.getValue().toString()) != Float.parseFloat(value);
      }
      if(operation.equals(">=")){
        return  Float.parseFloat(cell.getValue().toString()) >= Float.parseFloat(value);
      }
      if(operation.equals(">")){
        return  Float.parseFloat(cell.getValue().toString()) > Float.parseFloat(value);
      }
    }if(col.getType().equals("string")){
      if(operation.equals("=")){
        return cell.getValue().toString().equalsIgnoreCase(value);
      }
      if(operation.equals("!=")){
        return !cell.getValue().toString().equalsIgnoreCase(value);
      }
    }
    return true;
  }
  //function to compare if row already exists except for last cell of the row
public boolean equalsRow(Row row1,Row row2){

    for(int i = 0; i< row1.getCells().size();i++){
      if(!row1.getCell(i).getValue().toString().equals(row2.getCell(i).getValue().toString())) return false;
    }
    return true;
}
  public Row listContainsRow(List<Row> rows, Row row){
   row = row.removeLastCell();
    for(Row r : rows){

      Row newRow = r.removeLastCell();
      if(equalsRow(newRow,row)){ return r ;}


    }
    return null;
  }
  public List<Row> filterRows(List<Row> rows, List<String> columns){
    List<Row> newRows = new ArrayList<>();
    for(Row row : rows){
      Row newRow = new Row();
      for(String col: columns){
        int index = getColumnIndexByName(col);
        Cell cell = row.getCell(index);
        newRow.addCell(cell);
      }
      newRows.add(newRow);
    }
    return newRows;
  }

  public Row getSimilarRow(List<Row> rows, Row row){
    for(Row r : rows){
      r.removeLastCell();
      if(r.equals(row)) return r;
    }
    return null;
  }
  //function to get the last cell of the row

  public List<Row> groupByCols(List<Row> rows, List<String> columns, String count, String sum){

    if(columns.size()>0){
      //if(!count.equals("*")) columns.add(count);
      if(!sum.equals("")) columns.add(sum);
      List <Row> filteredRows = filterRows(rows,columns);
      List <Row> groupedRows = new ArrayList<>();
      for(Row row : filteredRows){
        Row newRow = new Row(row.getCells());
        if(sum.equals("")){
        Cell cell = new Cell<>(1);
        newRow.addCell(cell);}

        //check if newRow exists in groupedRows
        if(listContainsRow(groupedRows,newRow)!=null){
          int oldValue = Integer.parseInt(listContainsRow(groupedRows,newRow).getLastCell().getValue().toString());
          if(sum.equals("")){
          oldValue++;
          listContainsRow(groupedRows,newRow).getLastCell().setValue(oldValue);}
          else{
            int newValue =Integer.parseInt(newRow.getLastCell().getValue().toString());
            newValue+=oldValue;
            listContainsRow(groupedRows,newRow).getLastCell().setValue(newValue);
          }
        }
        else{
          groupedRows.add(newRow);
        }
      }
      return groupedRows;
    }
    else{
      return rows;
    }


  }

  public List<Row> innerJoin(Table joinTable, JoinCondition condition) {
    List<Row> joinedRows = new ArrayList<>();

    for (Row baseRow : rows) {
      for (Row joinRow : joinTable.getRows()) {
        if (satisfiesJoinCondition(baseRow, joinRow, condition)) {
          Row joinedRow = baseRow.copy();
          joinedRow.addCells(joinRow.getCells());
          joinedRows.add(joinedRow);
        }
      }
    }

    return joinedRows;
  }

  private boolean satisfiesJoinCondition(Row baseRow, Row joinRow, JoinCondition condition) {
    String baseColumnValue = baseRow.getCell(getColumnIndexByName(condition.getBaseColumn())).getValue().toString();
    String joinColumnValue = joinRow.getCell(getColumnIndexByName(condition.getJoinColumn())).getValue().toString();

    return baseColumnValue.equals(joinColumnValue);
  }


  @Override
  // TODO: manage id's
  public int getFactoryId() {
    return 1;
  }

  @Override
  public int getClassId() {
    return 123;
  }

  @Override
  public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
    // convert table to JSON
    Gson gson = new GsonBuilder().create();
    String json = gson.toJson(this);

    objectDataOutput.writeString(json);
  }

  @Override
  public void readData(ObjectDataInput objectDataInput) throws IOException {
    Table table = Utils.toTable(objectDataInput.readString());
    this.columns = table.getColumns();
    this.rows = table.getRows();
    this.name = table.getName();
  }

  public void setName(String name) {
    this.name = name;
  }
}
