package_home := $$HOME/workspace/learn-java
src_dir := ./src

jc := javac -Xlint:deprecation -classpath "$(package_home)"

RoadFrame.class:	$(src_dir)/RoadFrame.java
	$(jc) $(src_dir)/RoadFrame.java

clean:	
	rm -rf $(src_dir)/*.class
