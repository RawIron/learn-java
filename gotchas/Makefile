
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


Vows.class:	Vows.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" Vows.java


ATest.class:	ATest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" ATest.java

ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" ByteArrayTest.java

TheNullTest.class:	TheNullTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" TheNullTest.java

MockTest.class:	MockTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" MockTest.java


tests:	ATest.class ByteArrayTest.class MockTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ATest
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ByteArrayTest
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.MockTest


clean:	
	rm -rf *.class
