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

	// takes in the data and transforms it into a format the database loader can use
	public Map<String, List<String>> transform(Map<String, List<String>> data) {
		logger.info("Inside transform.");

		Map<String, List<String>> logData = new LinkedHashMap<>();

		// only deal with meeting links
		for (String link : data.keySet()) {
			Pattern p = Pattern.compile("^http://eavesdrop\\.openstack\\.org/meetings/(\\w+)/");
			Matcher m = p.matcher(link);

			// looks inside each year for meeting logs.
			for (String year : data.get(link)) {
				if(m.matches()) {
					ArrayList<String> meetingLogs = getLog(link + year, m.group(1));

					for (String log: meetingLogs) {
						ArrayList<String> meetingData = new ArrayList<>();
						// adds the data into the meetingData list
						meetingData.add(m.group(1));
						meetingData.add(year.substring(0, year.length() - 1));
						meetingData.add(link + year + log);

						// adds the data to the logData hash
						logData.put(log, meetingData);
					}
				}
			}
		}
		
		return logData;
	}

	// obtains the appropriate log file
	public ArrayList<String> getLog(String url, String team) {
		ArrayList<String> meetingLogs = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("body a");

			String match = "";

			/*
			 * Place log files into pre-determined indexes based on extension
			 *
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

				// making sure we're dealing with a log link
				if(original.contains(team)) {
					String s = original.replace(team+".", "");

					String log[] = s.split("\\.");

					// adds when is on same meeting and index where necessary
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
						// this is a new meeting name
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

					// for the final meeting
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