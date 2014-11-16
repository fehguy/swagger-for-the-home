import org.eatbacon.sfth

import data._

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec

class Specs extends FunSpec with ShouldMatchers {
  describe("ConfigurationDao") {
    it("should read JSON") {
      ConfigurationDao.reload
    }
  }
}
