
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:

DataLevelTest.class:	DataLevelTest.java junit.jar mockito.jar
	javac -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" DataLevelTest.java

LevelsTest.class:	LevelsTest.java junit.jar mockito.jar
	javac -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" LevelsTest.java


tests:	LevelsTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" junit.textui.TestRunner gameCommons.balanceSettings.LevelsTest

LevelsTestMain:	LevelsTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" gameCommons.balanceSettings.LevelsTestMain

testDataLevel:	DataLevelTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" junit.textui.TestRunner gameCommons.balanceSettings.DataLevelTest

clean:	
	rm -f *.class
