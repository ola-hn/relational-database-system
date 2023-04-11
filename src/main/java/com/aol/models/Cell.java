package com.aol.models;

public class Cell<T> {
  private final T value;

  public Cell(T value) {
    this.value = value;
  }
  public T getValue(){
    return value;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(value);
    return sb.toString();
  }
}
