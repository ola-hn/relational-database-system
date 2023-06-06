package com.aol.models;

import java.util.ArrayList;
import java.util.List;

public class Row {
  private List<Cell> cells;

  public Row(List<Cell> cells) {
    this.cells = cells;
  }
  public Row(){
    this.cells = new ArrayList<>();
  }
  public void addCell(Cell c){
    this.cells.add(c);
  }
  public Cell getCell(int index){
    return cells.get(index);
  }
  public Row removeLastCell(){
    int i = cells.size() - 1 ;
   Row row = new Row();
   for(int y = 0; y<cells.size()-1;y++){
     Cell cell = getCell(y);
     row.addCell(cell);
   }
   return row;
  }

  public void removeCell(int index){
    cells.remove(index);
  }

  public Cell getLastCell(){
    int i = cells.size() - 1 ;
    return cells.get(i);
  }

  public List<Cell> getCells(){
    return  cells;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    for (Cell cell:cells) {
      sb.append(cell.toString());
      sb.append(" ;");
    }
    sb.append("\n");
    return sb.toString();
  }
  public Row copy() {
    Row copiedRow = new Row();
    for (Cell cell : cells) {
      copiedRow.addCell(cell.copy()); 
    }
    return copiedRow;
  }

  public void addCells(List<Cell> cellsToAdd) {
    cells.addAll(cellsToAdd);
  }
}
