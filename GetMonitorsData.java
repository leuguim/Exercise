import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// Class Product
class Product{
	public int monitors;
	public int check_rate;
	public int history;
	public boolean multiple_notifications;
	public boolean push_notifications;
	public float price;
}


public class GetMonitorsData {

	public static void main(String[] args) {
		
		try{
			// Obtain the HTML document
			Document doc = Jsoup.connect("https://www.port-monitor.com/plans-and-pricing").userAgent("mozilla/17.0").get();
			Elements htmlProducts = doc.select("div.product");
			
			Product products[] = new Product[6];
			
			int i=0;
			
			System.out.print("[");
			
			for(Element prod:htmlProducts){
				
				// Find all properties of each element
				Product p = new Product();
				
				p.monitors = Integer.parseInt( prod.getElementsByTag("h2").first().text() );
				
				p.check_rate = Integer.parseInt( prod.select("dl.thin dd").first().text().substring(6, 8) );
				
				p.history = Integer.parseInt( prod.select("dl.thin dd").get(1).text().substring(0, 2) );
				
				p.multiple_notifications = false;
				if( prod.select("dl.thin dd span.label").first().text().equals("Yes") )
					p.multiple_notifications = true;
				
				p.push_notifications = false;
				if( prod.select("dl.thin dd span.label").get(1).text().equals("Yes") )
					p.push_notifications = true;
				
				String priceStr = prod.select("p a.btn").first().text();
				priceStr = priceStr.substring(1, priceStr.length() - 5);
				p.price = Float.parseFloat( priceStr );
				
				// Show the result
				System.out.print( "\n  { \n" 
				  + "    monitors: " + p.monitors + "\n" 
				  + "    check_rate: " + p.check_rate + "\n" 
				  + "    history: " + p.history + "\n" 
				  + "    multiple_notifications: " + p.multiple_notifications + "\n" 
				  + "    push_notifications: " + p.push_notifications + "\n" 
				  + "    price: " + p.price + "\n"
				  + "  }");
				
				
				products[i] = p;
				i++;
			}
			
			System.out.print("\n]");
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}