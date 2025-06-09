package_home := $$HOME/workspace/learn-java
src_dir := ./src

jc := javac -Xlint:deprecation -classpath "$(package_home)"
jr := java -classpath $(src_dir)

RoadSim:	$(src_dir)/RoadSim.java
	$(jc) $(src_dir)/RoadSim.java

run:
	$(jr) RoadSim

runway:
	_JAVA_AWT_WM_NONREPARENTING=1 $(jr) RoadSim

clean:	
	rm -rf $(src_dir)/*.class
