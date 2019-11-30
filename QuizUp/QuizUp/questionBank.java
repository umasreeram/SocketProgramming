package application;

public class questionBank {
	question q[];
	static int currqno = 0;
	int curr;
	int totq = 3;
	String qnop;
	public void makeQuestion()
	{
		
		q = new question[5];
		q[0] = new question("Entomology is the science that studies","Behaviour of human beings","Insects","Origin of scientific terms","Formation of rocks",1);
		q[1] = new question("Java language was developed by which company","Microsoft","IBM","Apple","Sun Microsystem",3);
		q[2] = new question("First China war was fought between","China and Britain","China and France","China and Egypt","China and Greek",0);
	}
	public question makeQuestionClient(String qnop)
	{
		int i=0,j=0,begin;
		i = qnop.indexOf('\n');
		if(i != -1)
			q[0].question = qnop.substring(0,i);
		else 
		{
			q[0].finalResult = qnop;
			return q[0];
		}
		System.out.println(q[0].question);
		begin = i+1;
		i = i+1;
		while((i=qnop.indexOf('\n',i)) > -1)
		{
			System.out.println(i);
			q[0].options[j] = qnop.substring(begin,i);
			System.out.println(q[0].options[j]);
			j++;
			i++;
			begin = i;
		}
	    return q[0];
	}
	question getQuestion()
	{ 
		if(currqno == 3)
		{
			return  null;
		}
		else
		{
			curr = currqno;
			currqno++;
			return q[curr];
		}
	}
	
	question getQuestion(ChatClient client)
	{
	    qnop = client.getquestion();
	    System.out.println("Question from client: "+qnop);
	    return makeQuestionClient(qnop);
	}
}
