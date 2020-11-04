import java.io.*;
import java.util.Scanner;

import client.*;
import common.*;

public class ServerConsole implements ChatIF {
	  EchoServer server;
	  
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromConsole; 
	  
	  
	  final public static int DEFAULT_PORT = 5555;

	  
	public ServerConsole(int port) {
	     server = new EchoServer(port);
	     try 
	     {
	       server.listen(); //Start listening for connections
	     } 
	     catch (Exception ex) 
	     {
	       System.out.println("ERROR - Could not listen for clients!");
	     }
	     
	    // Create scanner object to read from console
	    fromConsole = new Scanner(System.in);
	}
	
	  //Instance methods ************************************************
	  
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerClient("SERVER MSG>" + message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }

	
	public void display(String message) {
		System.out.println(message);
	}
	


	
	
	public static void main(String[] args) 
	  {
		int port = DEFAULT_PORT;
		
	    try
	    {
	      port = Integer.parseInt(args[1]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      port = DEFAULT_PORT;
	    }
	    ServerConsole serverchat = new ServerConsole(port);
	    serverchat.accept();  //Wait for console data
	  }
}

