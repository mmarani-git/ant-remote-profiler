package eu.dedalus.cis.tools.antremoteprofiler.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledBuildDTO;
import eu.dedalus.cis.tools.antremoteprofiler.server.persistence.EMFactoryHolder;
import eu.dedalus.cis.tools.antremoteprofiler.server.persistence.ProfiledBuildEntity;

public class RemoteProfilingServlet extends HttpServlet {
	private static final long serialVersionUID = -7792091549887711711L;
	private static final Logger logger = LoggerFactory.getLogger(RemoteProfilingServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>READY</h1>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		try {
			logger.info("MESSAGE RECEIVED");
			ProfiledBuildDTO profiledBuildDTO = getProfiledBuildDTO(req);
			persistBuild(profiledBuildDTO);
		} catch (Exception e) {
			logger.error("Error while processing message", e);
		}

		logger.info("END MESSAGE PROCESSING");
	}

	private void persistBuild(ProfiledBuildDTO profiledBuildDTO) {
		EntityManager entitymanager = EMFactoryHolder.createEntityManager();
		entitymanager.getTransaction().begin();

		try {
			ProfiledBuildEntity build = new ProfiledBuildEntity(profiledBuildDTO);

			entitymanager.persist(build);
			entitymanager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entitymanager.getTransaction().rollback();
		} finally {
			entitymanager.close();
		}
	}

	@Override
	public void destroy() {
		EMFactoryHolder.closeEMF();
	}

	private ProfiledBuildDTO getProfiledBuildDTO(HttpServletRequest req) throws IOException {
		String content;

		content = getContent(req);
		
		ObjectMapper mapper = new ObjectMapper();
		ProfiledBuildDTO profiledBuildDTO = mapper.readValue(content, ProfiledBuildDTO.class);

		return profiledBuildDTO;
	}

	private String getContent(HttpServletRequest req) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}
		
		String result = sb.toString();
		logger.debug("Received string:",result);
		return result;
	}
}
