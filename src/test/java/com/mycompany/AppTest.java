package com.mycompany;

import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * @author Paul Hammant DevOps, (c) 2018
 */
public class AppTest {

  /**
   * One app/server for all the tests in this class. If you want to start/stop a new server per test,
   * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
   */
  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  /**
   * A service test that uses RestAssured to
   * check hair color functionality over HTTP
   */
  @Test
  public void serviceTest() {
    get("/color/hair.json")
        .then()
        .assertThat()
        .body(startsWith("{\"color\":\""))
        .body(endsWith("\"}"))
        .body(specifiesAnyOfTheAllowedColors())
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }

  private AnyOf<String> specifiesAnyOfTheAllowedColors() {
    return anyOf(Arrays.stream(Color.values())
            .map(c -> containsString(c.name())).toArray(Matcher[]::new));
  }



  /**
   * A unit test that checks 'new' enum-based
   * implementation directly (without HTTP or TCP/IP)
   */
  @Test
  public void newHairColorTest() throws Throwable {
    Color color = new MockRouter(new App().withTogglesFor(Release4.class.getName()))
            .get("/color/hair.json");

    // Is an object here, not a JSON string.
    // an instance of that enum to be specific
    assertThat(color, isIn(Color.values()));
  }
}
