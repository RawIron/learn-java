
ATest.class:	ATest.java junit.jar
	javac -classpath 'junit.jar:.' ATest.java

ByteArrayTest.class:	ByteArrayTest.java junit.jar
	javac -classpath 'junit.jar:.' ByteArrayTest.java


tests:	ATest.class ByteArrayTest.class
	java -classpath 'junit.jar:.' junit.textui.TestRunner ATest ByteArrayTest
