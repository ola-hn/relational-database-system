package com.aol.backend.network;
import com.aol.backend.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;


public class Cluster {
  public static void main(String[] args) {
    HazelcastClusterManager clusterManager = new HazelcastClusterManager();
    VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

    Vertx.clusteredVertx(options, res -> {
      if (res.succeeded()) {
        Vertx vertx = res.result();

        // deploy verticles on cluster
        vertx.deployVerticle(new MainVerticle());

        System.out.println("Cluster up and running!");

      } else {
        System.err.println("Failed to create cluster: " + res.cause());
      }
    });
  }
}
