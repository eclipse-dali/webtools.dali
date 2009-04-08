/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.iterators.TreeIterator;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TreeIteratorTests extends TestCase {
	/** this will be populated with all the nodes created for the test */
	Collection<TreeNode> nodes = new ArrayList<TreeNode>();

	public TreeIteratorTests(String name) {
		super(name);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testHasNext1() {
		this.verifyHasNext(this.buildTreeIterator1());
	}

	public void testHasNext2() {
		this.verifyHasNext(this.buildTreeIterator2());
	}

	private void verifyHasNext(Iterator<TreeNode> iterator) {
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(this.nodes.size(), i);
	}

	public void testNext1() {
		this.verifyNext(this.buildTreeIterator1());
	}

	public void testNext2() {
		this.verifyNext(this.buildTreeIterator2());
	}

	private void verifyNext(Iterator<TreeNode> iterator) {
		while (iterator.hasNext()) {
			assertTrue("bogus element", this.nodes.contains(iterator.next()));
		}
	}

	public void testNoSuchElementException1() {
		this.verifyNoSuchElementException(this.buildTreeIterator1());
	}

	public void testNoSuchElementException2() {
		this.verifyNoSuchElementException(this.buildTreeIterator2());
	}

	private void verifyNoSuchElementException(Iterator<TreeNode> iterator) {
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

	public void testRemove1() {
		this.verifyRemove(this.buildTreeIterator1());
	}

	public void testRemove2() {
		this.verifyRemove(this.buildTreeIterator2());
	}

	private void verifyRemove(Iterator<TreeNode> iterator) {
		String parentName = "child 2";
		String childName = "grandchild 2A";
		int startSize = this.childrenSize(parentName);
		while (iterator.hasNext()) {
			TreeNode node = iterator.next();
			if (node.getName().equals(childName)) {
				iterator.remove();
			}
		}
		int endSize = this.childrenSize(parentName);
		assertEquals(startSize - 1, endSize);
	}

	private int childrenSize(String nodeName) {
		for (Iterator<TreeNode> stream = this.nodes.iterator(); stream.hasNext();) {
			TreeNode node = stream.next();
			if (node.getName().equals(nodeName)) {
				return node.childrenSize();
			}
		}
		throw new IllegalArgumentException(nodeName);
	}

	/**
	 * build a tree iterator with an explicit midwife
	 */
	private Iterator<TreeNode> buildTreeIterator1() {
		return new TreeIterator<TreeNode>(this.buildTree(), this.buildMidwife());
	}

	private TreeIterator.Midwife<TreeNode> buildMidwife() {
		return new TreeIterator.Midwife<TreeNode>() {
			public Iterator<TreeNode> children(TreeNode next) {
				return next.children();
			}
		};
	}

	/**
	 * build a tree iterator with an override
	 */
	private Iterator<TreeNode> buildTreeIterator2() {
		return new TreeIterator<TreeNode>(this.buildTree()) {
			@Override
			public Iterator<TreeNode> children(TreeNode next) {
				return next.children();
			}
		};
	}

	public void testInvalidTreeIterator() {
		// missing method override
		Iterator<TreeNode> iterator = new TreeIterator<TreeNode>(this.buildTree());
		boolean exCaught = false;
		try {
			TreeNode tn = iterator.next();
			fail("invalid tree node: " + tn);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
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

	private class TreeNode {
		private String name;
		private Collection<TreeNode> children = new ArrayList<TreeNode>();

		public TreeNode(String name) {
			super();
			TreeIteratorTests.this.nodes.add(this); // log node
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
