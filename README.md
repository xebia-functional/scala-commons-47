[![Build Status](https://clinker.47deg.com/desktop/plugin/public/status/scala-commons-47.png?branch=master)](https://clinker.47deg.com/jenkins/job/scala-commons-47/)

# Scala Commons 47

An heterogeneous collection of Scala related utils, traits and classes

**OptionTFutureConversion**

Allows mixing heterogeneous expressions in for comprehensions without the need for nested flatmaps or nested for - yield.
Uses Scalaz ```OptionT``` to resolve ```Future[Option]``` expressions into their flattened values.

```scala

import com.fortysevendeg.commons.scala.utils.OptionTFutureConversion._

def test = for {
    a <- ? <~ Future(Option(1))
    b <- optT <~ Some(1)
    c <- optT <~ Some(List(1, 2, 3))
    d <- optT <* List(1, 2, 3)
} yield (a, b, c, d)

// test.run resolves into a Future[Option[(a,b,c,d)]] with all it's inner values flattened

````

- ```<*``` wraps any expression into an async Future(exp) then ```OptionT[Future, A]```
- ```<~``` wraps any expression into its ```OptionT[Future, A]```
- ```?``` is a an alias for ```optT```

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


