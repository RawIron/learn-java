package_home := $$HOME/workspace/learn-java
lib := $(package_home)/lib
src_dir := ./src
test_dir := ./src

jc := javac -Xlint:deprecation -classpath "$(package_home)"
jr := java -classpath "$(package_home)"

tc := javac -classpath "$(lib)/junit.jar:$(package_home)"
tr := java  -classpath "$(lib)/junit.jar:$(package_home)" junit.textui.TestRunner

junit.jar:

RoadSim:	$(src_dir)/RoadSim.java
	$(jc) $(src_dir)/RoadSim.java

run:
	$(jr) trafficSim.src.RoadSim

runway:
	_JAVA_AWT_WM_NONREPARENTING=1 $(jr) trafficSim.src.RoadSim

RoadSimTest.class:	${test_dir}/RoadSimTest.java ${src_dir}/Car.class junit.jar
	$(tc) ${test_dir}/RoadSimTest.java

RoadSimTest: RoadSimTest.class
	$(tr) trafficSim.src.RoadSimTest

tests: RoadSimTest

clean:	
	rm -rf $(src_dir)/*.class
	rm -rf $(test_dir)/*.class
