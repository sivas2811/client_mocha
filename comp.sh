pkill -9 java
git pull
javac DataNode.java
echo "Compile Done. Starting executable" 
java -cp ~/mysql-connector-java-5.1.27/mysql-connector-java-5.1.27-bin.jar:. DataNode
