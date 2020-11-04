// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  public boolean closed = false;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	if (msg.toString().contains("#login")) {
		
		client.setInfo(client.toString(), Integer.parseInt(msg.toString().substring(7)));
		
	}
	else {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(client.getInfo(client.toString()).toString() + ": " + msg);}
  }
  
  public void handleMessageFromServerClient
  (String message) {
	  if(message.charAt(11) == '#') {
		  
			switch(message.substring(12,16)) {
				case "quit":
					try {
						close();
						}
						catch(IOException e) {}
					System.exit(0);
					break;
				case "stop":
					stopListening();
					break;
				case "clos":
					try {
					close();
					}
					catch(IOException e) {}
					break;
				case "setp":
					if(closed) {System.out.println("Server is not closed, please try again");}
					else {
						setPort(Integer.parseInt(message.substring(20)));}
					break;
				case "star":
					if(!isListening()) {
						try{
							listen();
						}
						catch (IOException e) {	}
					}
					else {
						System.out.println("Server is already listening!");
					}
					break;	
				case "getp":
					System.out.println(String.valueOf(getPort()));
					break;
				default:
					System.out.println("unable to read command");
					break;
				}
	  		}
	  else {
			System.out.println(message);
			this.sendToAllClients(message);
	  }
  
}
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    closed = false;
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  protected void clientConnected(ConnectionToClient client) {System.out.println("Client has connected");}

  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client) {System.out.println("Client has disconnected");}

  protected void connectionClosed() {
		System.out.println("Connection has been closed");
		closed = true;
	}
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
