package assign.etl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ETLController {
	private EavesdropReader reader;
	private Transformer transformer;
	private DBLoader loader;
	
	public ETLController() {
		transformer = new Transformer();
		loader = new DBLoader();
	}
	
	public static void main(String[] args) {
		ETLController etlController = new ETLController();
		etlController.performETLActions();
	}

	private void performETLActions() {		
		try {
			String source = "http://eavesdrop.openstack.org/meetings/solum/";
			reader = new EavesdropReader(source);
			
			// Read data
			Map<String, List<String>> data = reader.readData();
			
			// Transform data
			Map<String, List<String>> transformedData = transformer.transform(data);

			Document doc = Jsoup.connect(source).get();
			Elements links = doc.select("body a");

			ListIterator<Element> iter = links.listIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				String s = e.html();
				System.out.println(s);
			}
			
			// Load data
			loader.loadData(transformedData);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
