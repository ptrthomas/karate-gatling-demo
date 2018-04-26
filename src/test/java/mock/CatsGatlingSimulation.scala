package mock

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class CatsGatlingSimulation extends Simulation {

  MockUtils.startServer()

  val httpConf = http.baseURL(System.getProperty("mock.cats.url"))

  val create = scenario("create")
    .exec(http("POST /cats")
      .post("/").body(StringBody("""{ "name": "Billie" }"""))
      .check(jsonPath("$.id").saveAs("id")))
    .exec(http("GET /cats/{id}")
      .get("/${id}")
      .check(jsonPath("$.name").is("Billi")))
    .exec(http("PUT /cats/{id}")
      .put("/${id}").body(StringBody("""{ "id":"${id}", "name": "Bob" }"""))
      .check(jsonPath("$.name").is("Bob")))
    .exec(http("GET /cats/{id}")
      .get("/${id}"))

  val delete = scenario("delete")
    .exec(http("GET /cats")
      .get("/")
      .check(jsonPath("$[*].id").findAll.optional.saveAs("ids")))
    .doIf(_.contains("ids")) {
      foreach("${ids}", "id") {
        exec(http("DELETE /cats/{id}")
          .delete("/${id}"))
          .exec(http("GET /cats/{id}")
            .get("/${id}").check(status.is(404)))
      }
    }

  setUp(
    create.inject(rampUsers(10) over (5 seconds)).protocols(httpConf),
    delete.inject(rampUsers(5) over (5 seconds)).protocols(httpConf)
  )

}
