/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.swing.PrimitiveListTreeModel;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
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
			@Override
			protected ListChangeListener buildListChangeListener() {
				return this.buildListChangeListener_();
			}
		};
	}

	private ListValueModel<?> buildListValueModel() {
		return new ListAspectAdapter<TestModel, String>(TestModel.NAMES_LIST, this.testModel) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.names();
			}
			@Override
			public String get(int index) {
				return this.subject.getName(index);
			}
			@Override
			public int size() {
				return this.subject.namesSize();
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

	class TestModel extends AbstractModel {
		private final List<String> names;
			static final String NAMES_LIST = "names";
	
		TestModel() {
			super();
			this.names = new ArrayList<String>();
		}
	
		public ListIterator<String> names() {
			return IteratorTools.readOnly(this.names.listIterator());
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
		public void addNames(int index, List<String> list) {
			this.addItemsToList(index, this.names, list, NAMES_LIST);
		}
		public void addNames(List<String> list) {
			this.addNames(this.namesSize(), list);
		}
		public String removeName(int index) {
			return this.removeItemFromList(index, this.names, NAMES_LIST);
		}
		public List<String> removeNames(int index, int length) {
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
