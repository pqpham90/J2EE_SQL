package assign.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "assignments" )
public class Assignment {
	
	private Long id;

    private String title;
    private Date date;
    private UTCourse utcourse;
    
    public Assignment() {
    	// this form used by Hibernate
    }
    
    public Assignment(String title, Date date) {
    	// for application use, to create new events
    	this.title = title;
    	this.date = date;
    }
    
    @Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ASSIGNMENT_DATE")
    public Date getDate() {
		return date;
    }

    public void setDate(Date date) {
		this.date = date;
    }
    
    @ManyToOne
    @JoinColumn(name="course_id")
    public UTCourse getCourse() {
    	return this.utcourse;
    }
    
    public void setCourse(UTCourse c) {
    	this.utcourse = c;
    }

    public String getTitle() {
		return title;
    }

    public void setTitle(String title) {
		this.title = title;
    }
}
