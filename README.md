# A few quick Java hacks

## jam
simple coding challenges.
come up with one and jam.

#### Algo and Data Structures
run the tests
* GenericArrayUnique
* InsertSortLotsOfMemory
* LinkedListAppendOnly
* RingBufferOfArray

#### Concurrency
run `mainClass`
* ThreadsFightOverArray
* ThreadsPingPongWithReferee
* ThreadsRingLoops
* WorkerWithAsyncTask

### Maven

#### Run
run all tests
```bash
mvn test
```

run one test
```bash
mvn test -Dtest=GenericArrayUniqueTest
```

run a `class` with `main` in it
```bash
# compile the classes first
mvn compile
# run main
mvn exec:java -Dexec.mainClass="WorkerWithAsyncTask"
```

## gotchas
test language semantics.
test short recipes.
micro-benchmark.

`gotchas` are implemented as tests.
the modules don't have a `main` function.
to run the `gotchas` simply run the tests.

### Makefile

#### Setup
change the absolute path of `package_home` in the `Makefile`.

for example after you cloned the repo your directory structure looks like this:
```
/home/my-user-account/projects/learn-java
```

set the `package_home` in the `Makefile` to
```
/home/my-user-account/projects/learn-java
```

#### Add one
copy the template `ATest.java` to `YourNewTest.java` for example.

add two lines to the Makefile
```make
YourNewTest.class:  YourNewTest.java junit.jar mockito.jar
  $(jc) YourNewTest.java
YourNewTest: YourNewTest.class
  $(tr) gotchas.YourNewTest
```

add the new test to the `tests` goal
```make
tests: ATest YourNewTest
```

#### Run
run all the tests
```bash
make tests
```

run one test
```bash
make ProductTest
```

remove all compiled and temp files
```bash
make clean
```
