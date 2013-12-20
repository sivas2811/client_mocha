import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class test_scalability {

    /**
     * Runs the server.
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
    	String serverAddress = "54.226.150.230";
    	String str[] = { "r;ut;", "r;tc;" ,"s;ut;", "s;tc;"}; 
    	Calendar inst = Calendar.getInstance();
	int numberOfWords = 1000; 
	int num_runs = Integer.parseInt(args[0]); //1;
	int numReq = Integer.parseInt(args[1]); //100;
	int numThreads = Integer.parseInt(args[2]); //50
	String input = null;
	
	String[] random_str = new String[numberOfWords];
	for(int a=0; a<numberOfWords; ++a) {
		Random random = new Random();
		char[] word = new char[random.nextInt(8)+3]; 
        	for(int j = 0; j < word.length; j++)
	        {
	            if(j%2 != 0) {word[j] = (char)('a' + random.nextInt(26));}
	            else {word[j] = (char)('z' - random.nextInt(26));}

	        }
	        random_str[a] = new String(word);
  	}

	//r:ut - 1;
	//s:ut, r:tc - 2
	//s;tc; - 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
/*                       (numThreads, new ThreadFactory(){
                                public Thread newThread(Runnable runnable){
                                        Thread thread = new ThreadFactory().newThread(runnable);
                                        return thread;
                                }
    		        });*/
 	

	long millis = System.currentTimeMillis();
	for(int i=1; i<=num_runs; i++)
	{
		Random rand = new Random();
	    	for(int j=0; j<(numReq*i);j++) {
			int k=rand.nextInt(numberOfWords);
			int ja = rand.nextInt(3);
			Socket s = new Socket(serverAddress, 9090);
			/*if(ja%3==0) {
	    			input = str[0]+random_str[k]+k;//+i+j+str[1]);
			}
			else if( ja%3 ==1) {
	    			input = str[(j%2)+1] + random_str[k]+ k+ ";"+random_str[(k+j)%7]+i+j; 
			}
			else if( ja%3 ==2) {*/
	    			input = str[3] + random_str[k]+ k+ ";"+random_str[(k+j)%7]+i+j+";"+random_str[(i+j)%7]+k; 
			//}
	     	        Runnable worker = new WorkerThread_Client(input, s, millis, j);
	                executor.execute(worker);
    		}
//		long mill= System.currentTimeMillis();
//		System.out.println("No of requests: "+ numReq*i+ "Time taken" + Long.toString( mill-millis));
         }
}}
