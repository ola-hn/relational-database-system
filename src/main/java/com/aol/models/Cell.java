package com.aol.models;

import java.io.Serializable;

public class Cell<T> implements Serializable {
  private T value;

  public Cell(T value) {
    this.value = value;
  }
  public T getValue(){
    return value;
  }
  public void setValue( T value){
    this.value = value;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(value);
    return sb.toString();
  }

  public Cell<T> copy() {
      return new Cell<>(value);
    }
}
