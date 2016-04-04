
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


Transaction.class:
	javac -classpath "$(package_home)" Transaction.java

TransactionTest.class:	TransactionTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" TransactionTest.java

TransactionTest: TransactionTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gamecommons.engine.TransactionTest


Board.class:
	javac -classpath "$(package_home)" Board.java

BoardTest.class:	BoardTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" BoardTest.java

BoardTest: BoardTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gamecommons.engine.BoardTest


clean:
	rm -rf *.class
