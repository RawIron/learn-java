
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


TransactionManagerTest.class:	TransactionManagerTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" TransactionManagerTest.java

testTM:	TransactionManagerTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.TransactionManagerTest


ResourceManagerTest.class:	ResourceManagerTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" ResourceManagerTest.java

testRM:	ResourceManagerTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.ResourceManagerTest


ResourceTest.class:	ResourceTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" ResourceTest.java

testR:	ResourceTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.ResourceTest


LockManagerTest.class:	LockManagerTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" LockManagerTest.java

testLockM:	LockManagerTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.LockManagerTest


LogManagerTest.class:	LogManagerTest.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" LogManagerTest.java

testLogM:	LogManagerTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner distributedTransactions.LogManagerTest


tests:	testTM testRM testLockM testLogM testR


clean:	
	rm -rf *.class
