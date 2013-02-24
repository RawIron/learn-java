junit.jar:

Vows.class:	Vows.java junit.jar
	javac -classpath '../junit.jar:..' Vows.java

ATest.class:	ATest.java junit.jar
	javac -classpath '../junit.jar:..' ATest.java

ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	javac -classpath '../junit.jar:..' ByteArrayTest.java


tests:	ATest.class ByteArrayTest.class
	java -classpath '../junit.jar:..' junit.textui.TestRunner gotchas.ByteArrayTest


clean:	
	rm -rf *.class
