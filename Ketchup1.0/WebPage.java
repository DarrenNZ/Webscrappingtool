
/**
 * Write a description of class WebPage here.
 * 
 * Purpose of WebPage class is to allow for the storage and retrieval of information for each website Ketchup wishes to retrieve informaiton from.
 * This includes a constructor to create the object/webpage, then getters ie getLink, getTittle and getContent.
 * @author (Darren Carroll) 
 * @version (16/10/2016)
 */
    
   public class WebPage{
    //fields 
    public String link = "";
    public String content = "";
    public String port = "";
    public String country = "";
    public String tittle = "";
    
    //Constructor
    public WebPage(String country, String port,String link, String content, String tittle){
        this.country = country;
        this.port = port;
        this.link = link;
        this.content = content;
        this.tittle = tittle;
    }
    
    //getter
    public String getLink(){
        return this.link;
    }
    
    //getter
     public String getTittle(){
        return this.tittle; 
    }
    
    //getter
    public String getContent(){
        return this.content; 
    }
    
}

