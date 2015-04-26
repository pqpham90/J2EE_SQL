package assign.etl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EavesdropReader {
	
	private String url;
	private Logger logger;
	
	public EavesdropReader(String url) {
		this.url = url;
		
		logger = Logger.getLogger("EavesdropReader");
	}
	
	/*
	 * Return a map where the contents of map is a single entry:
	 * <this.url, List-of-parsed-entries>
	 */
	public Map<String, List<String>> readData() {
		logger.info("Inside readData.");

		Map<String, List<String>> data = new HashMap<String, List<String>>();
		List<String> years = new ArrayList<>();

		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("body a");

			ListIterator<Element> iter = links.listIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				String s = e.html();
				Pattern p = Pattern.compile("^\\d{4}/?");
				Matcher m = p.matcher(s);

				if(m.matches()) {
					years.add(s);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		data.put(url, years);
				
		return data;
	}
}
