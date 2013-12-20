import java.io.IOException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
public class WorkerThread_Client implements Runnable {
     
    private String input;
    private Socket socket;
    private int count;
    private long start;
    //private Connection con;
     
    public WorkerThread_Client(String input, Socket socket, long start, int count)  {
        this.input=input;
	this.socket=socket;
	this.start = start; 
	this.count = count;
    }

  // s;tc;topic;content (success), s;up;user;password(success), s;ut;user;topic (success)
  // r;up;user;password(authenticated), r;tc;topic(content), r;ut;user(topic)
        
    @Override
    public void run() {
      //  System.out.println(Thread.currentThread().getName()+" Start. Command = "+input);
        try {
                        processCommand();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
 
    private void processCommand() throws UnknownHostException, IOException, SQLException {
	PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
  	out.println(this.input);	
//  	System.out.println(this.input);	
	BufferedReader in = new BufferedReader
		 (new InputStreamReader(this.socket.getInputStream()));
	String input = in.readLine();
	out.close();
	in.close();
	
	if(count % 5000 == 0) {
		long end = System.currentTimeMillis();
		long diff = end - start;
		System.out.println( "Time taken : " + diff); 
	}
	//socket.close();
    }
         
    @Override
    public String toString(){
        return this.input;
    }
}
