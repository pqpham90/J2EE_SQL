package assign.resources;

import junit.framework.TestCase;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by pqpham90 on 4/26/15.
 */
public class TestMyEavesDropResource extends TestCase {
	MyEavesDropResource resource;


	@Override
	protected void setUp() {
		resource = new MyEavesDropResource("jdbc:mysql://givecaketo.me:3306/student_courses", "mwa", "bKbYbK4A6QumJTrr");

	}

	@Test
	public void testGetNumMeetingsInYear1() {
		try {
			String meeting = "solum";
			String year = "2014";

			Response r = resource.getNumMeetingCountInYear(meeting, year);

			assertEquals(200, r.getStatus());
		}
		catch (Exception e) {

		}
	}

	@Test
	public void testGetNumMeetingsInYear2() {
		try {
			String meeting = "solum_team_meeting";
			String year = "2014";

			Response r = resource.getNumMeetingCountInYear(meeting, year);

			assertEquals(200, r.getStatus());
		}
		catch (Exception e) {

		}
	}

	@Test
	public void testGetNumMeetingsInYear3() {
		try {
			String meeting = "solumz";
			String year = "2014";

			Response r = resource.getNumMeetingCountInYear(meeting, year);

			assertEquals(400, r.getStatus());
		}
		catch (Exception e) {

		}
	}

	@Test
	public void testGetNumMeetingsInYear4() {
		try {
			String meeting = "solum";
			String year = "2020";

			Response r = resource.getNumMeetingCountInYear(meeting, year);

			assertEquals(400, r.getStatus());
		}
		catch (Exception e) {

		}
	}
}
