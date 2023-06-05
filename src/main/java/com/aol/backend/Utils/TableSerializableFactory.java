package com.aol.backend.Utils;
import com.aol.models.Table;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

public class TableSerializableFactory implements DataSerializableFactory {

  private static final int MY_FACTORY_ID = 1;
  private static final int MY_CLASS_TYPE = 123;

  @Override
  public IdentifiedDataSerializable create(int typeId) {
    if (typeId == MY_CLASS_TYPE) {
      return new Table();
    }
    return null;
  }
}

