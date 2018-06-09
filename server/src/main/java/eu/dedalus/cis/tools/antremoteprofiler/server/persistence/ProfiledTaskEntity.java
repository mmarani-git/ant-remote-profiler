package eu.dedalus.cis.tools.antremoteprofiler.server.persistence;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledTaskDTO;

@Entity
@Table(name="PROFILED_TASK")
public class ProfiledTaskEntity {
	
	@Id @GeneratedValue @Column(name="id_task")
	private Long id;
	@Column
	private String taskName;
	@Column(name="start_ts")
	private Date start;
	@Column(name="end_ts")
	private Date end;
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_target")
	private ProfiledTargetEntity target;
	
	public ProfiledTaskEntity() {
		super();
	}
	
	public ProfiledTaskEntity(ProfiledTaskDTO dto,ProfiledTargetEntity target) {
		taskName=dto.getTaskName();
		start=(Date) dto.getStart().clone();
		end=(Date) dto.getEnd().clone();
		this.target=target;
	}
	
	
	public ProfiledTargetEntity getTarget() {
		return target;
	}

	public void setTarget(ProfiledTargetEntity target) {
		this.target = target;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	@Override
	public String toString() {
		return "ProfiledTaskEntity [id=" + id + ", taskName=" + taskName + ", start=" + start + ", end=" + end
				+ ", target=" + target + "]";
	}
}
