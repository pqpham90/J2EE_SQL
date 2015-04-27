package assign.resources;

import assign.services.MyEavesDropService;
import assign.services.MyEavesDropServiceImpl;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/myeavesdrop/meetings")
public class MyEavesDropResource {
	MyEavesDropService myEavesDropService;
	String password;
	String username;
	String dburl;

	public MyEavesDropResource(@Context ServletContext servletContext) {
		dburl = servletContext.getInitParameter("DBURL");
		username = servletContext.getInitParameter("DBUSERNAME");
		password = servletContext.getInitParameter("DBPASSWORD");
		this.myEavesDropService = new MyEavesDropServiceImpl(dburl, username, password);
	}

	public MyEavesDropResource(String password, String username, String dburl) {
		this.password = password;
		this.username = username;
		this.dburl = dburl;
		this.myEavesDropService = new MyEavesDropServiceImpl(dburl, username, password);
	}

	// just for checking that enviroment is working
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public Response helloWorld() {
		String result = "Hello world: " + dburl + " " + username + " " + password;
		return Response.status(405).entity(result).build();
	}

	// returns an xml of the number of meetings for the meeting in a given year
	@GET
	@Path("/{meeting}/year/{year}/count")
	@Produces("application/xml")
	public Response getNumMeetingCountInYear(@PathParam("meeting") String meeting,
	                                     @PathParam("year") String year) {
		String result = "";

		try {
			if(!myEavesDropService.checkMeeting(meeting) || !myEavesDropService.checkYearsExist(year)) {
				return Response.status(400).entity(result).build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			result += "<meetings>";
			result += "<count>" + myEavesDropService.getMeetingCounts(meeting, year) + "</count>";
			result += "</meetings>";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(result).build();
	}

	// returns an xml of all the meetings for the meeting in a given year
	@GET
	@Path("/{meeting}/year/{year}")
	@Produces("application/xml")
	public Response getNumMeetingsInYear(@PathParam("meeting") String meeting,
	                                     @PathParam("year") String year) {
		String result = "";

		try {
			if(!myEavesDropService.checkMeeting(meeting) || !myEavesDropService.checkYearsExist(year)) {
				return Response.status(400).entity(result).build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			result += "<meetings>";
			result += myEavesDropService.getMeetings(meeting, year);
			result += "</meetings>";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(result).build();
	}
}
