/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.AbstractTreeNodeValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.NullListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.TreeModelAdapter;
import org.eclipse.jpt.common.utility.io.IndentingPrintWriter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.TreeNodeValueModel;
import org.eclipse.jpt.common.utility.tests.internal.model.Displayable;

@SuppressWarnings("nls")
public class TreeModelAdapterTests extends TestCase {
	boolean eventFired;

	public TreeModelAdapterTests(String name) {
		super(name);
	}

	public void testGetRoot() {
		TreeModel treeModel = this.buildSortedTreeModel();
		treeModel.addTreeModelListener(new TestTreeModelListener());
		TestNode rootNode = (TestNode) treeModel.getRoot();
		TestModel root = rootNode.getTestModel();
		assertEquals("root", root.getName());
//		root.dump();
//		rootNode.dump();
	}

	public void testGetChild() {
		TreeModel treeModel = this.buildSortedTreeModel();
		treeModel.addTreeModelListener(new TestTreeModelListener());
		TestNode rootNode = (TestNode) treeModel.getRoot();

		TestNode expected = rootNode.childNamed("node 1");
		TestNode actual = (TestNode) treeModel.getChild(rootNode, 1);
		assertEquals(expected, actual);

		expected = rootNode.childNamed("node 2");
		actual = (TestNode) treeModel.getChild(rootNode, 2);
		assertEquals(expected, actual);
	}

	public void testGetChildCount() {
		TreeModel treeModel = this.buildSortedTreeModel();
		treeModel.addTreeModelListener(new TestTreeModelListener());
		TestNode rootNode = (TestNode) treeModel.getRoot();

		assertEquals(5, treeModel.getChildCount(rootNode));

		TestNode node = rootNode.childNamed("node 1");
		assertEquals(1, treeModel.getChildCount(node));
	}

	public void testGetIndexOfChild() {
		TreeModel treeModel = this.buildSortedTreeModel();
		treeModel.addTreeModelListener(new TestTreeModelListener());
		TestNode rootNode = (TestNode) treeModel.getRoot();

		TestNode child = rootNode.childNamed("node 0");
		assertEquals(0, treeModel.getIndexOfChild(rootNode, child));

		child = rootNode.childNamed("node 1");
		assertEquals(1, treeModel.getIndexOfChild(rootNode, child));

		child = rootNode.childNamed("node 2");
		assertEquals(2, treeModel.getIndexOfChild(rootNode, child));
		TestNode grandchild = child.childNamed("node 2.2");
		assertEquals(2, treeModel.getIndexOfChild(child, grandchild));
	}

	public void testIsLeaf() {
		TreeModel treeModel = this.buildSortedTreeModel();
		treeModel.addTreeModelListener(new TestTreeModelListener());
		TestNode rootNode = (TestNode) treeModel.getRoot();
		assertFalse(treeModel.isLeaf(rootNode));
		TestNode node = rootNode.childNamed("node 1");
		assertFalse(treeModel.isLeaf(node));
		node = rootNode.childNamed("node 3");
		assertTrue(treeModel.isLeaf(node));
	}


