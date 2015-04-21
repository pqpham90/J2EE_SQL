package assign.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name = "ut_courses" )
public class UTCourse {
	
	private Long id;
    private String courseName;
    private Set<Assignment> Assignment;

    public UTCourse() {
    	// this form used by Hibernate
    }
    
    public UTCourse(String courseName) {
    	this.courseName = courseName;
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
    
    @Column(name="course")
    public String getCourseName() {
		return courseName;
    }

    public void setCourseName(String courseName) {
		this.courseName = courseName;
    }
    
    @OneToMany(mappedBy="course")
    public Set<Assignment> getAssignment() {
    	return this.Assignment;
    }
    
    public void setAssignment(Set<Assignment> assignment) {
    	this.Assignment = assignment;
    }
}