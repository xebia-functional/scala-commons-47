package com.fortysevendeg.commons.scala.utils


import scalaz._
import scalaz.OptionT._
import scala.concurrent.{ExecutionContext, Future}
import scala.Some

/**
 * Allows mixing expressions in for comprehensions without the need for nested flatmaps or nested for - yield.
 * Uses Scalaz OptionT to resolve Future[Option] expressions into their flattened values.
 * e.g.
 *
 * import com.fortysevendeg.commons.scala.utils.OptionTFutureConversion._
 *
 * def test = for {
 * a <- ? <~ Future(Option(1))
 * b <- optT <~ Some(1)
 * c <- optT <~ Some(List(1, 2, 3))
 * d <- optT <* List(1, 2, 3)
 * } yield (a, b, c, d)
 *
 * test.run
 *
 * <* wraps any expression into an async Future(exp) then OptionT[Future, A]
 * <~ wraps any expression into its OptionT[Future, A]
 *
 *
 * ? is a an alias for optT
 *
 */

trait OptionTFutureConversionOps {

  type Result[+A] = OptionT[Future, A]

  implicit def futureMonad(implicit ec: ExecutionContext) = new Monad[Future] {
    override def point[A](a: ⇒ A): Future[A] = Future(a)

    override def bind[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa flatMap f
  }

  def toFutureOptionT[A](x: => Future[Option[A]])(implicit d: DummyImplicit): Result[A] = optionT(x)

  def toFutureOptionT[A](x: => Future[A])(implicit ec: ExecutionContext): Result[A] = optionT(x map (Some(_)))

  def toFutureOptionT[A](x: => Option[A])(implicit d: DummyImplicit, e: DummyImplicit): Result[A] = toFutureOptionT(Future.successful(x))

  def toFutureOptionT[A](x: => A): Result[A] = toFutureOptionT(Future.successful(Some(x)))

  def toAsyncFutureOptionT[A](x: => A)(implicit ec: ExecutionContext): Result[A] = toFutureOptionT(Future(x))

  def <~[A](x: => Future[Option[A]])(implicit ec: ExecutionContext, d: DummyImplicit): Result[A] = toFutureOptionT(x)

  def <~[A](x: => Future[A])(implicit ec: ExecutionContext): Result[A] = toFutureOptionT(x)

  def <~[A](x: => Option[A])(implicit d: DummyImplicit): Result[A] = toFutureOptionT(x)

  def <~[A](x: => A): Result[A] = toFutureOptionT(x)

  def <*[A](x: => A)(implicit ec: ExecutionContext): Result[A] = toAsyncFutureOptionT(x)

  object optT extends OptionTFutureConversionOps

  object ? extends OptionTFutureConversionOps

}

object OptionTFutureConversion extends OptionTFutureConversionOps