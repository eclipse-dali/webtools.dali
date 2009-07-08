/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ReflectiveChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

@SuppressWarnings("nls")
public class ReflectiveTreeChangeListenerTests extends TestCase {
	
	public ReflectiveTreeChangeListenerTests(String name) {
		super(name);
	}

	private TreeChangeListener buildZeroArgumentListener(Object target) {
		return ReflectiveChangeListener.buildTreeChangeListener(target, "nodeAddedZeroArgument", "nodeRemovedZeroArgument", "treeClearedZeroArgument", "treeChangedZeroArgument");
	}

	private TreeChangeListener buildSingleArgumentListener(Object target) {
		return ReflectiveChangeListener.buildTreeChangeListener(target, "nodeAddedSingleArgument", "nodeRemovedSingleArgument", "treeClearedSingleArgument", "treeChangedSingleArgument");
	}

	public void testNodeAddedZeroArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildZeroArgumentListener(target));
		testModel.addNode("root", "child");
		assertTrue(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testNodeAddedSingleArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildSingleArgumentListener(target));
		testModel.addNode("root", "child");
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertTrue(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testNodeRemovedZeroArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildZeroArgumentListener(target));
		testModel.removeNode("child");
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertTrue(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testNodeRemovedSingleArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildSingleArgumentListener(target));
		testModel.removeNode("child");
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertTrue(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testTreeClearedZeroArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		testModel.addNode("child", "grandchild");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[0]);
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildZeroArgumentListener(target));
		testModel.clearTree();
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertTrue(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testTreeClearedSingleArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		testModel.addNode("child", "grandchild");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[0]);
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildSingleArgumentListener(target));
		testModel.clearTree();
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertTrue(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testTreeChangedZeroArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "another child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildZeroArgumentListener(target));
		testModel.replaceNode("child", "another child");
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertTrue(target.treeChangedZeroArgumentFlag);
		assertFalse(target.treeChangedSingleArgumentFlag);
	}

	public void testTreeChangedSingleArgumentNamedTree() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "another child"});
		testModel.addTreeChangeListener(TestModel.STRINGS_TREE, this.buildSingleArgumentListener(target));
		testModel.replaceNode("child", "another child");
		assertFalse(target.nodeAddedZeroArgumentFlag);
		assertFalse(target.nodeAddedSingleArgumentFlag);
		assertFalse(target.nodeRemovedZeroArgumentFlag);
		assertFalse(target.nodeRemovedSingleArgumentFlag);
		assertFalse(target.treeClearedZeroArgumentFlag);
		assertFalse(target.treeClearedSingleArgumentFlag);
		assertFalse(target.treeChangedZeroArgumentFlag);
		assertTrue(target.treeChangedSingleArgumentFlag);
	}

	public void testListenerMismatch() {
		TestModel testModel = new TestModel("root");
		testModel.addNode("root", "child");
		Target target = new Target(testModel, TestModel.STRINGS_TREE, new String[]{"root", "child"});
		// build a TREE change listener and hack it so we
		// can add it as a COLLECTION change listener
		Object listener = ReflectiveChangeListener.buildTreeChangeListener(target, "treeEventSingleArgument");
		testModel.addCollectionChangeListener("bogus collection", (CollectionChangeListener) listener);

		boolean exCaught = false;
		try {
			testModel.changeCollection();
			fail("listener mismatch: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	class TestModel extends AbstractModel {
		private final String root;
		private Map<String, Collection<String>> childrenLists = new HashMap<String, Collection<String>>();
		private Map<String, String> parents = new HashMap<String, String>();
			public static final String STRINGS_TREE = "strings";
		TestModel(String root) {
			super();
			if (root == null) {
				throw new NullPointerException();
			}
			this.root = root;
			this.childrenLists.put(root, new ArrayList<String>());
			this.parents.put(root, null);
		}
		String getRoot() {
			return this.root;
		}
		private List<String> path(String node) {
			String temp = node;
			List<String> reversePath = new ArrayList<String>();
			do {
				reversePath.add(temp);
				temp = this.parents.get(temp);
			} while (temp != null);
			return CollectionTools.reverse(reversePath);
		}
		Iterator<String> strings() {
			return new CloneIterator<String>(this.childrenLists.keySet()) {
				@Override
				protected void remove(String s) {
					TestModel.this.removeNode(s);
				}
			};
		}
		void addNode(String parent, String child) {
			if ((parent == null) || (child == null)) {
				throw new NullPointerException();
			}

			Collection<String> children = this.childrenLists.get(parent);
			if (children == null) {
				throw new IllegalStateException("cannot add a child to a non-existent parent");
			}

			if (this.childrenLists.get(child) != null) {
				throw new IllegalStateException("cannot add a child that is already in the tree");
			}
			
			children.add(child);
			this.childrenLists.put(child, new ArrayList<String>());
			this.parents.put(child, parent);
			this.fireNodeAdded(STRINGS_TREE, this.path(child));
		}
		void removeNode(String node) {
			if (node == null) {
				throw new NullPointerException();
			}

			Collection<String> children = this.childrenLists.get(node);
			if (children == null) {
				throw new IllegalStateException("node is not in tree");
			}
			List<String> path = this.path(node);
			for (String s : children) {
				this.removeNode(s);
			}
			this.childrenLists.remove(node);
			this.parents.remove(node);
			this.fireNodeRemoved(STRINGS_TREE, path);
		}
		void replaceNode(String oldNode, String newNode) {
			if ((oldNode == null) || (newNode == null)) {
				throw new NullPointerException();
			}

			Collection<String> children = this.childrenLists.remove(oldNode);
			if (children == null) {
				throw new IllegalStateException("old node is not in tree");
			}
			this.childrenLists.put(newNode, children);
			for (String child : children) {
				this.parents.put(child, newNode);
			}

			String parent = this.parents.remove(oldNode);
			this.parents.put(newNode, parent);

			this.fireTreeChanged(STRINGS_TREE, this.path(newNode));
		}
		void clearTree() {
			this.childrenLists.clear();
			this.childrenLists.put(root, new ArrayList<String>());
			this.parents.clear();
			this.parents.put(root, null);
			this.fireTreeCleared(STRINGS_TREE);
		}
		void changeCollection() {
			this.fireCollectionChanged("bogus collection", Collections.emptySet());
		}
	}

	class Target {
		TestModel testModel;
		String treeName;
		List<String> path;
		boolean nodeAddedZeroArgumentFlag = false;
		boolean nodeAddedSingleArgumentFlag = false;
		boolean nodeRemovedZeroArgumentFlag = false;
		boolean nodeRemovedSingleArgumentFlag = false;
		boolean treeClearedZeroArgumentFlag = false;
		boolean treeClearedSingleArgumentFlag = false;
		boolean treeChangedZeroArgumentFlag = false;
		boolean treeChangedSingleArgumentFlag = false;
		boolean treeEventSingleArgumentFlag = false;
		Target(TestModel testModel, String treeName, String[] path) {
			super();
			this.testModel = testModel;
			this.treeName = treeName;
			this.path = Arrays.asList(path);
		}
		void nodeAddedZeroArgument() {
			this.nodeAddedZeroArgumentFlag = true;
		}
		void nodeAddedSingleArgument(TreeAddEvent e) {
			this.nodeAddedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.treeName, e.getTreeName());
			assertEquals(this.path, CollectionTools.list(e.getPath()));
		}
		void nodeRemovedZeroArgument() {
			this.nodeRemovedZeroArgumentFlag = true;
		}
		void nodeRemovedSingleArgument(TreeRemoveEvent e) {
			this.nodeRemovedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.treeName, e.getTreeName());
			assertEquals(this.path, CollectionTools.list(e.getPath()));
		}
		void treeClearedZeroArgument() {
			this.treeClearedZeroArgumentFlag = true;
		}
		void treeClearedSingleArgument(TreeClearEvent e) {
			this.treeClearedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.treeName, e.getTreeName());
		}
		void treeChangedZeroArgument() {
			this.treeChangedZeroArgumentFlag = true;
		}
		void treeChangedSingleArgument(TreeChangeEvent e) {
			this.treeChangedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.treeName, e.getTreeName());
		}
		void treeEventSingleArgument(TreeEvent e) {
			this.treeChangedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.treeName, e.getTreeName());
		}
		void collectionChangedDoubleArgument(TreeChangeEvent e, Object o) {
			fail("bogus event: " + e + " - object: " + o);
		}
	}

}
