
my_home := $$HOME/workspace_java
package_home := $(my_home)/javaScratchpad

junit.jar:
mockito.jar:


Vows.class:	Vows.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" Vows.java


Producer.class:	Producer.java
	javac -classpath "$(package_home)" Producer.java
Product.class:	Producer.java
	javac -classpath "$(package_home)" Producer.java

ATest.class:	ATest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" ATest.java

FunctionTest.class:	FunctionTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" FunctionTest.java

ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" ByteArrayTest.java

TheNullTest.class:	TheNullTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" TheNullTest.java

ProducerTest.class:	ProducerTest.java Producer.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" ProducerTest.java
ProducerFactoryMethodTest.class:	ProducerFactoryMethodTest.java Producer.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" ProducerFactoryMethodTest.java
ProductTest.class:	ProductTest.java Producer.java junit.jar mockito.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" ProductTest.java

tests:	ATest.class ByteArrayTest.class ProducerTest.class ProducerFactoryMethodTest.class ProductTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ATest
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ByteArrayTest
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProducerTest
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProductTest

testProducerFactory: ProducerFactoryMethodTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.ProducerFactoryMethodTest

FunctionTest: FunctionTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)/mockito.jar:$(package_home)" junit.textui.TestRunner gotchas.FunctionTest


UseOfFinalTest.class:	UseOfFinalTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" UseOfFinalTest.java

UseOfFinal:	UseOfFinalTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.UseOfFinalTest

IoStreamsTest.class:	IoStreamsTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" IoStreamsTest.java
IoStreams:	IoStreamsTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.IoStreamsTest

RegexTest.class:	RegexTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" RegexTest.java
Regex:	RegexTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.RegexTest

ExecTest.class:	ExecTest.java junit.jar
	javac -classpath "$(package_home)/junit.jar:$(package_home)" ExecTest.java
Exec:	ExecTest.class
	java -classpath "$(package_home)/junit.jar:$(package_home)" junit.textui.TestRunner gotchas.ExecTest


clean:	
	rm -rf *.class
