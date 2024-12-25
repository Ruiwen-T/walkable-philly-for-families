import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookstoreParser {
    private String baseURL;
    private Document currentDoc;
    ArrayList<POI> stores;

    /*
     * Constructor that initializes the base URL and loads the document produced from that URL
     */
    public BookstoreParser(){
        this.baseURL = "https://phillybookstoremap.com/bookstores/";
        try{
            this.currentDoc = Jsoup.connect(this.baseURL).get();
            //System.out.println("successful");
        } catch(IOException e){
            System.out.println("Could not access bookstore site");
        }
    }

    /*
     * Populates array list of bookstores, stored as POI objects
     */
    public void getStores(){
        this.stores = new ArrayList<>(); //set up array list
        //look for all the h2s with an a tag
        Elements headers = this.currentDoc.select("h2:has(a)");
        for(Element h : headers){
            //for each header, get its corresponding link and name
            Element aEl = h.selectFirst("a");
            String storeURL = aEl.attr("abs:href"); //resolve to absolute rather than relative URL
            String storeName = aEl.text();
            //go to the store's page
            try{
                Element sPage = Jsoup.connect(storeURL).get();
                //find h2 with "Philadelphia"
                Element tt = sPage.selectFirst("h2:contains(Philadelphia):has(a)");
                //split text on store name and "Find us on Google Maps" to obtain address
                String lText = tt.text();
                //System.out.println(lText);
                //specific cases where the bookstore name is written differently on different pages
                if(storeName.equals("H&H Books (The Head & The Hand)")){
                    storeName = "H&H Books";
                }
                if(storeName.equals("Straight From the Heart Bookstore")){
                    storeName = "Straight From the Heart";
                }
                if(storeName.equals("Wooden Shoe Books & Records")){
                    storeName = "Wooden Shoe Books and Records";
                }
                String[] components = lText.split(storeName);
                String[] subcomponents;
                String toSplit;
                //edge case where the bookstore name is repeated
                if(storeName.equals("booked.")){
                    //System.out.println(components[1]);
                    toSplit = components[2];
                }
                else{
                    toSplit = components[1];
                }
                //System.out.println(toSplit);
                //inconsistencies in writing of city and state
                if(toSplit.contains("Philadelphia PA, ")) {
                    subcomponents = toSplit.split("Philadelphia PA, ");
                }
                else if(toSplit.contains("Philadelphia, PA ")){
                    subcomponents = toSplit.split("Philadelphia, PA ");
                }
                else{
                    subcomponents = toSplit.split("Philadelphia PA ");
                }
                //get address from before appearance of "Philadelphia"
                String address = subcomponents[0].substring(1);
                //System.out.println(subcomponents[0]);
                //System.out.println(subcomponents[1]);
                //pull out zip code from after PA
                String zipcode = subcomponents[1].substring(0,5);
                int zip = Integer.parseInt(zipcode);
                //instantiate a POI for the store and add to collection of stores
                POI p = new POI(storeName,0,0,"bookstore",address,zip,-2);
                stores.add(p);
            } catch(IOException e){
                System.out.println("Could not access bookstore subpage");
            }
        }
    }

    /*
    public static void main(String[] args){
        BookstoreParser bp = new BookstoreParser();
        bp.getStores();
        for(POI p : bp.stores){
            System.out.println("name: " + p.getName() + "\n address: " + p.getAddress() + "\n zip: " + p.getZip());
        }
    }
     */
}