package udhc.prediction;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*; 
import java.io.*;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Servlet implementation class for Servlet: Search
 * It takes "Text" as get request and generates a XML file as a output.
 * in the portion "passage", the main content is written.
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
      
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
      //creating a new instance of a DOM to build a DOM tree.
      Document doc = docBuilder.newDocument();

      out= response.getWriter();

      String Text=request.getParameter("Text");
      MedicalWord m = new MedicalWord();
      String[] medical=m.getMedicalWord();
      predict prediction= new predict( Text,medical );
      String[] output=prediction.getResult();
    
      parser p=new parser("http://open-archive.highwire.org/handler?verb=ListRecords&metadataPrefix=oai_dc&set=casereports","dc:description");
   
      
      Element root = doc.createElement("Company");
      //adding a node after the last child node of the specified node.
      doc.appendChild(root);


    String[] parsed=p.startParsing();
    search s =  new search(medical);
    for(int i=0;i<parsed.length;i++)
    {
      if((i==33|| i == 34 ) || i==35)
      {
        continue;
      }
      if((i==57|| i == 58 ) || i==59)
      {
        continue;
      }
      if((i==87|| i == 88) || i==89)
      {
        continue;
      }
      s.reset();
      s.StartSearch(parsed[i], output);
      Element child = doc.createElement("itempassage");
      root.appendChild(child);
      Element child1 = doc.createElement("passage1");
      child.appendChild(child1);

      Text text = doc.createTextNode(parsed[i]);
      child1.appendChild(text);
      Element element = doc.createElement("iii");
      child.appendChild(element);

      Text text1 = doc.createTextNode(""+s.getCount());
      element.appendChild(text1);

    }
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    // create string from xml tree
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    DOMSource source = new DOMSource(doc);
    transformer.transform(source, result);
    String xmlString = sw.toString();
    out.write(xmlString);
 
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