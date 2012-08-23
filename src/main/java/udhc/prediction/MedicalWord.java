package udhc.prediction;

import java.io.BufferedReader;
import java.io.File;
  import java.io.*;

public class MedicalWord
{
     public String[] getMedicalWord()
     {
     String[] medical1 = new String[80000];
     
      //String[] medical = null;
  int i = 0;
      try
     { 
      System.out.print("I am here");

      //URL url = getClass().getResource("./webapps/ROOT/WEB-INF/Database2.txt");
          //System.out.print("I am here2"+url.toString());
      File f1= new File("./webapps/ROOT/WEB-INF/Database2.txt");
      String filepath=f1.getAbsolutePath();
      System.out.print("Filepath"+filepath);
      BufferedReader br = new BufferedReader(new FileReader(filepath));
       i=0;
      String strLine;
      while ((strLine = br.readLine()) != null)
      {
        String[] temp = strLine.split(" ");
              for (int j = 0; j < temp.length; j++, i++)
              {
                 medical1[i] = temp[j];
              }
      }
   
       br.close();
    }
    catch (Exception e)
    {
      System.out.println("Error1: " + e);
    }
  String[] medical = new String[i];
    System.arraycopy(medical1,0,medical,0,i);
    return medical;
    }
}