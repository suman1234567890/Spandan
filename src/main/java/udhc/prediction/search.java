package udhc.prediction;

public class search {
  private int count=0;
  predict p=null;
  String[] medical=null;
  public int StartSearch(String record,String[] source )
  {
    //predict p = new predict(record,medical);
    //String[] PredictedWord=p.getResult();
    String[] PredictedWord=record.split("[,\\s\\.\\?]"); 
    for(int i=0;i<PredictedWord.length;i++)
      
    {
      for(int j=0;j<source.length;j++)
      {
        if(PredictedWord[i].compareToIgnoreCase(source[j])==0)
        {
          count++;
          break;
        }
      }
    }
    return count;
  }
  public void reset()
  {
    count=0;
  }
  public int getCount()
  {
    return count;
  }
  public search(String[] medical)
  {
    this.medical = medical;
  }

}
