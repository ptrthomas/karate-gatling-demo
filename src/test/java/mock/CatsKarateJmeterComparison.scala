package mock

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._

import scala.concurrent.duration._

class CatsKarateJmeterComparison extends Simulation {
  MockUtils.startServer()

  val flow = scenario("create").repeat(10) {
    exec(karateFeature("classpath:mock/cats-flow.feature"))
  }

  setUp(
    flow.inject(rampUsers(10) over (5 seconds))
  )
}
