
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable
{ 	
	private Socket       socket = null;
	private ServerSocket server = null;
	private Thread       thread = null;
	private DataOutputStream streamOut = null;
	private DataInputStream  streamIn  =  null;
	public static boolean answered[] = new boolean[2];

  public static boolean scoresent[] = new boolean[2];
	private int port_number;

  public static int score[] = new int[2];
	private static srvquestion q0;
	private static srvquestion q1;
	private static srvquestion q2;
	private static srvquestion q3;
	private static srvquestion q4;
	public static srvquestion[] q=new srvquestion[5];
	private static int i=0,flag=0;
	
	public ChatServer(int port)
    {  
		try
		{  	
			port_number=port;
			System.out.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);  
			System.out.println("Server started: " + server);
			answered[0]=false;
			answered[1]=false;			
			start();
      }
      catch(IOException ioe)
      {  
    	  System.out.println(ioe); 
      }
   }
   public void run()
   {
	   String str;
	   while (thread != null)
	   {   try
	   		{
    	  
    	    System.out.println("Waiting for a client ..."); 
            socket = server.accept();
            System.out.println("Client accepted: " + socket);
    
            open();
            boolean done = false;
         
            while (i!=5)
            {  try
               {
            			
            	//Get Question and options          	
            	str = q[i].qandop();
           
            	//Broadcast it
            	streamOut.writeUTF(str);
            	
            	  //receive ans
            	  String line = streamIn.readUTF();
                  System.out.println(line);  
                  
                  //broadcast correct or not
                  if(check(line,q[i]))
                  {
                	  streamOut.writeUTF("true");              	  
                  }                
                  else
                  {
                	  streamOut.writeUTF("false");
                  }
                  
                  if(this.port_number==3000)
                  {
                	  System.out.println("3000 answered");
                	  answered[0]=true;
                	  while(!answered[1])
                	  {
                		  try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	  
                	  }   
                  }
                  else if(this.port_number==3001)
                  {
                	  System.out.println("3001 answered");
                	  answered[1]=true;
                	  
                	  while(!answered[0])
                	  {
                		  try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	  
                	  }
                  }

                  if(answered[0] && answered[1])
                  {
                	  if(flag==0)
                	  {
                    	  i++;
                    	  flag=1;
                	  }
                	  else
                	  {
                		  flag=0;
                	  }
                	  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	  answered[0]=false;
                	  answered[1]=false;
                 }
                  
                  try {
  					Thread.sleep(100);
  				} 
                  catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}   
               }
               catch(IOException ioe)
               {  done = true;  }
            }
            
            //end of while loop
            
       	  //String line = streamIn.readUTF();
          //System.out.println(line); 




         //asking for score

          streamOut.writeUTF("send score");
            
          String sc= streamIn.readUTF();
          System.out.println(sc);  
          if(this.port_number==3000)
            {score[0]=Integer.parseInt(sc);
              scoresent[0]=true;
            }
          else
            {score[1]=Integer.parseInt(sc);
              scoresent[1]=true;
            }


                  if(this.port_number==3000 && scoresent[0]==true)
                  {
                    
                    
                    while(!scoresent[1])
                    {
                      try {
              Thread.sleep(10);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }   
                    }   
                  }
                  else if(this.port_number==3001 && scoresent[1]==true)
                  {
                
                    
                    
                    while(!scoresent[0])
                    {
                      try {
              Thread.sleep(10);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }   
                    }
                  }


          if(score[1]>score[0])
            streamOut.writeUTF("Winner is 3001 and Loser is 3000");
          else
            streamOut.writeUTF("Winner is 3000 and Loser is 3001");


          streamOut.writeUTF("bye"); 
            close();
            break;
 

            
    
         }
	   
	   	
         catch(IOException ie)
         {  System.out.println("Acceptance Error: " + ie);  }
      }
	   
	   
   }
   public void start()
   {  if (thread == null)
      {  thread = new Thread(this);
       q0 = new srvquestion("Who is the president of India?","Pranab Mukherjee","Donald Trump","Barack Obama","Manmohan Singh",1);
	   q1 = new srvquestion("Who is the PM of India?","Pranab Mukherjee","Donald Trump","Narendra Modi","Manmohan Singh",3);
	   q2 = new srvquestion("Who was in the movie NH10?","Alia Bhatt","Aishwariya Rai","kareena Kapoor","Anushka Sharma",4);
	   q3 = new srvquestion("Who bulit the Taj Mahal?","Shah Jahan","Akbar","Jahangir","Aurangzeb",1);
	   q4 = new srvquestion("Who is the founder of Reliance Jio?","Bill Gates","Mukesh Ambani","Anil Ambani","Vijay Mallya",2);
		  
	   q[0]=q0;
	   q[1]=q1;
	   q[2]=q2;
	   q[3]=q3;
	   q[4]=q4;
		 
      	thread.start();
      }
   }
	public boolean check(String n,srvquestion obj)
	{
		int num=Integer.parseInt(n);
		if(num==obj.ans)
			return true;
		else 
			return false;
			
	}
   public void open() throws IOException
   { 	streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
   		streamOut = new DataOutputStream(socket.getOutputStream());   
   
   }
   
   
   
   public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (streamIn != null)  streamIn.close();
   }
   public static void main(String args[])
   {
	   
	   ChatServer server1 = null;
   	   ChatServer server2 = null;
   
     // if (args.length != 1)
   //      System.out.println("Usage: java ChatServer port");
   //   else
         server1 = new ChatServer(3000);
         server2 = new ChatServer(3001); 
   }
}

class srvquestion{
	
	String quest,op1,op2,op3,op4;
	int ans;
	
	srvquestion(String quest, String op1, String op2, String op3, String op4, int ans)
	{
		this.quest = quest;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.op4 = op4;
		this.ans=ans;
	}
	
	public String getquestion()
	{
		return quest;		
	}
	public String qandop()
	{
		return quest+"\n"+op1+"\n"+op2+"\n"+op3+"\n"+op4+"\n";
	}
	
}