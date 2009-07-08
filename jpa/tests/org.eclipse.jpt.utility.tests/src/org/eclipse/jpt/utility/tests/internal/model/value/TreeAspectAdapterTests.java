/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.iterators.TreeIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TreeAspectAdapter;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.TreeValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TreeAspectAdapterTests extends TestCase {
	private TestSubject subject1;
	private WritablePropertyValueModel<TestSubject> subjectHolder1;
	private TreeAspectAdapter<TestSubject, List<TestNode>> aa1;
	private TreeEvent event1;
	private TreeChangeListener listener1;

	private TestSubject subject2;

	
	public TreeAspectAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject();
		TestNode root, node;

		root = this.subject1.getRootNameNode();
		node = this.subject1.addName(root, "name 1.1");
		this.subject1.addName(node, "name 1.1.1");
		this.subject1.addName(node, "name 1.1.2");
		node = this.subject1.addName(root, "name 1.2");
		this.subject1.addName(node, "name 1.2.1");
		node = this.subject1.addName(root, "name 1.3");

		root = this.subject1.getRootDescriptionNode();
		node = this.subject1.addDescription(root, "description 1.1");
		this.subject1.addDescription(node, "description 1.1.1");
		this.subject1.addDescription(node, "description 1.1.2");
		node = this.subject1.addDescription(root, "description 1.2");
		this.subject1.addDescription(node, "description 1.2.1");
		node = this.subject1.addDescription(root, "description 1.3");

		this.subjectHolder1 = new SimplePropertyValueModel<TestSubject>(this.subject1);
		this.aa1 = this.buildAspectAdapter(this.subjectHolder1);
		this.listener1 = this.buildValueChangeListener1();
		this.aa1.addTreeChangeListener(TreeValueModel.NODES, this.listener1);
		this.event1 = null;

		this.subject2 = new TestSubject();

		root = this.subject2.getRootNameNode();
		node = this.subject2.addName(root, "name 2.1");
		this.subject2.addName(node, "name 2.1.1");
		this.subject2.addName(node, "name 2.1.2");
		node = this.subject2.addName(root, "name 2.2");
		this.subject2.addName(node, "name 2.2.1");
		node = this.subject2.addName(root, "name 2.3");

		root = this.subject2.getRootDescriptionNode();
		node = this.subject2.addDescription(root, "description 2.1");
		this.subject2.addDescription(node, "description 2.1.1");
		this.subject2.addDescription(node, "description 2.1.2");
		node = this.subject2.addDescription(root, "description 2.2");
		this.subject2.addDescription(node, "description 2.2.1");
		node = this.subject2.addDescription(root, "description 2.3");
	}

	private TreeAspectAdapter<TestSubject, List<TestNode>> buildAspectAdapter(PropertyValueModel<TestSubject> subjectHolder) {
		return new TreeAspectAdapter<TestSubject, List<TestNode>>(subjectHolder, TestSubject.NAMES_TREE) {
			// this is not a typical aspect adapter - the value is determined by the aspect name
			@Override
			protected Iterator<List<TestNode>> nodes_() {
				if (this.treeNames[0] == TestSubject.NAMES_TREE) {
					return this.subject.namePaths();
				}
				if (this.treeNames[0] == TestSubject.DESCRIPTIONS_TREE) {
					return this.subject.descriptionPaths();
				}
				throw new IllegalStateException("invalid aspect name: " + this.treeNames[0]);
			}
		};
	}

	private ChangeListener buildValueChangeListener1() {
		return new ChangeAdapter() {
			@Override
			public void nodeAdded(TreeAddEvent e) {
				TreeAspectAdapterTests.this.value1Changed(e);
			}
			@Override
			public void nodeRemoved(TreeRemoveEvent e) {
				TreeAspectAdapterTests.this.value1Changed(e);
			}
			@Override
			public void treeCleared(TreeClearEvent e) {
				TreeAspectAdapterTests.this.value1Changed(e);
			}
			@Override
			public void treeChanged(TreeChangeEvent e) {
				TreeAspectAdapterTests.this.value1Changed(e);
			}
		};
	}

	void value1Changed(TreeEvent e) {
		this.event1 = e;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSubjectHolder() {
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(TreeValueModel.NODES, this.event1.getTreeName());
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(TreeValueModel.NODES, this.event1.getTreeName());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(TreeValueModel.NODES, this.event1.getTreeName());
	}

	public void testTreeStructureChange() {
		assertNull(this.event1);

		this.subject1.addTwoNames(this.subject1.getRootNameNode(), "jam", "jaz");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(TreeValueModel.NODES, this.event1.getTreeName());
		assertTrue(this.subject1.containsNameNode("jam"));
		assertTrue(this.subject1.containsNameNode("jaz"));
	}

	public void testNodes() {
		assertEquals(this.convertToNames(this.subject1.namePaths()), this.convertToNames(this.aa1.nodes()));
	}

	private Collection<String> convertToNames(Iterator<List<TestNode>> namePaths) {
		Collection<String> result = new HashBag<String>();
		while (namePaths.hasNext()) {
			List<TestNode> path = namePaths.next();
			StringBuffer sb = new StringBuffer();
			sb.append('[');
			for (int i = 0; i < path.size(); i++) {
				sb.append(path.get(i).getText());
				if (i < path.size() - 1) {
					sb.append(':');
				}
			}
			sb.append(']');
			result.add(sb.toString());
		}
		return result;
	}

	public void testHasListeners() {
		assertTrue(this.aa1.hasAnyTreeChangeListeners(TreeValueModel.NODES));
		assertTrue(this.subject1.hasAnyTreeChangeListeners(TestSubject.NAMES_TREE));
		this.aa1.removeTreeChangeListener(TreeValueModel.NODES, this.listener1);
		assertFalse(this.subject1.hasAnyTreeChangeListeners(TestSubject.NAMES_TREE));
		assertFalse(this.aa1.hasAnyTreeChangeListeners(TreeValueModel.NODES));

		ChangeListener listener2 = this.buildValueChangeListener1();
		this.aa1.addChangeListener(listener2);
		assertTrue(this.aa1.hasAnyTreeChangeListeners(TreeValueModel.NODES));
		assertTrue(this.subject1.hasAnyTreeChangeListeners(TestSubject.NAMES_TREE));
		this.aa1.removeChangeListener(listener2);
		assertFalse(this.subject1.hasAnyTreeChangeListeners(TestSubject.NAMES_TREE));
		assertFalse(this.aa1.hasAnyTreeChangeListeners(TreeValueModel.NODES));
	}

	// ********** inner classes **********
	
	class TestSubject extends AbstractModel {
		private TestNode rootNameNode;
		public static final String NAMES_TREE = "names";
		private TestNode rootDescriptionNode;
		public static final String DESCRIPTIONS_TREE = "descriptions";
	
		public TestSubject() {
			this.rootNameNode = new TestNode("root name");
			this.rootDescriptionNode = new TestNode("root description");
		}
		public TestNode getRootNameNode() {
			return this.rootNameNode;
		}
		public Iterator<TestNode> nameNodes() {
			return new TreeIterator<TestNode>(this.rootNameNode) {
				@Override
				public Iterator<TestNode> children(TestNode next) {
					return next.children();
				}
			};
		}
		public Iterator<List<TestNode>> namePaths() {
			return new TransformationIterator<TestNode, List<TestNode>>(this.nameNodes()) {
				@Override
				protected List<TestNode> transform(TestNode next) {
					return next.getPath();
				}
			};
		}
		public TestNode addName(TestNode parent, String name) {
			TestNode child = new TestNode(name);
			parent.addChild(child);
			this.fireNodeAdded(NAMES_TREE, child.getPath());
			return child;
		}
		public void addTwoNames(TestNode parent, String name1, String name2) {
			parent.addChild(new TestNode(name1));
			parent.addChild(new TestNode(name2));
			this.fireTreeChanged(NAMES_TREE, parent.getPath());
		}
		public void removeNameNode(TestNode nameNode) {
			nameNode.getParent().removeChild(nameNode);
			this.fireNodeRemoved(NAMES_TREE, nameNode.getPath());
		}
		public boolean containsNameNode(String name) {
			return this.nameNode(name) != null;
		}
		public TestNode nameNode(String name) {
			for (Iterator<TestNode> stream = this.nameNodes(); stream.hasNext(); ) {
				TestNode node = stream.next();
				if (node.getText().equals(name)) {
					return node;
				}
			}
			return null;
		}
		public TestNode getRootDescriptionNode() {
			return this.rootDescriptionNode;
		}
		public Iterator<TestNode> descriptionNodes() {
			return new TreeIterator<TestNode>(this.rootDescriptionNode) {
				@Override
				public Iterator<TestNode> children(TestNode next) {
					return next.children();
				}
			};
		}
		public Iterator<List<TestNode>> descriptionPaths() {
			return new TransformationIterator<TestNode, List<TestNode>>(this.descriptionNodes()) {
				@Override
				protected List<TestNode> transform(TestNode next) {
					return next.getPath();
				}
			};
		}
		public TestNode addDescription(TestNode parent, String description) {
			TestNode child = new TestNode(description);
			parent.addChild(child);
			this.fireNodeAdded(DESCRIPTIONS_TREE, child.getPath());
			return child;
		}
		public void removeDescriptionNode(TestNode descriptionNode) {
			descriptionNode.getParent().removeChild(descriptionNode);
			this.fireNodeRemoved(DESCRIPTIONS_TREE, descriptionNode.getPath());
		}
		public boolean containsDescriptionNode(String name) {
			for (Iterator<TestNode> stream = this.descriptionNodes(); stream.hasNext(); ) {
				TestNode node = stream.next();
				if (node.getText().equals(name)) {
					return true;
				}
			}
			return false;
		}
	}
	
	class TestNode {
		private final String text;
		private TestNode parent;
		private final Collection<TestNode> children;
	
		public TestNode(String text) {
			this.text = text;
			this.children = new HashBag<TestNode>();
		}
		public String getText() {
			return this.text;
		}
		public TestNode getParent() {
			return this.parent;
		}
		private void setParent(TestNode parent) {
			this.parent = parent;
		}
		public Iterator<TestNode> children() {
			return new ReadOnlyIterator<TestNode>(this.children);
		}
		public void addChild(TestNode child) {
			this.children.add(child);
			child.setParent(this);
		}
		public void removeChild(TestNode child) {
			this.children.remove(child);
		}
		public List<TestNode> getPath() {
			return CollectionTools.reverseList(this.buildAntiPath());
		}
		private Iterator<TestNode> buildAntiPath() {
			return new ChainIterator<TestNode>(this) {
				@Override
				protected TestNode nextLink(TestNode currentLink) {
					return currentLink.getParent();
				}
			};
		}
		@Override
		public String toString() {
			return "TestNode(" + this.text + ")";
		}
	}
	
}
