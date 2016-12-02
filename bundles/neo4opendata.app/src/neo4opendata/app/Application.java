package neo4opendata.app;

import org.neo4j.graphdb.GraphDatabaseService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import neo4opendata.db.DatabaseService;
import neo4opendata.db.operations.DatabaseOperation;
import neo4opendata.db.operations.Result;
import neo4opendata.db.operations.impl.InitializeDB;
import neo4opendata.db.operations.impl.InitializeDB.InitializationResult;

public class Application implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Application.context = bundleContext;
		System.out.println("Start. Database service acquisition...");
		ServiceReference<DatabaseService> databaseServiceReference = bundleContext.getServiceReference(DatabaseService.class);
		DatabaseService databaseService = bundleContext.getService(databaseServiceReference);
		System.out.println("Got it! Loading database...");
		GraphDatabaseService dbFacade = databaseService.getDBFacade("/home/glefur/Perso/db/test.db");
		if (dbFacade != null) {
			System.out.println("Got it! ");
			DatabaseOperation operation = new InitializeDB();
			databaseService.performOperation(dbFacade, operation);
			databaseService.shutDown(dbFacade);
		} else {
			System.err.println("Fail.");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Application.context = null;
	}

}
