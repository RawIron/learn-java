# Java scratchpad

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
change the absolute path of `my_home` in the `Makefile`.
it has to be the directory in which you executed the `git clone` command.

for example after you cloned the repo your directory structure looks like this:
```
/home/my-user-account/projects/scratch-java
```

set the `my_home` in the `Makefile` to
```
/home/my-user-account/projects
```

copy the required `junit.jar` and `mockito.jar` files into the `my_home` folder. Download a specific version and symlink.
```
junit-4.8.2.jar
junit.jar -> junit-4.8.2.jar

mockito-all-1.9.5.jar
mockito.jar -> mockito-all-1.9.5.jar
```

#### Add one
copy the template `ATest.java`.

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
