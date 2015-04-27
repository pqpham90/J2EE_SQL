package assign.etl;

import assign.domain.Meeting;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class TestDBLoader extends TestCase {

	DBLoader etlHandler;
	
	@Override
	protected void setUp() {
		etlHandler = new DBLoader();
	}
	
//	@Test
//	public void testMeetingInsert() {
//		try {
//			String teamMeetingName = "Hyrule";
//			String year = "1997";
//			String meetingName = "Triforce";
//			String link = "Link";
//
//			int assignmentId = etlHandler.addMeeting(teamMeetingName, year, meetingName, link);
//			System.out.println("Meeting ID:" + assignmentId);
//
//			Meeting proxyServer = etlHandler.getMeeting(teamMeetingName);
//			assertEquals(proxyServer.getTeamMeetingName(), teamMeetingName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testMeetingtGetWithId() {
//		try {
//			String teamMeetingName = "Balamb";
//			String year = "1999";
//			String meetingName = "Gunblade";
//			String link = "Squall";
//
//			int assignmentId = etlHandler.addMeeting(teamMeetingName, year, meetingName, link);
//			System.out.println("Meeting ID:" + assignmentId);
//
//			Meeting proxyServer = etlHandler.getMeeting(assignmentId);
//			assertEquals(proxyServer.getTeamMeetingName(), teamMeetingName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testGetMeetingWithName() {
		try {
			String meetingName = "solum_team_meetingz";
			Boolean meetingExists = etlHandler.meetingExists(meetingName);

			assertFalse(meetingExists);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMeetingWithYear() {
		try {
			String year = "1980";
			Boolean yearExists = etlHandler.yearExists(year);

			assertFalse(yearExists);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMeetingWithNameAndYear() {
		try {
			String meetingName = "solum_team_meeting";
			String year = "2014";

			List<Meeting> meetings = etlHandler.getMeeting(meetingName, year);

			assertEquals(48, meetings.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
