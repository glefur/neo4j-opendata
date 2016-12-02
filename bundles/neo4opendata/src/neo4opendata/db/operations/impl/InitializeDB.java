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
package neo4opendata.db.operations.impl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import neo4opendata.db.model.RelationShip;
import neo4opendata.db.operations.DatabaseOperation;
import neo4opendata.db.operations.Result;

/**
 * @author <a href="mailto:goulwen.lefur@gmail.com">Goulwen Le Fur</a>.
 *
 */
public class InitializeDB implements DatabaseOperation {

	/**
	 * {@inheritDoc}
	 * @see neo4opendata.db.DatabaseService.DatabaseOperation#run()
	 */
	@Override
	public Result run(GraphDatabaseService db) throws Exception {
		Node firstNode = db.createNode();
		firstNode.setProperty("message", "Hello, ");
		Node secondNode = db.createNode();
		secondNode.setProperty("message", "World!");

		Relationship relationship = firstNode.createRelationshipTo(secondNode, RelationShip.KNOWS);
		relationship.setProperty("message", "brave Neo4j ");
		System.out.print( firstNode.getProperty( "message" ) );
		System.out.print( relationship.getProperty( "message" ) );
		System.out.print( secondNode.getProperty( "message" ) );	

		return new InitializationResult(firstNode, secondNode, relationship);
	}
	
	public static final class InitializationResult implements Result {
		
		private Node firstNode;
		private Node secondNode;
		private Relationship relationShip;
		
		public InitializationResult(Node firstNode, Node secondNode, Relationship relationShip) {
			this.firstNode = firstNode;
			this.secondNode = secondNode;
			this.relationShip = relationShip;
		}

		public Node getFirstNode() {
			return firstNode;
		}

		public Node getSecondNode() {
			return secondNode;
		}

		public Relationship getRelationShip() {
			return relationShip;
		}
		
	}

}
