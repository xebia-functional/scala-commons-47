[![Build Status](https://clinker.47deg.com/desktop/plugin/public/status/scala-commons-47.png?branch=master)](https://clinker.47deg.com/jenkins/job/scala-commons-47/)

# Scala Commons 47

## Monad Utils

An heterogeneous collection of Scala related utils, traits and classes

### OptionTFutureConversion

Allows mixing heterogeneous expressions in for comprehensions without the need for nested flatmaps or nested for - yield.
Uses Scalaz ```OptionT``` to resolve ```Future[Option]``` expressions into their flattened values.

Consider a case where you have the need to pass values from functions with heterogeneous result types and that are somewhat chained or dependent in a list of operations.

```scala

def fetchSample1: Future[Option[Int]] = ???

def fetchSample2(sample1: Int): Future[Option[Int]] = ???

def compute(a: Int, b: Int): Option[Int] = ???

def anotherAsyncComputation(previousComputation: Int): Future[Int] = ???

```

In plain Scala you may chain nested flatMap calls to keep the types compatible...

```scala

def plainScalaImpl: Future[Option[Int]] = fetchSample1 flatMap {
    case Some(sample1) => fetchSample2(sample1) flatMap {
      case Some(sample2) =>
        compute(sample1, sample2) match {
          case Some(previousComputation) => anotherAsyncComputation(previousComputation) map (Option(_))
          case _ => Future.successful(None)
        }
    }
}

```

Alternatively with Scalaz OptionT monad transformer and some boilerplate you can keep the types compatible in
a single for comprehension like so...

```scala

import scalaz._
import scalaz.OptionT._

def optionTImpl: Future[Option[Int]] = (for {
    sample1 <- optionT(fetchSample1)
    sample2 <- optionT(fetchSample2(sample1))
    previousComputation <- optionT(Future.successful(compute(sample1, sample2)))
    finalResult <- optionT(anotherAsyncComputation(previousComputation) map (Option(_)))
  } yield finalResult).run

```

Finally with The OptionTFutureUtils you can further simplify it to

```scala

import com.fortysevendeg.commons.scala.utils.OptionTFutureConversion._

def optionTFutureUtils : Future[Option[Int]] = (for {
    sample1 <- ? <~ fetchSample1
    sample2 <- ? <~ fetchSample2(sample1)
    previousComputation <- ? <~ compute(sample1, sample2)
    finalResult <- ? <~  anotherAsyncComputation(previousComputation)
  } yield finalResult).run

```

Note type wrapping to Future[Option] is no longer necessary as the conversions are automatically provided for many common objects
such as Option, Future[A], A, etc...

This keeps for a nice and simpler operation chaining resulting in  the final Future[Option[A]]

- ```<*``` wraps any expression into an async Future(exp) then ```OptionT[Future, A]```
- ```<~``` wraps any expression into its ```OptionT[Future, A]```
- ```?``` is a an alias for ```optT```

# Install

Simply add the following dependency to your SBT based build

```scala

resolvers += "47deg Public" at "http://clinker.47deg.com/nexus/content/groups/public"

libraryDependencies += "com.fortysevendeg" %% "scala-commons-47" % "0.1-SNAPSHOT" changing()

```


# Credits

Scala Commons 47 is a library maintained by the [47 Degrees](http://47deg.com) team.

# Continuous Integration

CI and Artifact Repository hosted in ClinkerHQ.com

[![ClinkerHQ][1]][2]

# License

Copyright (C) 2014 47 Degrees, LLC
http://47deg.com
hello@47deg.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[1]: http://dl.clinkerhq.com/assets/badge/clinker-badge_125x125.png
[2]: http://clinkerhq.com
[4]: https://clinker.47deg.com/jenkins/job/scala-commons-47/


