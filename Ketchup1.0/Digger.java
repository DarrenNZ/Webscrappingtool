
import ecs100.*;
import com.jaunt.*;
import org.jsoup.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

/** Write a description of class WebPage here.
 * 
 * The puropse of this class is to conduct recursive searches on user defined paramaters. The digger method does the initial creation of a predefined object with built in web searching methods. 
 * It then conduct multiple google searches using the string of arguments from the user, it then scans the html for links and stores it in a array called elements, the for each method cycles through 
 * the elements calling on the recursive diglet method.
 * @author (Darren Carroll) 
 * @version (16/10/2016)
 * 
 */


public class Digger
{
    //fields
    //asks user for information they wish to search on, then builds string or arguments
    String searchName = UI.askString("Please give me persons full name?");
    String searchJob = UI.askString("please provide persons occupation or tittle?");
    String searchCountry = UI.askString("What country do they reside?");
    String searchKey = UI.askString("Do you have any other key words?");
    public String searchAll = "\"" + searchName +"\" " + searchJob + " " + searchCountry + " " + searchKey;

    public String url = "https://www.google.com";
    public int average = 1;
    public int biggestResult;
    public int n = 5;//UI.askInt("Plase give me a number: 2 being a small search, 10 being big!");
    
    //constructor
    public Digger(){
        UI.addButton("Dig", this::digger);
    }

    public void digger(){
        //try catch is triggered fs the client has no internet connection or google.com is not responding.
        try{
            //goes to google and searches for user input and gets all the links form the page and puts thim into an element links
            UserAgent userAgent = new UserAgent();
            
            //google search
            userAgent.visit(this.url);
            userAgent.doc.apply(this.searchAll);            
            userAgent.doc.submit("Google Search");
            Elements linksOne = userAgent.doc.findEvery("<h3 class=r>").findEvery("<a>"); 

            //google news search            
            userAgent.visit(this.url);
            userAgent.doc.apply(this.searchAll + " bbc " + "cnn " + "news" + "fox");             
            userAgent.doc.submit("Google Search"); 
            Elements linksTwo = userAgent.doc.findEvery("<h3 class=r>").findEvery("<a>"); 

            //goes through the elements array calling recursive diglet method on the link
            for(Element link : linksOne){
                url = link.getAt("href");
                UI.println(url);
                this.digletMethod(url, n-1);
            }
            
            //goes through the elements array calling recursive diglet method on the link
            for(Element link : linksTwo){
                url = link.getAt("href");
                UI.println(url);
                this.digletMethod(url, n-1);
            }
        }
        catch(Exception e){
            UI.println("Google Search: someting went wrong..");
        }

    }

    //need to do two things 
    //1: see if there is more then average darren carroll in body text 
    //2: need to get all the links in the html
    public void digletMethod(String url, int n){
        if (n>0){
            //declares new web scraping object and array to store links.
            UserAgent userAgent; 
            Elements newLinks;
            try{
                //initialize web scraping object and array
                userAgent = new UserAgent();
                userAgent.visit(url);
                newLinks = userAgent.doc.findEvery("<a>");
                
                //goes through all of the links found
                for (Element link : newLinks){
                    //visits the url
                    url = link.getAt("href");    
                    UserAgent User = new UserAgent();
                    User.visit(url);
                    
                    //grabs the body of html and cleans it.
                    Element body = userAgent.doc.findFirst("body");
                    String outPut = body.innerText();
                    outPut = this.html2text(outPut);
                    
                    //if the count of words in the webpage match that of the search string then method is recalled on links found on that page
                    if (StringUtils.countMatches(outPut, this.searchName) > this.average){
                        
                        this.digletMethod(url, this.n-1);
                        UI.println(StringUtils.countMatches(outPut, this.searchName) + "Matches on webpage with search");
                    }
                    UI.println("the count was not high enough for" + url);
                }

            }
            //catches any errors that occur
            catch(Exception e){
                UI.println("Recursion: something went wrong..");
            }
        }
        else
            UI.println("finnished");
    }
    
    //takes html as argument and returns just the text of the html
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
