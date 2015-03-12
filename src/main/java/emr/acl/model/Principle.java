package emr.acl.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name="PRINCIPLE", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"USERNAME", "EMAIL"})
    )
public class Principle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="first_name", nullable = false, length = 30)
	private String firstName;
	
	@Column(name="last_name", nullable = false, length = 30)
	private String lastName;
	
	@Column(name="username", nullable = false, length = 30)
	private String username;
	
	@Column(name="password", nullable = false, length = 80)
	private String password;
	
	@Column(name="job_title", nullable = true, length = 30)
	private String jobTitle;
	
	@Column(name="role", nullable = false, length = 10)
	private String role;
	
	@Column(name="email", nullable = false, length = 50)
	private String email;
	
	@Column(name="last_password_change")
	private String lastPasswordChange;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> patientIds = new HashSet<String>();
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getPatientIds() {
		return patientIds;
	}
	public void setPatientIds(Set<String> patientIds) {
		this.patientIds = patientIds;
	}
	public String getLastPasswordChange() {
		return lastPasswordChange;
	}
	public void setLastPasswordChange(String lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
