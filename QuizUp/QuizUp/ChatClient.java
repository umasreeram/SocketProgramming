package application;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient

{   private Socket socket              = null;
    private BufferedReader  console   = null;
    private DataOutputStream streamOut = null;
	private DataInputStream  streamIn  =  null;
	public static int score=0;
	public static int correctanswercount = 0;
	public static int wronganswercount = 0;
    String line = "",qnop="",result="",end="";
	
public ChatClient(String serverName, int serverPort)
   {
		System.out.println("Establishing connection. Please wait ...");
	      try
	      {  socket = new Socket(serverName, serverPort);
	         System.out.println("Connected: " + socket);
	         start();
	      }
	      catch(UnknownHostException uhe)
	      {  System.out.println("Host unknown: " + uhe.getMessage());
	      }
	      catch(IOException ioe)
	      {  System.out.println("Unexpected exception: " + ioe.getMessage());
	      }

	         


   }
   public void start() throws IOException
   {
	   console = new BufferedReader(new InputStreamReader(System.in));
	   streamOut = new DataOutputStream(socket.getOutputStream());
       streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
   }
   
   public void stop()
   {  try
      {  if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing ...");
      }
   }
   
   public String getquestion ()
   {
	   try {
	    	  ///getting q from server
  	          qnop = streamIn.readUTF();
	    	  if(qnop.equals("send score"))
	    	  {
	    		  streamOut.writeUTF(score+"");	         
		          streamOut.flush();
		          qnop = streamIn.readUTF();
		          return qnop;
		 	  
	    		  
	    	  }
	    	}


         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	         }
         
       
   		return qnop;
   }
   
   public void stopchat ()
   {
	    stop();
	   
   }
   /*public static void main(String args[])
   {
	  String str;
	  ChatClient client = null;
      client = new ChatClient("localhost", 3001);     
      str = client.getquestion ();
      client.stopchat ();
   }*/
   public String checkAnswer(String ans)
   {
	   try
	   {
		   streamOut.writeUTF(ans);
	       
	       streamOut.flush();
	
	     //checking is myans is true or false
	 	  result = streamIn.readUTF();
	 	  if(result.equals("true"))
	 	  {
	 		  score+=5;
	 		  correctanswercount++;
	 		  return "Last answer: Correct!!";
	 		  
	 	  }
	 	  else
	 	  {
	 		  wronganswercount++;
	 		  return "Last answer: Wrong!!";
	 	  }
	 	 //System.out.println("Current Score:"+score);

	   }
 	   

     catch(IOException ioe)
      {  System.out.println("Sending error: " + ioe.getMessage());
      	return "";
      }
     
   }  
}