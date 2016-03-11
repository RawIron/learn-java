my_home := $$HOME/workspace_java
package_home := $(my_home)/scratch-java

junit.jar:
mockito.jar:


ATest.class:	ATest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" ATest.java
ATest: ATest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ATest


Vows.class:	Vows.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" Vows.java

# does not work
TheNullTest.class:	TheNullTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" TheNullTest.java
TheNullTest: TheNullTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.TheNullTest


Producer.class:	Producer.java
	javac -classpath "$(package_home)" Producer.java

ProductTest.class:	ProductTest.java Producer.class junit.jar mockito.jar
	javac -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" ProductTest.java
ProductTest: ProductTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProductTest

ProducerTest.class:	ProducerTest.java Producer.class junit.jar mockito.jar
	javac -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" ProducerTest.java
ProducerTest: ProducerTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProducerTest

ProducerFactoryMethodTest.class:	ProducerFactoryMethodTest.java Producer.class junit.jar mockito.jar
	javac -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" ProducerFactoryMethodTest.java
ProducerFactoryTest: ProducerFactoryMethodTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProducerFactoryMethodTest


ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" ByteArrayTest.java
ByteArray:	ByteArrayTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ByteArrayTest


FunctionTest.class:	FunctionTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" FunctionTest.java
FunctionTest: FunctionTest.class
	java -classpath "$(my_home)/junit.jar:$(my_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.FunctionTest


# does not compile
UseOfFinalTest.class:	UseOfFinalTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" UseOfFinalTest.java
UseOfFinal:	UseOfFinalTest.class
	java -classpath "$(my_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.UseOfFinalTest


IoStreamsTest.class:	IoStreamsTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" IoStreamsTest.java
IoStreams:	IoStreamsTest.class
	java -classpath "$(my_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.IoStreamsTest


RegexTest.class:	RegexTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" RegexTest.java
Regex:	RegexTest.class
	java -classpath "$(my_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.RegexTest


ExecTest.class:	ExecTest.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" ExecTest.java
Exec:	ExecTest.class
	java -classpath "$(my_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.ExecTest


Sha1Test.class:	Sha1Test.java junit.jar
	javac -classpath "$(my_home)/junit.jar:$(package_home)" Sha1Test.java
Sha1Test:	Sha1Test.class
	java -classpath "$(my_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.Sha1Test


tests:	ATest ByteArray ProducerTest ProducerFactoryTest ProductTest Regex FunctionTest Exec IoStreams Sha1Test


clean:	
	rm -rf *.class
