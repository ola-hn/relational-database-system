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

  public String toString(){
    StringBuilder sb = new StringBuilder();
    for (Cell cell:cells) {
      sb.append(cell.toString());
      sb.append(" ;");
    }
    sb.append("\n");
    return sb.toString();
  }
}
