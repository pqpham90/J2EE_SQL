package assign.services;

import assign.domain.Meeting;
import assign.etl.DBLoader;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class MyEavesDropServiceImpl implements MyEavesDropService {
	String dbURL = "";
	String dbUsername = "";
	String dbPassword = "";
	DataSource ds;

	// DB connection information would typically be read from a config file.
	public MyEavesDropServiceImpl(String dbUrl, String username, String password) {
		this.dbURL = dbUrl;
		this.dbUsername = username;
		this.dbPassword = password;

		ds = setupDataSource();
	}

	public DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername(this.dbUsername);
        ds.setPassword(this.dbPassword);
        ds.setUrl(this.dbURL);
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }

	// counts number of meetings for given year
	public int getMeetingCounts(String meeting, String year) throws Exception {
		DBLoader dbLoader = new DBLoader();
		return dbLoader.getMeeting(meeting, year).size();
	}


	public String getMeetings(String meeting, String year) throws Exception {
		DBLoader dbLoader = new DBLoader();
		List<Meeting> meetings =  dbLoader.getMeeting(meeting, year);

		String result = "";

		for (Meeting m : meetings) {
			result += "<meeting>";
			result += "<team_meeting_name>" + m.getTeamMeetingName() +"</team_meeting_name>";
			result += "<year>" + m.getYear() +"</year>";
			result += "<meeting_name>" + m.getMeetingName() +"</meeting_name>";
			result += "<link>" + m.getLink() +"</link>";
			result += "</meeting>";
		}

		return result;
	}


	public boolean checkMeeting(String meeting) throws Exception {
		DBLoader dbLoader = new DBLoader();
		return dbLoader.meetingExists(meeting);
	}

	public boolean checkMYears(String meeting) throws Exception {
		DBLoader dbLoader = new DBLoader();
		return dbLoader.yearExists(meeting);
	}
}
