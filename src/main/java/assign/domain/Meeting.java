package assign.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table( name = "meetings" )
public class Meeting {
	
	private int id;
    private String teamMeetingName;
    private String year;
    private String meetingName;
    private String link;

    public Meeting() {
    	// this form used by Hibernate
    }
    
    public Meeting( String teamMeetingName, String year, String meetingName, String link) {
    	// for application use, to create new events
        this.teamMeetingName = teamMeetingName;
        this.year = year;
        this.meetingName = meetingName;
        this.link = link;
    }
    
    @Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
		return id;
    }

    private void setId(int id) {
		this.id = id;
    }

    @Column(name = "team_meeting_name",  nullable = false, length = 20)
    public String getTeamMeetingName() {
        return teamMeetingName;
    }

    public void setTeamMeetingName(String name) {
        this.teamMeetingName = name;
    }

    @Column(nullable = false, length = 4)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(name = "meeting_name",  nullable = false, length = 50)
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Column(nullable = false, columnDefinition="varchar(1000)")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
