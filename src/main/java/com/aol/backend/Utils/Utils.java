package com.aol.backend.Utils;

import com.aol.models.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static Table toTable(String jString){
    return new Gson().fromJson(jString, Table.class);

  }

  public static Query toQuery(String jString){
    return new Gson().fromJson(jString, Query.class);
  }

  public static <T> Cell<T> getPrimitiveType(String columnType, String value){
    if(columnType.equalsIgnoreCase("integer")){
      Cell<Integer> cell = new Cell<>(Integer.parseInt(value));
      return (Cell<T>) cell;
    }
    if(columnType.equalsIgnoreCase("float")){
      Cell<Float> cell = new Cell<>(Float.parseFloat(value));
      return (Cell<T>) cell;
    }else{
      Cell<String> cell = new Cell<>(value);
      return (Cell<T>) cell;
    }
  }

  public static <T> Cell<T> getPrimitiveTypeForString(String str){
   if(str.matches("^-?\\d+$")){
     int num = Integer.parseInt(str);
     Cell<Integer> cell = new Cell<>(num);
     return (Cell<T>) cell;
   } else if (str.matches("^-?\\d*\\.\\d+$")) {
     float num = Float.parseFloat(str);
     Cell<Float> cell = new Cell<>(num);
     return (Cell<T>) cell;
   }else{
     Cell<String> cell = new Cell<>(str);
     return (Cell<T>) cell;
   }
  }
  public static List <Row> toRows(List<String[]> csvData, List<Column> columns){
    List <Row> rows = new ArrayList<>();

    if(csvData != null){
    for(String[] row : csvData){
      Row r = new Row();
      for(int i=0; i<columns.size();i++){
      Cell c = getPrimitiveType(columns.get(i).getType(),row[i]);
      r.addCell(c);
      }
      rows.add(r);
    }
    }
    return rows;
  }
}
