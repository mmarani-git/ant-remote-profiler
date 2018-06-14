package eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto;

import java.util.Date;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTarget;

public class ProfiledTargetDTO {
	private String targetName;
	private Date start;
	private Date end;

	public ProfiledTargetDTO() {};

	public ProfiledTargetDTO(ProfiledTarget target) {
		targetName = target.getTarget().getName();
		start = (Date) target.getStart().clone();
		end = (Date) target.getEnd().clone();
	}

	public String getTargetName() {
		return targetName;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
}
