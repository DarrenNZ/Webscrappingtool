import org.jsoup.*;
import ecs100.*;

//import org.jsoup.nodes.Document.*;

/**
 * Write a description of class imageGrabber here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class imageGrabber
{
    // instance variables - replace the example below with your own

    //http://www.sciencekids.co.nz/images/pictures/flags680/Fiji.jpg
    //need to do a google search for country ports and return valu

    //Document doc;
    /**
     * Constructor for objects of class imageGrabber
     */
    public imageGrabber()
    {
        /**
        try {

        //goes to the url and grabs the image
        //doc = Jsoup.connect("https://en.wikipedia.org/wiki/Fiji").get();
        Elements images = doc2.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element image : images) {

        UI.println("\nsrc : " + image.attr("src"));
        UI.println("height : " + image.attr("height"));
        UI.println("width : " + image.attr("width"));
        UI.println("alt : " + image.attr("alt"));

        }

        } catch (IOException e) {
        e.printStackTrace();
        }
         **/
        try {
            String html = ("https://en.wikipedia.org/wiki/Fiji");//this.newLink();//URL
            Document doc = Jsoup.parse(html);

            String outPut = doc.innerText();
            outPut = this.html2text(outPut);

            //begining of the para to collect
            String StrStart = new String("art from Fijian towns shows that");//start string variable
            int start = outPut.indexOf( StrStart );

            //end of the para to collect
            String StrEnd = "Fijians, 2,000 Chinese and 5,000 Europeans"; //end string variable
            int finnish = outPut.indexOf( StrEnd );

            //final result of concatination
            String result = outPut.substring( start , finnish + StrEnd.length()); // return the result
            UI.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
