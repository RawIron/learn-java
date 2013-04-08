
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


TransactionManagerTest.class:	TransactionManagerTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" TransactionManagerTest.java

tests:	TransactionManagerTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.TransactionManagerTest


clean:	
	rm -rf *.class
