package_home := $$HOME/workspace/learn-java
lib := $(package_home)/lib

jc := javac -classpath "$(lib)/junit.jar:$(lib)/mockito.jar:$(package_home)"
tr := java  -classpath "$(lib)/junit.jar:$(lib)/mockito.jar:$(package_home)" junit.textui.TestRunner

junit.jar:
mockito.jar:


ATest.class:	ATest.java junit.jar
	$(jc) ATest.java
ATest: ATest.class
	$(tr) gotchas.ATest


Vows.class:	Vows.java junit.jar
	$(jc) Vows.java

# does not work
TheNullTest.class:	TheNullTest.java junit.jar
	$(jc) TheNullTest.java
TheNullTest: TheNullTest.class
	$(tr) gotchas.TheNullTest


Producer.class:	Producer.java
	$(jc) Producer.java

ProductTest.class:	ProductTest.java Producer.class junit.jar mockito.jar
	$(jc) ProductTest.java
ProductTest: ProductTest.class
	$(tr) gotchas.ProductTest

ProducerTest.class:	ProducerTest.java Producer.class junit.jar mockito.jar
	$(jc) ProducerTest.java
ProducerTest: ProducerTest.class
	$(tr) gotchas.ProducerTest

ProducerFactoryMethodTest.class:	ProducerFactoryMethodTest.java Producer.class junit.jar mockito.jar
	$(jc) ProducerFactoryMethodTest.java
ProducerFactoryTest: ProducerFactoryMethodTest.class
	$(tr) gotchas.ProducerFactoryMethodTest


ByteArrayTest.class:	ByteArrayTest.java Vows.class junit.jar
	$(jc) ByteArrayTest.java
ByteArray:	ByteArrayTest.class
	$(tr) gotchas.ByteArrayTest


FunctionTest.class:	FunctionTest.java junit.jar
	$(jc) FunctionTest.java
FunctionTest: FunctionTest.class
	$(tr) gotchas.FunctionTest


# does not compile
UseOfFinalTest.class:	UseOfFinalTest.java junit.jar
	$(jc) UseOfFinalTest.java
UseOfFinal:	UseOfFinalTest.class
	$(tr) gotchas.UseOfFinalTest


IoStreamsTest.class:	IoStreamsTest.java junit.jar
	$(jc) IoStreamsTest.java
IoStreams:	IoStreamsTest.class
	$(tr) gotchas.IoStreamsTest


RegexTest.class:	RegexTest.java junit.jar
	$(jc) RegexTest.java
Regex:	RegexTest.class
	$(tr) gotchas.RegexTest


ExecTest.class:	ExecTest.java junit.jar
	$(jc) ExecTest.java
Exec:	ExecTest.class
	$(tr) gotchas.ExecTest


Sha1Test.class:	Sha1Test.java junit.jar
	$(jc) Sha1Test.java
Sha1Test:	Sha1Test.class
	$(tr) gotchas.Sha1Test


tests:	ATest ByteArray ProducerTest ProducerFactoryTest ProductTest Regex FunctionTest Exec IoStreams Sha1Test


clean:	
	rm -rf *.class
