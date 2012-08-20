package healthcare.prediction;

import javax.xml.parsers.SAXParser;
import java.io.*;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import java.net.URLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import healthcare.prediction.words;

public class predictionBMJ extends HttpServlet
{
   PrintWriter out;

   int medicalwordlength = 0;

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
         String input=request.getParameter("Text");
         String[] args=input.split(" ");
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
             prediction(wrequest, response);
   }

   public void prediction(words[] w1,HttpServletRequest request, HttpServletResponse response)
   {
      try
      {
         out = response.getWriter();

         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();

         DefaultHandler handler = new DefaultHandler()
         {

            boolean bfname = false;

            boolean blname = false;

            boolean bnname = false;

            boolean bsalary = false;

            private StringBuilder acc = new StringBuilder();

            public void startElement(String uri, String localName, String qName, Attributes attributes)
               throws SAXException
            {

               //System.out.println("Start Element :" + qName);

               if (qName.equalsIgnoreCase("dc:description"))
               {
                  bfname = true;
               }

            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException
            {

               //System.out.println("End Element :" + qName);
               if (qName.equalsIgnoreCase("dc:description"))
               {
                  String s = acc.toString();
                  String[] args = s.split(" ");
                  String[] medicalwords;
                  words[] w = new words[args.length];
                  for (int i = 0; i < args.length; i++)
                  {
                     w[i] = new words();
                     w[i].setword(args[i]);
                     w[i].setstatus(false);
                  }

                  medicalwords = getMedicalWord();

                  predict(w, args, medicalwords);

                  out.println("\n\n\n\n\n\nOUTPUT(if medical word found then true other wise false)");
                  out.println("Tags are");
                  for (int i = 0; i < args.length; i++)
                  {
                     if (w[i].getstatus())
                     {
                        out.print(w[i].getWord() + ", ");
                     }
                  }
                  out.println("MatchWord");
                    for(int i = 0;i<w1.length();i++)
                      {
                           for(int j=0;j<args.length;j++)
                             {
                                 if((w[j].getWord()).compareTo(w1[i].getWord)==0 && w[i].getStatus()==true)
           {
          out.print(w[i].getWord()+" , ");
          }
                                 
                             }
                      }
                      

                  out.println("\n\n Passage : " + s); 
  
  bfname = false;
               }
               acc.setLength(0);

            }

            public void characters(char ch[], int start, int length) throws SAXException
            {

               acc.append(ch, start, length);

            }

         };

         String url = "http://open-archive.highwire.org/handler?verb=ListRecords&metadataPrefix=oai_dc&set=casereports";

         saxParser.parse(new InputSource(new URL(url).openStream()), handler);

      }
      catch (Exception e)
      {
         e.printStackTrace();
         out.println(e);
      }
   }

   public void predict(words[] w, String[] word, String[] medicalwords)
   {
      // out.println("Prediction Started: Word Length:"+word.length+" term length:"+medicalwordlength);

      for (int i = 0; i < medicalwordlength; i++)
      {
         for (int j = 0; j < word.length; j++)
         {

            if (medicalwords[i].toUpperCase().compareTo(word[j].toUpperCase()) == 0)

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
      String[] medical = new String[40000];

      int i = 0;
      try
      {

         String filename = "/WEB-INF/Database2.txt";
         ServletContext context = getServletContext();

         InputStream in = context.getResourceAsStream(filename);

         InputStreamReader isr = new InputStreamReader(in);
         BufferedReader br = new BufferedReader(isr);

         String strLine;

         while ((strLine = br.readLine()) != null)
         {

            String[] temp = strLine.split(" ");
            for (int j = 0; j < temp.length; j++, i++)
            {
               medical[i] = temp[j];
            }

         }

         in.close();
         medicalwordlength = i;
      }
      catch (Exception e)
      {
         out.println("Error: " + e.getMessage());
      }
      // out.println("GetMethodWord Ended: ");

      return medical;

   }

}
