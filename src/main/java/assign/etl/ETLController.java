package assign.etl;

import java.util.ArrayList;
import java.util.List;
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
		ArrayList<String> source = new ArrayList<>();
		source.add("http://eavesdrop.openstack.org/meetings/solum/");
		source.add("http://eavesdrop.openstack.org/meetings/solum_team_meeting/");

		for(String link : source){
			reader = new EavesdropReader(link);

			// Read data
			Map<String, List<String>> data = reader.readData();

			// Transform data
			Map<String, List<String>> transformedData = transformer.transform(data);

			// Load data
			loader.loadData(transformedData);
		}

		System.exit(0);
	}
}
