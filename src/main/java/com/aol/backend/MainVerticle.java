package com.aol.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){
    Router router = Router.router(vertx);
    router.get("/api/db/hello").handler(ctx ->{
      ctx.request().response().end("Hello");
    });
    router.get("/api/db/:name").handler(ctx -> {
      String name = ctx.pathParam("name");
      ctx.request().response().end("Table "+name);
    });
    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
