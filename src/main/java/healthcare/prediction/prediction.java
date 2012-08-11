package healthcare.prediction;
  import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import healthcare.prediction.words;
public class prediction extends HttpServlet {
  int medicalwordlength=0;
    PrintWriter out;
  public void doGet(HttpServletRequest request,HttpServletResponse response)
      throws ServletException, IOException {
      
    String s= request.getParameter("sentence");
  
    
    out = response.getWriter();
      out.println(s);
    // Use "out" to send content to browser
        String[] args=s.split(" ");
        String[] medicalwords;
        words[] w=new words[args.length];
        for(int i=0;i<args.length;i++)
        {
              w[i]=new words();
              w[i].setword(args[i]);
              w[i].setstatus(false);
        }
    
    
        medicalwords=getMedicalWord();


        predict(w,args,medicalwords);

        out.println("OUTPUT(if medical word found then true other wise false)");


        for(int i=0;i<args.length;i++)
        {
          out.println(w[i].getWord()+"       status        ----- >"+w[i].getstatus());
        }


    }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response)
      throws ServletException, IOException {
      
       doGet(request,response);
    
    
    // Use "out" to send content to browser
  }
    public void predict(words[] w,String[] word,String[] medicalwords)
  {
   // out.println("Prediction Started: Word Length:"+word.length+" term length:"+medicalwordlength);

    for(int i=0;i<medicalwordlength;i++)
    {
      for(int j=0;j<word.length;j++)
      {
        
        
        if(medicalwords[i].toUpperCase().compareTo(word[j].toUpperCase())==0)

        {
          w[j].setstatus(true);
          
          //out.println(word[j]+"Matched");
        
          
        }
      
      }


    }

     
  }

  public String[] getMedicalWord()
  {

    //out.println("GetMethodWord Started: ");
    //String medical[]= new String(20000) ;
    String[] medical=new String[40000];
    
    int i=0;
    try
    {
        
         String filename = "/WEB-INF/Database2.txt";
  ServletContext context = getServletContext();
  
  InputStream in = context.getResourceAsStream(filename);

  InputStreamReader isr = new InputStreamReader(in);
  BufferedReader br= new BufferedReader(isr);

      String strLine;
      
      while ((strLine = br.readLine()) != null)   {
    
          //System.out.println (strLine);
        String[] temp=strLine.split(" ");
        for(int j=0;j<temp.length;j++,i++)
        {
          medical[i]=temp[j];
        }
        
      }
    
      in.close();
      medicalwordlength=i;
    }
    catch (Exception e)
    {
      out.println("Error: " + e.getMessage());
      }
   // out.println("GetMethodWord Ended: ");

    return medical;
  
    
  }

}
    
class words
{
  boolean stat;
  String Word=new String();
  public void setword(String InputWord)
  {
    Word=InputWord;
  }
  public void setstatus(boolean i)
  {
    stat=i;    
  }
  public boolean getstatus()
  {
    return stat;
    
  }
  public String getWord()
  {
    return Word;
  }
}