/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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

	public void testHasNext() {
		Iterator<TreeNode> iterator = this.buildTreeIterator();
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(this.nodes.size(), i);
	}

	public void testNext() {
		Iterator<TreeNode> iterator = this.buildTreeIterator();
		while (iterator.hasNext()) {
			assertTrue("bogus element", this.nodes.contains(iterator.next()));
		}
	}

	public void testNoSuchElementException() {
		Iterator<TreeNode> iterator = this.buildTreeIterator();
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

	public void testRemove() {
		Iterator<TreeNode> iterator = this.buildTreeIterator();
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
	private Iterator<TreeNode> buildTreeIterator() {
		return IteratorTools.treeIterator(this.buildTree(), this.buildTransformer());
	}

	private Transformer<TreeNode, Iterator<? extends TreeNode>> buildTransformer() {
		return CHILDREN_TRANSFORMER;
	}

	private TreeNode buildTree() {
		TreeNode root = new TreeNode("root");
		TreeNode child1 = new TreeNode(root, "child 1");
		@SuppressWarnings("unused")
		TreeNode grandchild1A = new TreeNode(child1, "grandchild 1A");
		TreeNode child2 = new TreeNode(root, "child 2");
		@SuppressWarnings("unused")
		TreeNode grandchild2A = new TreeNode(child2, "grandchild 2A");
		TreeNode grandchild2B = new TreeNode(child2, "grandchild 2B");
		@SuppressWarnings("unused")
		TreeNode greatGrandchild2B1 = new TreeNode(grandchild2B, "great-grandchild 2B1");
		@SuppressWarnings("unused")
		TreeNode greatGrandchild2B2 = new TreeNode(grandchild2B, "great-grandchild 2B2");
		TreeNode grandchild2C = new TreeNode(child2, "grandchild 2C");
		@SuppressWarnings("unused")
		TreeNode greatGrandchild2C1 = new TreeNode(grandchild2C, "great-grandchild 2C1");
		@SuppressWarnings("unused")
		TreeNode child3 = new TreeNode(root, "child 3");
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

	private static Transformer<TreeNode, Iterator<? extends TreeNode>> CHILDREN_TRANSFORMER = new ChildrenTransformer();
	protected static class ChildrenTransformer
		extends TransformerAdapter<TreeNode, Iterator<? extends TreeNode>>
	{
		@Override
		public Iterator<? extends TreeNode> transform(TreeNode node) {
			return node.children();
		}
	}
}
