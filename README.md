# Java scratchpad

## jam

## gotchas
test language semantics.
test short recipes.
micro-benchmark.

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
```
tests: ATest YourNewTest
```

#### Run
run all the tests
```
make tests
```

run one test
```
make ProductTest
```

remove all compiled and temp files
```
make clean
```