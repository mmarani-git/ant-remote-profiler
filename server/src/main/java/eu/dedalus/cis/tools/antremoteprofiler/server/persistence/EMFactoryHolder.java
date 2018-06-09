package eu.dedalus.cis.tools.antremoteprofiler.server.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactoryHolder {
	private static EntityManagerFactory emf=null;
	
	private static synchronized EntityManagerFactory getEMF() {
		if (emf==null)
			emf=Persistence.createEntityManagerFactory( "buildprofiler" );
		
		return emf;
	}

	public static void closeEMF() {
		if(emf!=null)
			emf.close();
	}
	
	public static EntityManager createEntityManager() {
		return getEMF().createEntityManager();
	}
}
