package udhc.prediction;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import java.net.URL;
public class parser {
	private String url;
	private String div;
	public parser(String url,String div)
	{
		this.url=url;
		this.div=div;
	}
	public String[] startParsing()
	{
		String[] result=null;
		try
	      {
			 
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         Handler handler = new Handler(div);
	         saxParser.parse(new InputSource(new URL(url).openStream()), handler);
	         result= new String[handler.length()];
	         
	         System.arraycopy(handler.getPassage(), 0, result, 0, handler.length());
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	      return result;
	}
}
class Handler extends DefaultHandler
{
	boolean bfname = false;
    public int number=0;
	String[] passages= new String[200];
    private StringBuilder acc=new StringBuilder();
    String div="";
    public Handler(String div)
    {
    	this.div=div;
    }
    public int length()
    {
    	return number;
    }
    public String[] getPassage()
    {
    	return passages;
    
    }
    public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException
    {
       if (qName.equalsIgnoreCase(div))
       {
          bfname = true;
       }
    }
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
       if (qName.equalsIgnoreCase(div))
       {
          String s = acc.toString();
          passages[number]=s;
          number++;
          bfname = false;
       }
         acc.setLength(0);
    }
    public void characters(char ch[], int start, int length) throws SAXException
    {
       acc.append(ch,start,length);
    }
}