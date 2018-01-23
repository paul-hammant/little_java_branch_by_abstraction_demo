package com.mycompany;

import org.jooby.Jooby;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * @author Paul Hammant DevOps, (c) 2018
 */
public class App extends Jooby {

  {
    get("/color/hair.json", (req, rsp) -> {
      rsp.status(200)
              .type("application/json")
              .send("{\"color\":\"" + getChangingHairColor() + "\"}");
    });
  }

  private String getChangingHairColor() {
    List<String> colors = asList("Blonde", "Brown", "Black", "Red");
    return colors.get(new Random().nextInt(colors.size()));
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
