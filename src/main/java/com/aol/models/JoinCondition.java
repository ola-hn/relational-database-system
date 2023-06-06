package com.aol.models;

public class JoinCondition {
  private String baseColumn;
  private String joinColumn;

  public JoinCondition(String baseColumn, String joinColumn) {
    this.baseColumn = baseColumn;
    this.joinColumn = joinColumn;
  }

  public String getBaseColumn() {
    return baseColumn;
  }

  public String getJoinColumn() {
    return joinColumn;
  }
}
