package com.mycompany;

import com.typesafe.config.Config;
import org.jooby.Jooby;

/**
 * @author Paul Hammant DevOps, (c) 2018
 */
public class App extends Jooby {

  private ReleaseToggles releaseToggles;

  {
    get("/color/hair.json", (req, rsp) -> {
      rsp.status(200)
              .type("application/json")
              .send("{\"color\":\"" + releaseToggles.getChangingHairColor() + "\"}");
    });

    onStart(registry -> {
      withTogglesFor(registry.require(Config.class).getString("ReleaseToggles"));
    });
  }

  App withTogglesFor(String releaseTogglesClassName) {
    try {
      releaseToggles = (ReleaseToggles) Class.forName(releaseTogglesClassName).newInstance();
      return this;
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
