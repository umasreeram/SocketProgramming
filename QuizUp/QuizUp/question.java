package application;

public class question {
  String question="";
  String options[] = new String[4];
  int ansno;
  String finalResult="";
  question(String qstr,String op1,String op2,String op3,String op4,int ans)
  {
	  question = qstr;
	  options[0] = op1;
	  options[1] = op2;
	  options[2] = op3;
	  options[3] = op4;
	  ansno = ans;
  }
}
