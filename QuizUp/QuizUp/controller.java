package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.RadioButton;
import java.util.*;


public class controller {
	@FXML private Label message,message1,score1,portdis,optionslabel;
	@FXML private Slider move;
	@FXML private RadioButton rb1,rb2,rb3,rb4;
	@FXML private Button submit;
	
	String op[] = new String[4];
	String question;
	String questionClient;
	String result;
	question q;
	int score;
	int noQdone = 0;
	boolean firsttime = true;
	String ansno;
	questionBank qb;
	ChatClient client;
	@FXML ToggleGroup tgroup;
    public void initialize() {
		
    	rb1.setToggleGroup(tgroup);
    	rb2.setToggleGroup(tgroup);
    	rb3.setToggleGroup(tgroup);
    	rb4.setToggleGroup(tgroup);
    	rb1.setSelected(false);
    	rb2.setSelected(false);
    	rb3.setSelected(false);
    	rb4.setSelected(false);
    	message1.setText("");
    	client = new ChatClient("localhost",Main.portno);
    	Integer port = new Integer(Main.portno);
    	portdis.setText("Connected on port: "+ port.toString());
    	System.out.println("Connected on port " + Main.portno);
    	qb = new questionBank();
        
        qb.makeQuestion();
        q = qb.getQuestion(client);
		setQuestion(q);
		//questionClient = client.getquestion();
        //message1.setText(questionClient);
		//quizTimeline();
    }
	
	public void generateRandom(ActionEvent event )
	{
		Random r = new Random();
		int no = r.nextInt(50) + 1;
		message.setText(Integer.toString(no));
	}
	
	public void quizTimeline ()
	{
		final Rectangle rectBasicTimeline = new Rectangle(100, 50, 100, 50);
		rectBasicTimeline.setFill(Color.RED);
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(), 300);
		final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
	
	public void setQuestion(question q)
	{
		question = q.question;
		op[0] = q.options[0];
		op[1] = q.options[1];
		op[2] = q.options[2];
		op[3] = q.options[3]; 
		message.setText("Question: "+question);	
		rb1.setText(op[0]);
		rb2.setText(op[1]);
		rb3.setText(op[2]);
		rb4.setText(op[3]);
	}
	public void quizClient(ActionEvent event)
	{
		   if(rb1.isSelected())
			   ansno = "1";
		   else if(rb2.isSelected())
			   ansno = "2";
		   else if(rb3.isSelected())
			   ansno = "3";
		   else if(rb4.isSelected())
			   ansno = "4";
		   else 
			   message1.setText("Please select an answer");
		   //noQdone++;
		   //int i,j; 
		   wait1();
		   result = client.checkAnswer(ansno);
		   score = ChatClient.score;
		   message1.setText(result);
		   Integer scorestr = new Integer(score);
		   Integer correctstr = new Integer (ChatClient.correctanswercount);
		   Integer wrongstr = new Integer (ChatClient.wronganswercount);
		   score1.setText("Correct: " + correctstr.toString() + ", Wrong: " + wrongstr.toString() + ", Score: "+ scorestr.toString());
		     
		   q = qb.getQuestion(client);
		   if(q.finalResult.equalsIgnoreCase(""))
		   {
			   setQuestion(q);
			   //wait1();
			   //wait1();
			   //message1.setText("");

		   }
		   else
		   {
			   message1.setText(q.finalResult);
			   wait1();
			   message.setVisible(false);
			   rb1.setVisible(false);
			   rb2.setVisible(false);
			   rb3.setVisible(false);
			   rb4.setVisible(false);
			   submit.setVisible(false);
			   message.setVisible(false);
			   optionslabel.setVisible(false);
			   client.stop();
		   }
		   
		    
		   rb1.setSelected(false);
	   	   rb2.setSelected(false);
	   	   rb3.setSelected(false);
	   	   rb4.setSelected(false);

	}
	public void quiz(ActionEvent event)
	{
	   if((rb1.isSelected()) && (op[q.ansno].equals(rb1.getText())))
		   message1.setText("Last Answer: Correct");
	   else if((rb2.isSelected()) && (op[q.ansno].equals(rb2.getText())))
		   message1.setText("Last Answer: Correct");
	   else if((rb3.isSelected()) && (op[q.ansno].equals(rb3.getText())))
		   message1.setText("Last Answer: Correct");
	   else if((rb4.isSelected()) && (op[q.ansno].equals(rb4.getText())))
		   message1.setText("Last Answer: Correct");
	   else 
		   message1.setText("Last Answer: Wrong");
	   noQdone++;
	   int i,j; 
	   for(i=0;i<50000;i++)
		   for(j=0;j<50000;j++)
			   ;
	   if(noQdone < qb.totq) 
	   {//qb = new questionBank();
		   q = qb.getQuestion(client);
		   setQuestion(q);
	   } 
	   rb1.setSelected(false);
   	   rb2.setSelected(false);
   	   rb3.setSelected(false);
   	   rb4.setSelected(false);
	}
	void wait1()
	{
		int i,j;
		for(i=0;i<10000;i++)
			   for(j=0;j<10000;j++)
		   		   ;
	}
}
