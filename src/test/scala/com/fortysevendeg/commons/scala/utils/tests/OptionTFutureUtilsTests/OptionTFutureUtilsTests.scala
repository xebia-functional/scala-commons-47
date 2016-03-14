package com.fortysevendeg.commons.scala.utils.tests.OptionTFutureUtilsTests

import org.specs2.mutable._
import com.fortysevendeg.commons.scala.utils.OptionTFutureConversion._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class OptionTFutureUtilsTests extends Specification {

  def expect[A](exp: Result[A], expected: A) =
    Await.result(exp.run, Duration.Inf) must equalTo(Option(expected))

  "The OptionTFutureConversion" should {
    "Transform a Future[Option[A]] into an A" in {
      expect(? <~ Future(Option(1)), 1)
    }
    "Transform a Future[A] into an A" in {
      expect(? <~ Future(1), 1)
    }
    "Transform a Option[A] into an A" in {
      expect(? <~ Option(1), 1)
    }
    "Transform an A into an A" in {
      expect(? <~ 1, 1)
    }
    "Transform a Future[List[A]] into an List[A]" in {
      expect(? <~ Future(List(1, 2, 3)), List(1, 2, 3))
    }
    "Mix in heterogeneous values into a for comprehension" in {
      expect(for {
        a <- ? <~ Future(Option(1))
        b <- ? <~ Future(2)
        c <- ? <~ Option(3)
        d <- ? <~ 4
        e <- ? <~ Future(List(5, 6, 7))
      } yield (a, b, c, d, e), (1, 2, 3, 4, List(5, 6, 7)))
    }
  }

}