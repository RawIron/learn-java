junit.jar:
mockito.jar:


Vows.class:	Vows.java junit.jar
	javac -classpath '../junit.jar:..' Vows.java


ATest.class:	ATest.java junit.jar
	javac -classpath '../junit.jar:..' ATest.java

ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	javac -classpath '../junit.jar:..' ByteArrayTest.java

TheNullTest.class:	TheNullTest.java junit.jar
	javac -classpath '../junit.jar:..' TheNullTest.java

MockTest.class:	MockTest.java junit.jar mockito.jar
	javac -classpath '../junit.jar:../mockito.jar:..' MockTest.java


tests:	ATest.class ByteArrayTest.class
	java -classpath '../junit.jar:../mockito.jar:..' junit.textui.TestRunner gotchas.ByteArrayTest


clean:	
	rm -rf *.class
