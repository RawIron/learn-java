
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


LevelsTest.class:	LevelsTest.java junit.jar mockito.jar
	javac -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" LevelsTest.java

tests:	LevelsTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" junit.textui.TestRunner gameCommons.balanceSettings.LevelsTest

LevelsTestMain:	LevelsTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" gameCommons.balanceSettings.LevelsTestMain


clean:	
	rm -f *.class
