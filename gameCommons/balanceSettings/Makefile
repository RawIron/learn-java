
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:



DataItemProducer.class:	DataItemProducer.java
	javac -classpath "$(package_home)" DataItemProducer.java

Levels.class:	Levels.java
	javac -classpath "$(package_home)" Levels.java

Achievements.class:	Achievements.java
	javac -classpath "$(package_home)" Achievements.java

Producers.class:	Producers.java
	javac -classpath "$(package_home)" Producers.java

Product.class:	Product.java
	javac -classpath "$(package_home)" Product.java



DataLevelTest.class:	DataLevelTest.java junit.jar mockito.jar
	javac -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" DataLevelTest.java

LevelsTest.class:	LevelsTest.java junit.jar mockito.jar
	javac -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" LevelsTest.java


tests:	LevelsTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" junit.textui.TestRunner gameCommons.balanceSettings.LevelsTest

testDataLevel:	DataLevelTest.class
	java -classpath "$(package_home):$(package_home)/junit.jar:$(package_home)/mockito.jar" junit.textui.TestRunner gameCommons.balanceSettings.DataLevelTest


clean:
	rm -f *.class
