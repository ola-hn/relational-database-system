package com.aol.backend.Utils;

import com.aol.models.Table;
import com.google.gson.Gson;

public class Utils {

  public static Table toTable(String jString){
    return new Gson().fromJson(jString, Table.class);

  }
}
