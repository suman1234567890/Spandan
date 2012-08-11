/// The class Helps to break a setence into words.
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