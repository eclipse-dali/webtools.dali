/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterables.TreeIterable;
import org.eclipse.jpt.common.utility.internal.iterators.TreeIterator;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TreeIterableTests extends TestCase {
	/** this will be populated with all the nodes created for the test */
	Collection<TreeNode> nodes = new ArrayList<TreeNode>();

	public TreeIterableTests(String name) {
		super(name);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator1() {
		for (TreeNode tn : this.buildTreeIterable1()) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testIterator2() {
		for (TreeNode tn : this.buildTreeIterable2()) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testMidwife1() {
		for (TreeNode tn : new TreeIterable<TreeNode>(this.buildTree(), this.buildMidwife())) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testMidwife2() {
		for (TreeNode tn : new TreeIterable<TreeNode>(new TreeNode[] { this.buildTree() }, this.buildMidwife())) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testToString() {
		assertNotNull(this.buildTreeIterable1().toString());
	}

	public void testMissingMidwife() {
		boolean exCaught = false;
		try {
			for (TreeNode tn : new TreeIterable<TreeNode>(this.buildTree())) {
				assertTrue(this.nodes.contains(tn));
			}
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private Iterable<TreeNode> buildTreeIterable1() {
		return new TreeIterable<TreeNode>(this.buildTree()) {
			@Override
			public Iterator<TreeNode> children(TreeNode next) {
				return next.children();
			}
		};
	}

	private Iterable<TreeNode> buildTreeIterable2() {
		return new TreeIterable<TreeNode>(new TreeNode[] { this.buildTree() }) {
			@Override
			public Iterator<TreeNode> children(TreeNode next) {
				return next.children();
			}
		};
	}

	private TreeIterator.Midwife<TreeNode> buildMidwife() {
		return new TreeIterator.Midwife<TreeNode>() {
			public Iterator<TreeNode> children(TreeNode next) {
				return next.children();
			}
		};
	}

	private TreeNode buildTree() {
		TreeNode root = new TreeNode("root");
		TreeNode child1 = new TreeNode(root, "child 1");
		new TreeNode(child1, "grandchild 1A");
		TreeNode child2 = new TreeNode(root, "child 2");
		new TreeNode(child2, "grandchild 2A");
		TreeNode grandchild2B = new TreeNode(child2, "grandchild 2B");
		new TreeNode(grandchild2B, "great-grandchild 2B1");
		new TreeNode(grandchild2B, "great-grandchild 2B2");
		TreeNode grandchild2C = new TreeNode(child2, "grandchild 2C");
		new TreeNode(grandchild2C, "great-grandchild 2C1");
		new TreeNode(root, "child 3");
		return root;
	}

	public class TreeNode {
		private String name;
		private Collection<TreeNode> children = new ArrayList<TreeNode>();

		public TreeNode(String name) {
			super();
			TreeIterableTests.this.nodes.add(this); // log node
			this.name = name;
		}

		public TreeNode(TreeNode parent, String name) {
			this(name);
			parent.addChild(this);
		}

		public String getName() {
			return this.name;
		}

		private void addChild(TreeNode child) {
			this.children.add(child);
		}

		public Iterator<TreeNode> children() {
			return this.children.iterator();
		}

		public int childrenSize() {
			return this.children.size();
		}

		@Override
		public String toString() {
			return "TreeNode(" + this.name + ")";
		}
	}

}
