/**
 * Neo 4 OpenData example project.
 * 
 * Copyright (C) 2016 Goulwen Le Fur
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package neo4opendata.internal.db;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import neo4opendata.db.DatabaseService;

/**
 * @author <a href="mailto:goulwen.lefur@gmail.com">Goulwen Le Fur</a>.
 *
 */
public class DatabaseServiceImpl implements DatabaseService {
	
	/**
	 * {@inheritDoc}
	 * @see neo4opendata.db.DatabaseService#getDBFacade(java.lang.String)
	 */
	@Override
	public GraphDatabaseService getDBFacade(String dbPath) {
		return new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbPath));
	}
	
	/**
	 * {@inheritDoc}
	 * @see neo4opendata.db.DatabaseService#shutDown(org.neo4j.graphdb.GraphDatabaseService)
	 */
	@Override
	public void shutDown(GraphDatabaseService db) {
		db.shutdown();
	}

	/**
	 * {@inheritDoc}
	 * @see neo4opendata.db.DatabaseService#performOperation(org.neo4j.graphdb.GraphDatabaseService, neo4opendata.db.DatabaseService.DatabaseOperation)
	 */
	@Override
	public void performOperation(GraphDatabaseService db, DatabaseOperation operation) throws Exception {
		try (Transaction tx = db.beginTx()) {
			operation.run();
			tx.success();
		}
	}

}
