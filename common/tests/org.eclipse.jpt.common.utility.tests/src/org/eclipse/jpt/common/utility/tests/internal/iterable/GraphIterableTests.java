/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.DisabledTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class GraphIterableTests
	extends TestCase
{
	/** this will be populated with all the nodes created for the test */
	Collection<GraphNode> nodes = new ArrayList<>();

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
		return ObjectTools.graph(this.buildGraphRoot(), CHILDREN_TRANSFORMER);
	}

	public void testNeighbors_roots() {
		for (GraphNode gn : this.buildGraphIterable_roots()) {
			assertTrue(this.nodes.contains(gn));
		}
	}

	private Iterable<GraphNode> buildGraphIterable_roots() {
		return IterableTools.graphIterable(new GraphNode[] { this.buildGraphRoot() }, CHILDREN_TRANSFORMER);
	}

	public void testToString() {
		assertNotNull(this.buildGraphIterable().toString());
	}

	public void testBogusTransformer() {
		boolean exCaught = false;
		try {
			for (GraphNode gn : IterableTools.graphIterable(this.buildGraphRoot(), DisabledTransformer.<GraphNode, Iterable<? extends GraphNode>>instance())) {
				assertTrue(this.nodes.contains(gn));
			}
			fail();
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public GraphNode buildGraphRoot() {
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

	private static final Transformer<GraphNode, Iterable<? extends GraphNode>> CHILDREN_TRANSFORMER = new ChildrenTransformer();
	static class ChildrenTransformer
		extends TransformerAdapter<GraphNode, Iterable<? extends GraphNode>>
	{
		@Override
		public Iterable<GraphNode> transform(GraphNode node) {
			return node.getNeighbors();
		}
	}

	public class GraphNode {
		private String name;

		private Collection<GraphNode> neighbors = new ArrayList<>();

		public GraphNode(String name) {
			super();
			GraphIterableTests.this.nodes.add(this); // log node
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		void setNeighbors(GraphNode[] neighbors) {
			this.neighbors = ListTools.arrayList(neighbors);
		}

		public Iterable<GraphNode> getNeighbors() {
			return this.neighbors;
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
