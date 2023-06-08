package com.aol.backend.Utils;
import com.aol.models.Table;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

public class TableSerializableFactory implements DataSerializableFactory {

  private static final int factoryID = 1;
  private static final int tableClassID = 123;

  @Override
  public IdentifiedDataSerializable create(int typeId) {
    if (typeId == tableClassID) {
      return new Table();
    }
    return null;
  }
}

