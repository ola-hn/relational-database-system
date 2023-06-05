package com.aol.backend.network;
import com.aol.backend.MainVerticle;
import com.aol.backend.Utils.TableSerializableFactory;
import com.hazelcast.config.Config;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;


public class Node {
  private static HazelcastClusterManager clusterManager = new HazelcastClusterManager();
  public static void main(String[] args) {
    //TODO: listen for new memberships and messages...

    Config hazelcastConfig = new Config();
    SerializationConfig serializationConfig = hazelcastConfig.getSerializationConfig();
    serializationConfig.addDataSerializableFactory(1, new TableSerializableFactory());

    clusterManager.setConfig(hazelcastConfig);

    VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

    Vertx.clusteredVertx(options, res -> {
      if (res.succeeded()) {
        Vertx vertx = res.result();

        // deploy verticles on cluster
        vertx.deployVerticle(new MainVerticle());

        if (clusterManager.getNodes().size() == 1)
          System.out.println("Cluster up and running!");
        else
          System.out.println("New node joined cluster!");

      } else {
          if (clusterManager.getNodes().size() == 1)
            System.err.println("Failed to create cluster: " + res.cause());
          else
            System.out.println("Failed to join cluster!");
      }
    });
  }

  public static HazelcastInstance getHazelcastInstance() {
    return clusterManager.getHazelcastInstance();
  }
}
