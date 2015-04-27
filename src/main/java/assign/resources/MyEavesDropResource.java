package assign.resources;

import assign.services.MyEavesDropService;
import assign.services.MyEavesDropServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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

	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public Response helloWorld() {
		String result = "Hello world: " + dburl + " " + username + " " + password;
		return Response.status(405).entity(result).build();
	}

	@GET
	@Path("/{meeting}/year/{year}/count")
	@Produces("application/xml")
	public Response getNumMeetingCountInYear(@PathParam("meeting") String meeting,
	                                     @PathParam("year") String year,
	                                     @Context final HttpServletResponse response) {
		String result = "";

		try {
			if(!myEavesDropService.checkMeeting(meeting) || !myEavesDropService.checkMYears(year)) {
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

	@GET
	@Path("/{meeting}/year/{year}")
	@Produces("application/xml")
	public Response getNumMeetingsInYear(@PathParam("meeting") String meeting,
	                                     @PathParam("year") String year,
	                                     @Context final HttpServletResponse response) {
		String result = "";

		try {
			if(!myEavesDropService.checkMeeting(meeting) || !myEavesDropService.checkMYears(year)) {
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
