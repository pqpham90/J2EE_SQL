package assign.services;

import assign.etl.DBLoader;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

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

	// parses out all meeting info from database
	public int getMeetings(String meeting, String year) throws Exception {
		DBLoader dbLoader = new DBLoader();
		return dbLoader.getMeeting(meeting, year).size();
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
