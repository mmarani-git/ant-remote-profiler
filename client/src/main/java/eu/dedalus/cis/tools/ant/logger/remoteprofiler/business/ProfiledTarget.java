package eu.dedalus.cis.tools.ant.logger.remoteprofiler.business;

import java.util.Date;

import org.apache.tools.ant.Target;

public class ProfiledTarget {
	private Target target;
	private Date start;
	private Date end;
	
	public ProfiledTarget(Target target, Date start) {
		super();
		this.target = target;
		this.start = start;
	}
	
	public Target getTarget() {
		return target;
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
		return "ProfiledTarget [target=" + target.getName() + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
