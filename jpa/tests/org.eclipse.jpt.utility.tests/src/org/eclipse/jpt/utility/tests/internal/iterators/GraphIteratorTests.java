/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.GraphIterator;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class GraphIteratorTests extends TestCase {
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

	public void testHasNext1() {
		this.verifyHasNext(this.buildGraphIterator1());
	}

	public void testHasNext2() {
		this.verifyHasNext(this.buildGraphIterator2());
	}

	private void verifyHasNext(Iterator<GraphNode> iterator) {
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(this.nodes.size(), i);
	}

	public void testNext1() {
		this.verifyNext(this.buildGraphIterator1());
	}

	public void testNext2() {
		this.verifyNext(this.buildGraphIterator2());
	}

	private void verifyNext(Iterator<GraphNode> iterator) {
		while (iterator.hasNext()) {
			assertTrue("bogus element", this.nodes.contains(iterator.next()));
		}
	}

	public void testNoSuchElementException1() {
		this.verifyNoSuchElementException(this.buildGraphIterator1());
	}

	public void testNoSuchElementException2() {
		this.verifyNoSuchElementException(this.buildGraphIterator2());
	}

	private void verifyNoSuchElementException(Iterator<GraphNode> iterator) {
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

	public void testSize1() {
		this.verifySize(this.buildGraphIterator1());
	}

	public void testSize2() {
		this.verifySize(this.buildGraphIterator2());
	}

	private void verifySize(Iterator<GraphNode> iterator) {
		int iteratorSize = CollectionTools.size(iterator);
		int actualSize = this.nodes.size();
		assertTrue("Too few items in iterator.", iteratorSize >= actualSize);
		assertTrue("Too many items in iterator.", iteratorSize <= actualSize);
	}

	/**
	 * build a graph iterator with an explicit misterRogers
	 */
	private Iterator<GraphNode> buildGraphIterator1() {
		return new GraphIterator<GraphNode>(this.buildGraphRoot(), this.buildMisterRogers());
	}

	private GraphIterator.MisterRogers<GraphNode> buildMisterRogers() {
		return new GraphIterator.MisterRogers<GraphNode>() {
			public Iterator<GraphNode> neighbors(GraphNode next) {
				return next.neighbors();
			}
		};
	}

	/**
	 * build a graph iterator with an override
	 */
	private Iterator<GraphNode> buildGraphIterator2() {
		return new GraphIterator<GraphNode>(this.buildGraphRoot()) {
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

	private class GraphNode {
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
