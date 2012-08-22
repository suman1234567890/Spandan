package udhc.prediction;

import java.io.*;
import java.io.File;
import java.net.URL;

public class predict {
  private int length=0;
  String[] output;
  String[] result;
  public  predict(String sentence,String[] MedicalWords)
  {
    String[] Words=sentence.split(" ");
    
    output= new String[Words.length];
    int k=0;
    for (int i = 0; i < Words.length; i++)
    {
          for (int j = 0; j < MedicalWords.length; j++)
           {
             if (MedicalWords[j].toUpperCase().compareTo(Words[i].toUpperCase()) == 0)
              {
                output[k]=MedicalWords[j];
                k++;
                break;
              }
           }
        }
    result=new String[k];
    System.arraycopy(output, 0, result, 0, k);
  }
  public String[] getResult()
  {
    return result;
  }
  
/**
 * @return the length
 */
public int getLength()
{
   return length;
}
/**
 * @param length the length to set
 */
public void setLength(int length)
{
   this.length = length;
}
/**
 * @return the output
 */
public String[] getOutput()
{
   return output;
}
/**
 * @param output the output to set
 */
public void setOutput(String[] output)
{
   this.output = output;
}
}
