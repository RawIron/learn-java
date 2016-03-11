# Java scratchpad

## jam

## gotchas
### Makefile
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

copy the required `junit.jar` and `mockito.jar` files into the `my_home` folder.
```
junit-4.8.2.jar
junit.jar -> junit-4.8.2.jar

mockito-all-1.9.5.jar
mockito.jar -> mockito-all-1.9.5.jar
```

run all the tests
```
make tests
```

remove all compiled and temp files
```
make clean
```