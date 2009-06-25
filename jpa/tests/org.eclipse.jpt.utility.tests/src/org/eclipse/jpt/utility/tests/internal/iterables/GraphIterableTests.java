/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.GraphIterable;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class GraphIterableTests extends TestCase {
	/** this will be populated with all the nodes created for the test */
	Collection<GraphNode> nodes = new ArrayList<GraphNode>();

	public GraphIterableTests(String name) {
		super(name);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testNeighbors() {
		for (GraphNode gn : this.buildGraphIterable()) {
			assertTrue(this.nodes.contains(gn));
		}
	}

	private Iterable<GraphNode> buildGraphIterable() {
		return new GraphIterable<GraphNode>(this.buildGraphRoot()) {
			@Override
			public Iterator<GraphNode> neighbors(GraphNode next) {
				return next.neighbors();
			}
		};
	}

	private GraphNode buildGraphRoot() {
		GraphNode ncNode = new GraphNode("North Carolina");
		GraphNode vaNode = new GraphNode("Virginia");
		GraphNode scNode = new GraphNode("South Carolina");
		GraphNode gaNode = new GraphNode("Georgia");
		GraphNode flNode = new GraphNode("Florida");
		GraphNode alNode = new GraphNode("Alabama");
		GraphNode msNode = new GraphNode("Mississippi");
		GraphNode tnNode = new GraphNode("Tennessee");

		ncNode.setNeighbors(new GraphNode[] { vaNode, scNode, gaNode, tnNode });
		vaNode.setNeighbors(new GraphNode[] { ncNode, tnNode });
		scNode.setNeighbors(new GraphNode[] { ncNode, gaNode });
		gaNode.setNeighbors(new GraphNode[] { ncNode, scNode, flNode, alNode, tnNode });
		flNode.setNeighbors(new GraphNode[] { gaNode });
		alNode.setNeighbors(new GraphNode[] { gaNode, msNode, tnNode });
		msNode.setNeighbors(new GraphNode[] { alNode, tnNode });
		tnNode.setNeighbors(new GraphNode[] { vaNode, ncNode, gaNode, alNode, msNode });

		return ncNode;
	}

	public class GraphNode {
		private String name;

		private Collection<GraphNode> neighbors = new ArrayList<GraphNode>();

		public GraphNode(String name) {
			super();
			GraphIterableTests.this.nodes.add(this); // log node
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		void setNeighbors(GraphNode[] neighbors) {
			this.neighbors = CollectionTools.list(neighbors);
		}

		public Iterator<GraphNode> neighbors() {
			return this.neighbors.iterator();
		}

		public int neighborsSize() {
			return this.neighbors.size();
		}

		@Override
		public String toString() {
			return "GraphNode(" + this.name + ")";
		}
	}
}
