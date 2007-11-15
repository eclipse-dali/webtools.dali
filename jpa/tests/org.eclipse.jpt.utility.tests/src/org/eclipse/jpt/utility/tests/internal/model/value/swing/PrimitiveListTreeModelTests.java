/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.PrimitiveListTreeModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class PrimitiveListTreeModelTests extends TestCase {
	TestModel testModel;
	private TreeModel treeModel;

	public PrimitiveListTreeModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testModel = this.buildTestModel();
		this.treeModel = this.buildTreeModel();
	}

	private TestModel buildTestModel() {
		return new TestModel();
	}

	private TreeModel buildTreeModel() {
		return new PrimitiveListTreeModel(this.buildListValueModel()) {
			@Override
			protected void primitiveChanged(int index, Object newValue) {
				if ( ! newValue.equals("")) {
					PrimitiveListTreeModelTests.this.testModel.replaceName(index, (String) newValue);
				}
			}
		};
	}

	private ListValueModel buildListValueModel() {
		return new ListAspectAdapter(TestModel.NAMES_LIST, this.testModel) {
			@Override
			protected ListIterator getValueFromSubject() {
				return ((TestModel) this.subject).names();
			}
			@Override
			public Object get(int index) {
				return ((TestModel) this.subject).getName(index);
			}
			@Override
			public int size() {
				return ((TestModel) this.subject).namesSize();
			}
			public void add(int index, Object item) {
				((TestModel) this.subject).addName(index, (String) item);
			}
			public void addAll(int index, List items) {
				((TestModel) this.subject).addNames(index, items);
			}
			public Object remove(int index) {
				return ((TestModel) this.subject).removeName(index);
			}
			public List remove(int index, int length) {
				return ((TestModel) this.subject).removeNames(index, length);
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAddPrimitive() {
		this.treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
				PrimitiveListTreeModelTests.this.verifyTreeModelEvent(e, new int[] {0}, new String[] {"foo"});
			}
		});
		this.testModel.addName("foo");
	}

	public void testRemovePrimitive() {
		this.testModel.addName("foo");
		this.testModel.addName("bar");
		this.testModel.addName("baz");
		this.treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				PrimitiveListTreeModelTests.this.verifyTreeModelEvent(e, new int[] {1}, new String[] {"bar"});
			}
		});
		String name = this.testModel.removeName(1);
		assertEquals("bar", name);
	}

	public void testReplacePrimitive() {
		this.testModel.addName("foo");
		this.testModel.addName("bar");
		this.testModel.addName("baz");
		this.treeModel.addTreeModelListener(new TestTreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				PrimitiveListTreeModelTests.this.verifyTreeModelEvent(e, new int[] {1}, new String[] {"jar"});
			}
		});
		String name = this.testModel.replaceName(1, "jar");
		assertEquals("bar", name);
	}

	void verifyTreeModelEvent(TreeModelEvent e, int[] expectedChildIndices, String[] expectedNames) {
		assertTrue(Arrays.equals(expectedChildIndices, e.getChildIndices()));
		Object[] actualChildren = e.getChildren();
		assertEquals(expectedNames.length, actualChildren.length);
		for (int i = 0; i < expectedNames.length; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) actualChildren[i];
			assertEquals(expectedNames[i], node.getUserObject());
		}
		assertEquals(1, e.getPath().length);
		assertEquals(this.treeModel.getRoot(), e.getPath()[0]);
		assertEquals(this.treeModel, e.getSource());
	}


// ********** inner classes **********

	private class TestModel extends AbstractModel {
		private final List<String> names;
			static final String NAMES_LIST = "names";
	
		TestModel() {
			super();
			this.names = new ArrayList<String>();
		}
	
		public ListIterator<String> names() {
			return new ReadOnlyListIterator<String>(this.names);
		}
		public int namesSize() {
			return this.names.size();
		}
		public String getName(int index) {
			return this.names.get(index);
		}
		public void addName(int index, String name) {
			this.addItemToList(index, name, this.names, NAMES_LIST);
		}
		public void addName(String name) {
			this.addName(this.namesSize(), name);
		}
		public void addNames(int index, List list) {
			this.addItemsToList(index, this.names, list, NAMES_LIST);
		}
		public void addNames(List list) {
			this.addNames(this.namesSize(), list);
		}
		public String removeName(int index) {
			return (String) this.removeItemFromList(index, this.names, NAMES_LIST);
		}
		public List removeNames(int index, int length) {
			return this.removeItemsFromList(index, length, this.names, NAMES_LIST);
		}
		public String replaceName(int index, String newName) {
			return this.setItemInList(index, newName, this.names, NAMES_LIST);
		}
	}
	
	
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
