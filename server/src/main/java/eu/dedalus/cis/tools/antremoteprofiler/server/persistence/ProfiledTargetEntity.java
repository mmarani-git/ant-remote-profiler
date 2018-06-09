package eu.dedalus.cis.tools.antremoteprofiler.server.persistence;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledTargetDTO;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledTaskDTO;

@Entity
@Table(name="PROFILED_TARGET")
public class ProfiledTargetEntity {
	@Id @GeneratedValue @Column(name="id_target")
	private Long id;
	@Column
	private String targetName;
	@Column(name="start_ts")
	private Date start;
	@Column(name="end_ts")
	private Date end;
	
	@OneToMany(
	        cascade = CascadeType.PERSIST,
	        mappedBy="target"
	    )
	private List<ProfiledTaskEntity> tasks = new Vector<ProfiledTaskEntity>();
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_build")
	private ProfiledBuildEntity build;
	
	public ProfiledTargetEntity() {};
	public ProfiledTargetEntity(ProfiledTargetDTO dto,ProfiledBuildEntity build) {
		targetName=dto.getTargetName();
		start = (Date) dto.getStart();
		end = (Date) dto.getEnd().clone();
		for (ProfiledTaskDTO task : dto.getTasks()) {
			tasks.add(new ProfiledTaskEntity(task,this));
		}
		this.build=build;
	}
	
	public ProfiledBuildEntity getBuild() {
		return build;
	}
	public void setBuild(ProfiledBuildEntity build) {
		this.build = build;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
	public List<ProfiledTaskEntity> getTasks() {
		return tasks;
	}
	public void setTasks(List<ProfiledTaskEntity> tasks) {
		this.tasks = tasks;
	}
	@Override
	public String toString() {
		return "ProfiledTargetEntity [id=" + id + ", targetName=" + targetName + ", start=" + start + ", end=" + end
				+ ", tasks=" + tasks + ", build=" + build + "]";
	}
}
