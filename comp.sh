pkill -9 java
git pull
javac test_scalability.java 
echo "Compile Done. Starting executable" 
java test_scalability 1 $1 $2
