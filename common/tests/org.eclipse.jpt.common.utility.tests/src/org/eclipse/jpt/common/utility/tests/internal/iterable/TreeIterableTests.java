/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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
		for (TreeNode tn : ObjectTools.tree(this.buildTree(), this.buildTransformer())) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testIterator2() {
		for (TreeNode tn : IterableTools.treeIterable(new TreeNode[] { this.buildTree() }, this.buildTransformer())) {
			assertTrue(this.nodes.contains(tn));
		}
	}

	public void testToString() {
		assertNotNull(IterableTools.treeIterable(this.buildTree(), this.buildTransformer()).toString());
	}

	private Transformer<TreeNode, Iterable<? extends TreeNode>> buildTransformer() {
		return new Transformer<TreeNode, Iterable<? extends TreeNode>>() {
			public Iterable<? extends TreeNode> transform(TreeNode next) {
				return next.getChildren();
			}
		};
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

		public Iterable<TreeNode> getChildren() {
			return this.children;
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
