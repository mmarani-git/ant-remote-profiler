package eu.dedalus.cis.tools.ant.logger.remoteprofiler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.launch.Launcher;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledBuild;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTarget;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient.ProfilerHttpClient;

public class RemoteProfilerLogger extends DefaultLogger {
	private ProfiledBuild profiledBuild;
	
	@Override
	public void buildStarted(BuildEvent event) {
		super.buildStarted(event);
		String username = System.getProperty("user.name");
		String hostname="";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] Cannot retrieve hostname");
		}
		profiledBuild = new ProfiledBuild(username,hostname,new Date(),event.getProject());
	}
	
	@Override
	public void buildFinished(BuildEvent event) {
		super.buildFinished(event);
		profiledBuild.setEnd(new Date());
		
		Throwable error = event.getException();
		profiledBuild.setSuccess(error==null);
		
		ProfilerHttpClient client = new ProfilerHttpClient(profiledBuild);
		client.send();
	}
	
	private boolean isTargetEvent(BuildEvent event) {
		if (event.getTarget()==null) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] targetStarted but no target");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void targetStarted(BuildEvent event) {
		super.targetStarted(event);
		
		if (!isTargetEvent(event)) {
			return;
		}
		
		//at build start, project name is empty
		if(profiledBuild.getProject()==null) {
			profiledBuild.setProject(event.getProject());
		}
		
		ProfiledTarget profiledTarget = new ProfiledTarget(event.getTarget(), new Date());
		
		profiledBuild.addProfiledTarget(profiledTarget);
	}
	
	@Override
	public void targetFinished(BuildEvent event) {
		super.targetFinished(event);
		
		if (!isTargetStarted(event.getTarget()))
			return;
		
		profiledBuild.getProfiledTarget(event.getTarget()).setEnd(new Date());
	}
	
	private boolean isTargetStarted(Target target) {
		if (!profiledBuild.containsTarget(target)) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] targetFinished not registered at start");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Outputs to console the whole profiledBuild
	 * Useful for testing
	 */
	@SuppressWarnings("unused")
	private void print() {
		System.out.println("**** PROFILING DATA *****");
		System.out.println(profiledBuild);
		for(ProfiledTarget target : profiledBuild.getProfiledTargets()) {
			System.out.println("\t"+target);
		}
	}
	
	public static void main(String[] args) {
		List<String> antArgs = new ArrayList<String>();
		antArgs.add("-logger");
		antArgs.add("eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger");
		antArgs.add("-f");
		antArgs.add("src\\test\\java\\testbuild.xml");
		
		Launcher.main(antArgs.toArray(new String[0]));
	}
}
