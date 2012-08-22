package udhc.prediction;


import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Servlet implementation class for Servlet: Search
 *
 */
  public class Find extends HttpServlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#HttpServlet()
   */
     
  
  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/xml");
    PrintWriter out=null;
    try
    {
      //filewalk f= new filewalk();
        //f.walk("./webapps/ROOT/WEB-INF/");
      out= response.getWriter();

        String Text=request.getParameter("Text");
      MedicalWord m = new MedicalWord();
      String[] medical=m.getMedicalWord();
        predict prediction= new predict( Text,medical );
    String[] output=prediction.getResult();
    
      //String[] medical=prediction.getMedicalWord();
      //out.println("length: of medical word"+medical.length);
    parser p=new parser("http://open-archive.highwire.org/handler?verb=ListRecords&metadataPrefix=oai_dc&set=casereports","dc:description");
   out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><channel>");

    String[] parsed=p.startParsing();
    search s =  new search(medical);
    for(int i=0;i<parsed.length;i++)
    {
      out.print("<item>");
      out.print("<passage>");
      s.reset();
      s.StartSearch(parsed[i], output);
      out.println(parsed[i]);
      out.print("</passage>");
      out.print("<count>");
      out.println("Count:"+s.getCount());
      out.print("</count>");
      out.print("</item>");
    }
 out.write("</channel>");  

  }
    catch(Exception e)
    {
       out.print(e);
    }
    // TODO Auto-generated method stub
  }    
  
  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
  }             
}