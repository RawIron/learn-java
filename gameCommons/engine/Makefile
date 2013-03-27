
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:

Transaction.class:	
	javac -classpath "$(package_home)" Transaction.java

TransactionTest.class:	TransactionTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" TransactionTest.java

TransactionTest: TransactionTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gameCommons.engine.TransactionTest

clean:	
	rm -rf *.class
