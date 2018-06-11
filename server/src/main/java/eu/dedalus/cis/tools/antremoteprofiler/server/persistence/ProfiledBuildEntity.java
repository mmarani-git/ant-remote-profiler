package eu.dedalus.cis.tools.antremoteprofiler.server.persistence;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledBuildDTO;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledTargetDTO;

@Entity
@Table(name="profiled_build")
public class ProfiledBuildEntity {
	@Id @GeneratedValue @Column(name="id_build")
	private Long id;
	@Column
	private String username;
	@Column
	private String hostname;
	@Column(name="start_ts")
	private Date start;
	@Column(name="end_ts")
	private Date end;
	@Column
	private String projectName;
	@OneToMany(
	        cascade = CascadeType.PERSIST,
	        mappedBy="build"
	    )
	private List<ProfiledTargetEntity> targets=new Vector<ProfiledTargetEntity>();
	@Column
	private Boolean success;
	
	public ProfiledBuildEntity() {	}

	public ProfiledBuildEntity(ProfiledBuildDTO dto) {
		username=dto.getUsername();
		hostname=dto.getHostname();
		start=(Date) dto.getStart().clone();
		end=(Date) dto.getEnd().clone();
		projectName=dto.getProjectName();
		success = dto.getSuccess();
		for(ProfiledTargetDTO target : dto.getTargets()) {
			targets.add(new ProfiledTargetEntity(target,this));
		}
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<ProfiledTargetEntity> getTargets() {
		return targets;
	}

	public void setTargets(List<ProfiledTargetEntity> targets) {
		this.targets = targets;
	}

	@Override
	public String toString() {
		return "ProfiledBuildEntity [id=" + id + ", username=" + username + ", hostname=" + hostname + ", start="
				+ start + ", end=" + end + ", projectName=" + projectName + ", targets=" + targets + "]";
	}
	
	
}
