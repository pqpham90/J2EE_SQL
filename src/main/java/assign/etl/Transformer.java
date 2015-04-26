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

public class Transformer {
	private Logger logger;
	
	public Transformer() {
		logger = Logger.getLogger("Transformer");		
	}
	
	public Map<String, List<String>> transform(Map<String, List<String>> data) {
		logger.info("Inside transform.");

		Map<String, List<String>> logData = new LinkedHashMap<>();

		for (String link : data.keySet()) {
			Pattern p = Pattern.compile("^http://eavesdrop\\.openstack\\.org/meetings/(\\w+)/");
			Matcher m = p.matcher(link);

			for (String year : data.get(link)) {
				if(m.matches()) {
					ArrayList<String> meetingLogs = getLog(link + year, m.group(1));

					for (String log: meetingLogs) {
//						System.out.println(m.group(1));
						ArrayList<String> meetingData = new ArrayList<>();
						meetingData.add(m.group(1));
						meetingData.add(year.substring(0, year.length() - 1));
						meetingData.add(link + year + log);

						logData.put(log, meetingData);
					}
				}
			}
		}

		// transform data into newData
		
		return logData;
	}

	public ArrayList<String> getLog(String url, String team) {
		ArrayList<String> meetingLogs = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("body a");

			String match = "";

			/*
 			 * 0 = html
 			 * 1 = log.html
 			 * 2 = log.txt
 			 * 3 = txt
 			 */
			boolean logExists[] = new boolean[4];
			String logs[] = new String[4];

			ListIterator<Element> iter = links.listIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				String original = e.html();

				if(original.contains(team)) {
					String s = original.replace(team+".", "");

					String log[] = s.split("\\.");

					if((log[0] + "." + log[1]).equals(match)) {
						if(original.contains("html") && !original.contains("log.html")) {
							logs[0] = original;
							logExists[0] = true;
						}
						else if (original.contains("log.html")) {
							logs[1] = original;
							logExists[1] = true;
						}
						else if (original.contains("log.txt")) {
							logs[2] = original;
							logExists[2] = true;
						}
						else if (original.contains("txt") && !original.contains("log.txt")) {
							logs[3] = original;
							logExists[3] = true;
						}
					}
					else {

						if(logExists[1]) {
							meetingLogs.add(logs[1]);
						}
						else if(logExists[2]) {
							meetingLogs.add(logs[2]);
						}
						else if(logExists[0]) {
							meetingLogs.add(logs[0]);
						}
						else if(logExists[3]) {
							meetingLogs.add(logs[3]);
						}

						if(original.contains("html") && !original.contains("log.html")) {
							logs[0] = original;
							logExists[0] = true;
						}
						else if (original.contains("log.html")) {
							logs[1] = original;
							logExists[1] = true;
						}
						else if (original.contains("log.txt")) {
							logs[2] = original;
							logExists[2] = true;
						}
						else if (original.contains("txt") && !original.contains("log.txt")) {
							logs[3] = original;
							logExists[3] = true;
						}

						logExists = new boolean[4];
						logs = new String[4];
						match = log[0] + "." + log[1];
					}

					if(logExists[1]) {
						meetingLogs.add(logs[1]);
					}
					else if(logExists[2]) {
						meetingLogs.add(logs[2]);
					}
					else if(logExists[0]) {
						meetingLogs.add(logs[0]);
					}
					else if(logExists[3]) {
						meetingLogs.add(logs[3]);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return meetingLogs;
	}
}