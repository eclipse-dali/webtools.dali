/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.transformer.DisabledTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class GraphIteratorTests
	extends TestCase
{
	/** this will be populated with all the nodes created for the test */
	Collection<GraphNode> nodes = new ArrayList<GraphNode>();

	public GraphIteratorTests(String name) {
		super(name);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testHasNext() {
		Iterator<GraphNode> iterator = this.buildGraphIterator();
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(this.nodes.size(), i);
	}

	public void testNext() {
		Iterator<GraphNode> iterator = this.buildGraphIterator();
		while (iterator.hasNext()) {
			assertTrue("bogus element", this.nodes.contains(iterator.next()));
		}
	}

	public void testNoSuchElementException() {
		Iterator<GraphNode> iterator = this.buildGraphIterator();
		boolean exCaught = false;
		while (iterator.hasNext()) {
			iterator.next();
		}
		try {
			iterator.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

	public void testSize() {
		Iterator<GraphNode> iterator = this.buildGraphIterator();
		int iteratorSize = IteratorTools.size(iterator);
		int actualSize = this.nodes.size();
		assertTrue("Too few items in iterator.", iteratorSize >= actualSize);
		assertTrue("Too many items in iterator.", iteratorSize <= actualSize);
	}

	public void testInvalidGraphIterator() {
		boolean exCaught = false;
		try {
			// missing method override
			Iterator<GraphNode> iterator = IteratorTools.graphIterator(this.buildGraphRoot(), DisabledTransformer.<GraphNode, Iterator<? extends GraphNode>>instance());
			GraphNode gn = iterator.next();
			fail("invalid graph node: " + gn);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

	private Iterator<GraphNode> buildGraphIterator() {
		return IteratorTools.graphIterator(this.buildGraphRoot(), CHILDREN_TRANSFORMER);
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

	private static final TransformerAdapter<GraphNode, Iterator<? extends GraphNode>> CHILDREN_TRANSFORMER = new ChildrenTransformer();
	static class ChildrenTransformer
		extends TransformerAdapter<GraphNode, Iterator<? extends GraphNode>>
	{
		@Override
		public Iterator<GraphNode> transform(GraphNode node) {
			return node.neighbors();
		}
	}

	public class GraphNode {
		private String name;

		private Collection<GraphNode> neighbors = new ArrayList<GraphNode>();

		public GraphNode(String name) {
			super();
			GraphIteratorTests.this.nodes.add(this); // log node
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		void setNeighbors(GraphNode[] neighbors) {
			this.neighbors = ListTools.arrayList(neighbors);
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
