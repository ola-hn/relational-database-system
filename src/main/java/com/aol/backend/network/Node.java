package com.aol.backend.network;
import com.aol.backend.MainVerticle;
import com.aol.backend.Utils.TableSerializableFactory;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
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
          System.out.println("Node successfully joined cluster!");

        // register membership listener to detect when a node joins/leaves
        HazelcastInstance hazelcastInstance= clusterManager.getHazelcastInstance();
        hazelcastInstance.getCluster().addMembershipListener(new MembershipListener() {
          @Override
          public void memberAdded(MembershipEvent membershipEvent) {
            // a new node joined the cluster
            String newMemberAddress = membershipEvent.getMember().getAddress().toString();
            System.out.println("New node joined the cluster: " + newMemberAddress);
          }

          @Override
          public void memberRemoved(MembershipEvent membershipEvent) {
            // a node left the cluster
            String leftMemberAddress = membershipEvent.getMember().getAddress().toString();
            System.out.println("A node left the cluster: " + leftMemberAddress);
          }
        });

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
