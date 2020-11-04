// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI, int loginid) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {	
	
	clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {	  
	  //System.out.println(message.substring(0,6));
	  if(message.charAt(2) == '#') {
		switch(message.substring(3,7)) {
			case "quit":
				quit();
				break;
			case "logo":
				try {
					closeConnection();
					}
					catch(IOException e){
					};
				break;
			case "seth":
				if(isConnected()) {clientUI.display("Client is still logged in, log off and try again");}
				else if(message.substring(2,10).equals("#sethost ")){setHost(message.substring(11));}
				else {clientUI.display("unable to read command");}
				break;
			case "setp":
				if(isConnected()) {clientUI.display("Client is still logged in, log off and try again");}
				else if(message.substring(2,10).equals("#setport")){
					clientUI.display(message.substring(11));
					setPort(Integer.parseInt(message.substring(11)));
				}
				else {clientUI.display("unable to read command");}
				break;
			case "logi":
				try {
				openConnection();
				}
				catch(IOException e){
				};
				break;
			case "geth":
				clientUI.display(getHost());
				break;
			case "getp":
				clientUI.display(String.valueOf(getPort()));
				break;
			default:
				clientUI.display("unable to read command");
				break;
			}
	}
	  if (message.charAt(0) == '#') {
		  if (message.substring(0,6).equals("#login")) {
			  try
			    {
			      sendToServer(message);
			    }
			    catch(IOException e)
			    {}
		  }
	  }
	   
  	else {
	    try
	    {
	      sendToServer(message);
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  
  // Class methods
  
  /**
	 * Hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
	protected void connectionClosed() {
		System.out.println("Connection has been closed");
	}
  
  /**
	 * Hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
	protected void connectionException(Exception exception) {
		quit();
	}

}
//End of ChatClient class