	public void testTreeNodesChanged() {
		// the only way to trigger a "node changed" event is to use an unsorted tree;
		// a sorted tree will will trigger only "node removed" and "node inserted" events
		TreeModel treeModel = this.buildUnsortedTreeModel();
		this.eventFired = false;
		treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				TreeModelAdapterTests.this.eventFired = true;
			}
		});
		TestNode rootNode = (TestNode) treeModel.getRoot();
		TestNode node = rootNode.childNamed("node 1");
		TestModel tm = node.getTestModel();
		tm.setName("node 1++");
		assertTrue(this.eventFired);

		this.eventFired = false;
		node = node.childNamed("node 1.1");
		tm = node.getTestModel();
		tm.setName("node 1.1++");
		assertTrue(this.eventFired);
	}

	public void testTreeNodesInserted() {
		// use an unsorted tree so the nodes are not re-shuffled...
		TreeModel treeModel = this.buildUnsortedTreeModel();
		this.eventFired = false;
		treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				TreeModelAdapterTests.this.eventFired = true;
			}
		});
		TestNode rootNode = (TestNode) treeModel.getRoot();
		TestNode node = rootNode.childNamed("node 1");
		TestModel tm = node.getTestModel();
		tm.addChild("new child...");
		assertTrue(this.eventFired);

		this.eventFired = false;
		node = node.childNamed("node 1.1");
		tm = node.getTestModel();
		tm.addChild("another new child...");
		assertTrue(this.eventFired);
	}

	public void testTreeNodesRemoved() {
		TreeModel treeModel = this.buildUnsortedTreeModel();
		this.eventFired = false;
		treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				TreeModelAdapterTests.this.eventFired = true;
			}
		});
		TestNode rootNode = (TestNode) treeModel.getRoot();
		TestModel root = rootNode.getTestModel();
		root.removeChild(root.childNamed("node 3"));
		assertTrue(this.eventFired);

		this.eventFired = false;
		TestNode node = rootNode.childNamed("node 2");
		TestModel tm = node.getTestModel();
		tm.removeChild(tm.childNamed("node 2.2"));
		assertTrue(this.eventFired);
	}

	public void testTreeStructureChanged() {
		ModifiablePropertyValueModel<TreeNodeValueModel<Object>> nodeHolder = new SimplePropertyValueModel<TreeNodeValueModel<Object>>(this.buildSortedRootNode());
		TreeModel treeModel = this.buildTreeModel(nodeHolder);
		this.eventFired = false;
		treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				// do nothing
			}
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				// do nothing
			}
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
				TreeModelAdapterTests.this.eventFired = true;
			}
		});
		nodeHolder.setValue(this.buildUnsortedRootNode());
		assertTrue(this.eventFired);
	}

	/**
	 * Test a problem we had where removing a child from a tree would cause
	 * the {@link JTree} to call {@link Object#equals(Object)} on each node removed
	 * (actually, it was {@link javax.swing.tree.TreePath}, but that was because
	 * its own {@link javax.swing.tree.TreePath#equals(Object) equals} method was called by
	 * {@link JTree}); and since we had already removed the last listener from the
	 * aspect adapter, the aspect adapter would say its value was <code>null</code>;
	 * this would cause a NPE until we tweaked {@link TreeModelAdapter} to remove its
	 * listeners from a node only <em>after</em> the node had been completely
	 * removed from the {@link JTree}
	 * @see TreeModelAdapter#removeChildren(TreeNodeValueModel[] path, int[] childIndices, TreeNodeValueModel[] children)
	 * @see TreeModelAdapter#addChildren(TreeNodeValueModel[] path, int[] childIndices, TreeNodeValueModel[] children)
	 */
	public void testLazyInitialization() {
		TreeModel treeModel = this.buildSpecialTreeModel();
		JTree jTree = new JTree(treeModel);
		TestNode rootNode = (TestNode) treeModel.getRoot();
		TestModel root = rootNode.getTestModel();
		// this would cause a NPE:
		root.removeChild(root.childNamed("node 3"));
		assertEquals(treeModel, jTree.getModel());
	}


	private TreeModel buildSortedTreeModel() {
		return this.buildTreeModel(this.buildSortedRootNode());
	}

	private TestNode buildSortedRootNode() {
		return new SortedTestNode(this.buildRoot());
	}

	private TreeModel buildUnsortedTreeModel() {
		return this.buildTreeModel(this.buildUnsortedRootNode());
	}

	private TestNode buildUnsortedRootNode() {
		return new UnsortedTestNode(this.buildRoot());
	}

	private TreeModel buildSpecialTreeModel() {
		return this.buildTreeModel(this.buildSpecialRootNode());
	}

	private TestNode buildSpecialRootNode() {
		return new SpecialTestNode(this.buildRoot());
	}

	private TestModel buildRoot() {
		TestModel root = new TestModel("root");
		/*Node node_0 = */root.addChild("node 0");
		TestModel node_1 = root.addChild("node 1");
		TestModel node_1_1 = node_1.addChild("node 1.1");
		/*Node node_1_1_1 = */node_1_1.addChild("node 1.1.1");
		TestModel node_2 = root.addChild("node 2");
		/*Node node_2_0 = */node_2.addChild("node 2.0");
		/*Node node_2_1 = */node_2.addChild("node 2.1");
		/*Node node_2_2 = */node_2.addChild("node 2.2");
		/*Node node_2_3 = */node_2.addChild("node 2.3");
		/*Node node_2_4 = */node_2.addChild("node 2.4");
		/*Node node_2_5 = */node_2.addChild("node 2.5");
		/*Node node_3 = */root.addChild("node 3");
		/*Node node_4 = */root.addChild("node 4");
		return root;
	}


	// ********** member classes **********

	/**
	 * This is a typical model class with the typical change notifications
	 * for #name and #children.
	 */
	public static class TestModel extends AbstractModel {

		// the  parent is immutable; the root's parent is null
		private final TestModel parent;

		// the name is mutable; so I guess it isn't the "primary key" :-)
		private String name;
			public static final String NAME_PROPERTY = "name";

		private final Collection<TestModel> children;
			public static final String CHILDREN_COLLECTION = "children";


		public TestModel(String name) {	// root ctor
			this(null, name);
		}
		private TestModel(TestModel parent, String name) {
			super();
			this.parent = parent;
			this.name = name;
			this.children = new HashBag<TestModel>();
		}

		public TestModel getParent() {
			return this.parent;
		}

		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}

		public Iterator<TestModel> children() {
			return IteratorTools.readOnly(this.children.iterator());
		}
		public int childrenSize() {
			return this.children.size();
		}
		public TestModel addChild(String childName) {
			TestModel child = new TestModel(this, childName);
			this.addItemToCollection(child, this.children, CHILDREN_COLLECTION);
			return child;
		}
		public TestModel[] addChildren(String[] childNames) {
			TestModel[] newChildren = new TestModel[childNames.length];
			for (int i = 0; i < childNames.length; i++) {
				newChildren[i] = new TestModel(this, childNames[i]);
			}
			this.addItemsToCollection(newChildren, this.children, CHILDREN_COLLECTION);
			return newChildren;
		}
		public void removeChild(TestModel child) {
			this.removeItemFromCollection(child, this.children, CHILDREN_COLLECTION);
		}
		public void removeChildren(TestModel[] testModels) {
			this.removeItemsFromCollection(testModels, this.children, CHILDREN_COLLECTION);
		}
		public void clearChildren() {
			this.clearCollection(this.children, CHILDREN_COLLECTION);
		}
		public TestModel childNamed(String childName) {
			for (TestModel child : this.children) {
				if (child.getName().equals(childName)) {
					return child;
				}
			}
			throw new RuntimeException("child not found: " + childName);
		}

		public String dumpString() {
			StringWriter sw = new StringWriter();
			IndentingPrintWriter ipw = new IndentingPrintWriter(sw);
			this.dumpOn(ipw);
			return sw.toString();
		}
		public void dumpOn(IndentingPrintWriter writer) {
			writer.println(this);
			writer.indent();
			for (TestModel child : this.children) {
				child.dumpOn(writer);
			}
			writer.undent();
		}
		public void dumpOn(OutputStream stream) {
			IndentingPrintWriter writer = new IndentingPrintWriter(new OutputStreamWriter(stream));
			this.dumpOn(writer);
			writer.flush();
		}
		public void dump() {
			this.dumpOn(System.out);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.name);
		}

	}


	/**
	 * This Node wraps a TestModel and converts into something that can
	 * be used by TreeModelAdapter. It converts changes to the TestModel's
	 * name into "state changes" to the Node; and converts the
	 * TestModel's children into a ListValueModel of Nodes whose order is
	 * determined by subclass implementations.
	 */
	public static abstract class TestNode extends AbstractTreeNodeValueModel<Object> implements Displayable, Comparable<TestNode> {
		/** the model object wrapped by this node */
		private final TestModel testModel;
		/** this node's parent node; null for the root node */
		private final TestNode parent;
		/** this node's child nodes */
		private final ListValueModel<TreeNodeValueModel<Object>> childrenModel;
		/** a listener that notifies us when the model object's "internal state" changes */
		private final PropertyChangeListener testModelListener;


		// ********** constructors/initialization **********

		/**
		 * root node constructor
		 */
		public TestNode(TestModel testModel) {
			this(null, testModel);
		}

		/**
		 * branch or leaf node constructor
		 */
		public TestNode(TestNode parent, TestModel testModel) {
			super();
			this.parent = parent;
			this.testModel = testModel;
			this.childrenModel = this.buildChildrenModel(testModel);
			this.testModelListener = this.buildTestModelListener();
		}

		private PropertyChangeListener buildTestModelListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent e) {
					TestNode.this.testModelChanged(e);
				}
			};
		}

		/**
		 * subclasses decide the order of the child nodes
		 */
		protected abstract ListValueModel<TreeNodeValueModel<Object>> buildChildrenModel(TestModel model);

		/**
		 * used by subclasses;
		 * transform the test model children into nodes
		 */
		protected ListValueModel<TreeNodeValueModel<Object>> buildNodeAdapter(TestModel model) {
			return new TransformationListValueModel<TestModel, TreeNodeValueModel<Object>>(this.buildChildrenAdapter(model)) {
				@Override
				protected TestNode transformItem(TestModel item) {
					return TestNode.this.buildChildNode(item);
				}
			};
		}

		/**
		 * subclasses must build a concrete node for the specified test model
		 */
		protected abstract TestNode buildChildNode(TestModel childTestModel);

		/**
		 * return a collection value model on the specified model's children
		 */
		protected CollectionValueModel<TestModel> buildChildrenAdapter(TestModel model) {
			return new CollectionAspectAdapter<TestModel, TestModel>(TestModel.CHILDREN_COLLECTION, model) {
				@Override
				protected Iterator<TestModel> iterator_() {
					return this.subject.children();
				}
				@Override
				protected int size_() {
					return this.subject.childrenSize();
				}
			};
		}


		// ********** TreeNodeValueModel implementation **********

		public TestModel getValue() {
			return this.testModel;
		}

		public TreeNodeValueModel<Object> parent() {
			return this.parent;
		}

		public ListValueModel<TreeNodeValueModel<Object>> childrenModel() {
			return this.childrenModel;
		}


		// ********** AbstractTreeNodeValueModel implementation **********

		@Override
		protected void engageValue() {
			this.testModel.addPropertyChangeListener(TestModel.NAME_PROPERTY, this.testModelListener);
		}

		@Override
		protected void disengageValue() {
			this.testModel.removePropertyChangeListener(TestModel.NAME_PROPERTY, this.testModelListener);
		}


		// ********** Displayable implementation **********

		public String displayString() {
			return this.testModel.getName();
		}

		public Icon icon() {
			return null;
		}


		// ********** debugging support **********

		public String dumpString() {
			StringWriter sw = new StringWriter();
			IndentingPrintWriter ipw = new IndentingPrintWriter(sw);
			this.dumpOn(ipw);
			return sw.toString();
		}

		public void dumpOn(IndentingPrintWriter writer) {
			writer.println(this);
			writer.indent();
			for (Iterator<TreeNodeValueModel<Object>> stream = this.childrenModel.iterator(); stream.hasNext(); ) {
				// cast to a TestNode (i.e. this won't work with a NameTestNode in the tree)
				((TestNode) stream.next()).dumpOn(writer);
			}
			writer.undent();
		}

		public void dumpOn(OutputStream stream) {
			IndentingPrintWriter writer = new IndentingPrintWriter(new OutputStreamWriter(stream));
			this.dumpOn(writer);
			writer.flush();
		}

		public void dump() {
			this.dumpOn(System.out);
		}


		// ********** behavior **********

		/**
		 * the model's name has changed, forward the event to our listeners
		 */
		protected void testModelChanged(PropertyChangeEvent e) {
			// we need to notify listeners that our "internal state" has changed
			this.fireStateChanged();
			// our display string stays in synch with the model's name
			this.firePropertyChanged(DISPLAY_STRING_PROPERTY, e.getOldValue(), e.getNewValue());
		}


		// ********** queries **********

		public TestModel getTestModel() {
			return this.testModel;
		}

		/**
		 * testing convenience method
		 */
		public TestNode childNamed(String name) {
			for (Iterator<TreeNodeValueModel<Object>> stream = this.childrenModel.iterator(); stream.hasNext(); ) {
				TreeNodeValueModel<Object> childNode = stream.next();
				if (childNode instanceof TestNode) {
					if (((TestNode) childNode).getTestModel().getName().equals(name)) {
						return (TestNode) childNode;
					}
				}
			}
			throw new IllegalArgumentException("child not found: " + name);
		}


		// ********** standard methods **********

		public int compareTo(TestNode o) {
			return this.displayString().compareTo(o.displayString());
		}

		@Override
		public String toString() {
			return "Node(" + this.testModel + ")";
		}

	}

	/**
	 * concrete implementation that keeps its children sorted
	 */
	public static class SortedTestNode extends TestNode {

		// ********** constructors **********
		public SortedTestNode(TestModel testModel) {
			super(testModel);
		}
		public SortedTestNode(TestNode parent, TestModel testModel) {
			super(parent, testModel);
		}

		// ********** initialization **********
		/** the list should be sorted */
		@Override
		protected ListValueModel<TreeNodeValueModel<Object>> buildChildrenModel(TestModel testModel) {
			return new SortedListValueModelWrapper<TreeNodeValueModel<Object>>(this.buildDisplayStringAdapter(testModel));
		}
		/** the display string (name) of each node can change */
		protected ListValueModel<TreeNodeValueModel<Object>> buildDisplayStringAdapter(TestModel testModel) {
			return new ItemPropertyListValueModelAdapter<TreeNodeValueModel<Object>>(this.buildNodeAdapter(testModel), DISPLAY_STRING_PROPERTY);
		}
		/** children are also sorted nodes */
		@Override
		protected TestNode buildChildNode(TestModel childNode) {
			return new SortedTestNode(this, childNode);
		}

	}


	/**
	 * concrete implementation that leaves its children unsorted
	 */
	public static class UnsortedTestNode extends TestNode {

		// ********** constructors **********
		public UnsortedTestNode(TestModel testModel) {
			super(testModel);
		}
		public UnsortedTestNode(TestNode parent, TestModel testModel) {
			super(parent, testModel);
		}

		// ********** initialization **********
		/** the list should NOT be sorted */
		@Override
		protected ListValueModel<TreeNodeValueModel<Object>> buildChildrenModel(TestModel testModel) {
			return this.buildNodeAdapter(testModel);
		}
		/** children are also unsorted nodes */
		@Override
		protected TestNode buildChildNode(TestModel childNode) {
			return new UnsortedTestNode(this, childNode);
		}

	}


	/**
	 * concrete implementation that leaves its children unsorted
	 * and has a special set of children for "node 3"
	 */
	public static class SpecialTestNode extends UnsortedTestNode {

		// ********** constructors **********
		public SpecialTestNode(TestModel testModel) {
			super(testModel);
		}
		public SpecialTestNode(TestNode parent, TestModel testModel) {
			super(parent, testModel);
		}

		// ********** initialization **********
		/** return a different list of children for "node 3" */
		@Override
		protected ListValueModel<TreeNodeValueModel<Object>> buildChildrenModel(TestModel testModel) {
			if (testModel.getName().equals("node 3")) {
				return this.buildSpecialChildrenModel();
			}
			return super.buildChildrenModel(testModel);
		}
		protected ListValueModel<TreeNodeValueModel<Object>> buildSpecialChildrenModel() {
			TreeNodeValueModel<Object>[] children = new NameTestNode[1];
			children[0] = new NameTestNode(this);
			return new SimpleListValueModel<TreeNodeValueModel<Object>>(Arrays.asList(children));
		}
		/** children are also special nodes */
		@Override
		protected TestNode buildChildNode(TestModel childNode) {
			return new SpecialTestNode(this, childNode);
		}

	}


	public static class NameTestNode extends AbstractTreeNodeValueModel<Object> {
		private final ModifiablePropertyValueModel<String> nameAdapter;
		private final SpecialTestNode specialNode;		// parent node
		private final PropertyChangeListener nameListener;
		private final ListValueModel<TreeNodeValueModel<Object>> childrenModel;

		// ********** construction/initialization **********

		public NameTestNode(SpecialTestNode specialNode) {
			super();
			this.nameListener = this.buildNameListener();
			this.specialNode = specialNode;
			this.nameAdapter = this.buildNameAdapter();
			this.childrenModel = new NullListValueModel<TreeNodeValueModel<Object>>();
		}
		protected PropertyChangeListener buildNameListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent e) {
					NameTestNode.this.nameChanged(e);
				}
			};
		}
		protected ModifiablePropertyValueModel<String> buildNameAdapter() {
			return new PropertyAspectAdapter<TestModel, String>(TestModel.NAME_PROPERTY, this.getTestModel()) {
				@Override
				protected String buildValue_() {
					return this.subject.getName();
				}
				@Override
				protected void setValue_(String value) {
					this.subject.setName(value);
				}
			};
		}

		public TestModel getTestModel() {
			return this.specialNode.getTestModel();
		}

		// ********** TreeNodeValueModel implementation **********

		public String getValue() {
			return this.nameAdapter.getValue();
		}
		@Override
		public void setValue(Object value) {
			this.nameAdapter.setValue((String) value);
		}
		public TreeNodeValueModel<Object> parent() {
			return this.specialNode;
		}
		public ListValueModel<TreeNodeValueModel<Object>> childrenModel() {
			return this.childrenModel;
		}

		// ********** AbstractTreeNodeValueModel implementation **********

		@Override
		protected void engageValue() {
			this.nameAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.nameListener);
		}
		@Override
		protected void disengageValue() {
			this.nameAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.nameListener);
		}

		// ********** behavior **********

		protected void nameChanged(PropertyChangeEvent e) {
			// we need to notify listeners that our "value" has changed
			this.firePropertyChanged(VALUE, e.getOldValue(), e.getNewValue());
		}
	}

	private TreeModel buildTreeModel(TestNode root) {
		return this.buildTreeModel(new StaticPropertyValueModel<TreeNodeValueModel<Object>>(root));
	}

	private TreeModel buildTreeModel(PropertyValueModel<TreeNodeValueModel<Object>> rootHolder) {
		return new TreeModelAdapter<Object>(rootHolder) {
			@Override
			protected ListChangeListener buildChildrenListener() {
				return this.buildChildrenListener_();
			}
			@Override
			protected StateChangeListener buildNodeStateListener() {
				return this.buildNodeStateListener_();
			}
			@Override
			protected PropertyChangeListener buildNodeValueListener() {
				return this.buildNodeValueListener_();
			}
			@Override
			protected PropertyChangeListener buildRootListener() {
				return this.buildRootListener_();
			}
		};
	}



	/**
	 * listener that will blow up with any event;
	 * override and implement expected event methods
	 */
	public class TestTreeModelListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			fail("unexpected event");
		}
		public void treeNodesInserted(TreeModelEvent e) {
			fail("unexpected event");
		}
		public void treeNodesRemoved(TreeModelEvent e) {
			fail("unexpected event");
		}
		public void treeStructureChanged(TreeModelEvent e) {
			fail("unexpected event");
		}
	}

}
