import ecs100.*;
import org.jsoup.*;
import org.jsoup.nodes.Document.*;
import com.jaunt.*;
import java.io.*;
import java.util.*;

/**
 * The purpose of this class is to ask the user which country and or port they would like to find out about, 
 * then visit predefined webpages that are seen as containing userful information and put each of these webpages into
 * webpage objects. 
 * 
 * @author (Darren Carroll) 
 * @version (16/10/2016)
 */
public class Process
{
    // declaration of class scope fields
    public static String country;
    public static String port;
    public int count = 0;
    public List <WebPage> allsites;
    public List <String> links;

    public Process()//String country, String port)
    {
        //this.country = country;
        this.country = UI.askString("What country would you like to find out about?");
        this.port = UI.askString("What port would you like to find out about?");

        allsites = new ArrayList<WebPage>();
        links = new ArrayList<String>();

        UI.clearGraphics();
        //UI.addButton("run", this::setUpLinks);
        if (this.country != ""){UI.addButton("run", this::setUpLinks);}

    }

    public void setUpLinks()
    {
        String start = "";
        String end = "";
        String link;

        link = "https://travel.state.gov/content/passports/en/country/" + this.country + ".html"; //country needs to be in lower
        start = "Safety and Security";
        this.scrape(link, start, end);

        link = "https://travel.state.gov/content/passports/en/country/" + this.country + ".html"; //country needs to be in lower
        start = "Local Laws & Special Circumstances";
        this.scrape(link, start, end);

        link = "https://travel.state.gov/content/passports/en/country/" + this.country + ".html"; //country needs to be in lower
        start = "Travel & Transportation";
        this.scrape(link, start, end);

        link = "https://travel.state.gov/content/passports/en/country/" + this.country + ".html"; //country needs to be in lower
        start = "Entry, Exit & Visa Requirements";
        this.scrape(link, start, end);

        link = "https://www.safetravel.govt.nz/" + this.country; 
        start = "The New Zealand High Commission";
        this.scrape(link, start, end);

        link = "http://ports.com/search/"; 
        link = this.precursorScrape(link , this.port);
        start = "Port details";
        this.scrape(link, start, end);

        //http://ports.com/search/
        this.populateGui();
    }

    public String newLink(){
        //get next link form the list of links
        count++;
        return this.links.get(count);
    }

    //cycles through all of the webpages calling on the getters that need to be used to display the information.
    public void populateGui(){
        for (WebPage w: allsites){
            UI.println( w.getLink() );
            UI.println( w.getTittle() );
            //if getImage()is true then grab image
            UI.println ( w.getContent() );  
            UI.println("---------------------------");
            UI.println();
        }
    }

    public void scrape(String link, String start, String end){
        /**
         *  this method creates a web scraping object, retrieves the html, cleans it then using the front and end variables selects the required information and puts it into the content variable which is passed into the webpages constructor
        along with the other search paramaters, the link of the webpage and the title.
         */

        try{
            //goes to URL and grabs the body of the text
            UserAgent userAgent = new UserAgent();
            userAgent.visit(link);//this.Link
            Element body = userAgent.doc.findFirst("body");

            String outPut = body.innerText();
            outPut = this.html2text(outPut);

            //begining of the para to collect
            String StrStart = new String(start);//start string variable
            int startInt = outPut.indexOf( StrStart );

            //end of the para to collect
            if (!end.equals("")){
                String StrEnd = end; //end string variable
                int finnishInt = outPut.indexOf( StrEnd );

                String content = outPut.substring( startInt , finnishInt + StrEnd.length()); 
                WebPage newWebPage = new WebPage(this.country, this.port, link, content, start);
                this.allsites.add(newWebPage);
            }
            else{
                String content = outPut.substring( startInt , startInt + 500); 
                WebPage newWebPage = new WebPage(this.country, this.port, link, content, start);
                this.allsites.add(newWebPage);
            }

        }
        //handles any errors that occur
        catch(JauntException e){         
            System.err.println(e);
        }

    }

    //cleans html and return just the text.
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
