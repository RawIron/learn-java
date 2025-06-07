package_home := $$HOME/workspace/learn-java
src_dir := ./src

jc := javac -Xlint:deprecation -classpath "$(package_home)"
jr := java -classpath $(src_dir)

RoadFrame:	$(src_dir)/RoadFrame.java
	$(jc) $(src_dir)/RoadFrame.java

run:
	$(jr) RoadFrame

runway:
	_JAVA_AWT_WM_NONREPARENTING=1 $(jr) RoadFrame

clean:	
	rm -rf $(src_dir)/*.class
