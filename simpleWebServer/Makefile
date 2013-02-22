
cd ./workspace_java/simpleWebServer
rm -f *.class


cd ./workspace_java/simpleWebServer
javac -classpath './workspace_java/javaScratchpad/junit.jar:./workspace_java/javaScratchpad/javax.servlet.jar:./workspace_java/javaScratchpad/' -sourcepath ./workspace_java/javaScratchpad/ -Xlint Runner.java


cd ./workspace_java
java -classpath './workspace_java/javaScratchpad/junit.jar:./workspace_java/javaScratchpad/' junit.textui.TestRunner simpleWebServer.LoggerTest

cd ./workspace_java
java -classpath './workspace_java/javaScratchpad/junit.jar:./workspace_java/javaScratchpad/' simpleWebServer/Runner
