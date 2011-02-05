/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListCurator;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public final class ListCuratorTests 
	extends TestCase 
{
	private TestSubject subject1;
	private WritablePropertyValueModel<TestSubject> subjectHolder1;
	
	private ListCurator<TestSubject, String> curator;
	private ListChangeListener listener1;
	private ListEvent event1;
	
	private TestSubject subject2;

	public ListCuratorTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject(this.subject1Names());
		this.subjectHolder1 = new SimplePropertyValueModel<TestSubject>(this.subject1);
		this.curator = this.buildListCurator(this.subjectHolder1);
		this.listener1 = this.buildListChangeListener1();
		this.curator.addListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		this.event1 = null;
		
		this.subject2 = new TestSubject(this.subject2Names());
	}
	
	private List<String> subject1Names() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("alpha");
		list.add("bravo");
		list.add("charlie");
		list.add("delta");
		return list;
	}
	
	private List<String> subject2Names() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("echo");
		list.add("foxtrot");
		list.add("glove");
		list.add("hotel");
		return list;
	}
	
	private ListCurator<TestSubject, String> buildListCurator(PropertyValueModel<TestSubject> subjectHolder) {
		return new ListCurator<TestSubject, String>(subjectHolder) {
			@Override
			public Iterator<String> iteratorForRecord() {
				return this.subject.strings();
			}
		};
	}

	private ListChangeListener buildListChangeListener1() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsRemoved(ListRemoveEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsReplaced(ListReplaceEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsMoved(ListMoveEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void listCleared(ListClearEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
		};
	}

	void value1Changed(ListEvent e) {
		this.event1 = e;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSubjectHolder() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(this.subject2Names(), CollectionTools.list(this.curator.listIterator()));
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertFalse(this.curator.iterator().hasNext());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
	}

	public void testAdd() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
		assertNull(this.event1);

		this.subject1.addString("echo");
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(this.subject1Names().size(), ((ListAddEvent) this.event1).getIndex());
		assertEquals("echo", ((ListAddEvent) this.event1).getItems().iterator().next());
		List<String> stringsPlus = this.subject1Names();
		stringsPlus.add("echo");
		assertEquals(stringsPlus, CollectionTools.list(this.curator.listIterator()));

		this.event1 = null;
		this.subject1.addString(0, "zulu");
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(0, ((ListAddEvent) this.event1).getIndex());
		assertEquals("zulu", ((ListAddEvent) this.event1).getItems().iterator().next());
		stringsPlus.add(0, "zulu");
		assertEquals(stringsPlus, CollectionTools.list(this.curator.listIterator()));
	}
	
	public void testRemove() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
		assertNull(this.event1);

		String removedString = this.subject1.removeString(0);	// should be "alpha"
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(0, ((ListRemoveEvent) this.event1).getIndex());
		assertEquals(removedString, ((ListRemoveEvent) this.event1).getItems().iterator().next());
		List<String> stringsMinus = this.subject1Names();
		stringsMinus.remove(0);
		assertEquals(stringsMinus, CollectionTools.list(this.curator.listIterator()));
		
		removedString = this.subject1.removeString(2);	// should be "delta"
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(2, ((ListRemoveEvent) this.event1).getIndex());
		assertEquals(removedString, ((ListRemoveEvent) this.event1).getItems().iterator().next());
		stringsMinus.remove(2);
		assertEquals(stringsMinus, CollectionTools.list(this.curator.listIterator()));
	}
	
	public void testCompleteListChange() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
		assertNull(this.event1);
		
		this.subject1.setStrings(this.subject2Names());
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		List<String> newStrings = this.subject2Names();
		assertEquals(newStrings, CollectionTools.list(this.curator.listIterator()));
	}
	
	public void testPartialListChange() {
		List<String> startingList = CollectionTools.list(this.curator.listIterator());
		assertEquals(this.subject1Names(), startingList);
		assertNull(this.event1);
		
		String identicalString = startingList.get(1);  // should be "bravo"
		String nonidenticalString = startingList.get(0); // should be "alpha"
		List<String> newStrings = CollectionTools.list(new String[] {new String("bravo"), new String("alpha"), "echo", "delta", "foxtrot"});
		this.subject1.setStrings(newStrings);
		
		List<String> finalList = CollectionTools.list(this.curator.listIterator());
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.getListName());
		assertEquals(newStrings, finalList);
		assertTrue(identicalString == finalList.get(0));
		assertTrue(nonidenticalString != finalList.get(1));
	}
	
	public void testIterator() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.subject1.strings()));
		assertEquals(this.subject1Names(), CollectionTools.list(this.curator.listIterator()));
	}
	
	public void testGet() {
		assertEquals(this.subject1Names().get(0), this.subject1.getString(0));
		assertEquals(this.subject1Names().get(0), this.curator.get(0));
	}
	
	public void testSize() {
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.subject1.strings()));
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.curator.listIterator()));
		assertEquals(this.subject1Names().size(), this.curator.size());
	}
	
	public void testHasListeners() {
		assertTrue(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyStateChangeListeners());
		this.curator.removeListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		assertFalse(this.subject1.hasAnyStateChangeListeners());
		assertFalse(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener listener2 = this.buildChangeListener();
		this.curator.addChangeListener(listener2);
		assertTrue(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyStateChangeListeners());
		this.curator.removeChangeListener(listener2);
		assertFalse(this.subject1.hasAnyStateChangeListeners());
		assertFalse(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}
	
	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(ListAddEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			@Override
			public void itemsRemoved(ListRemoveEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			@Override
			public void itemsMoved(ListMoveEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			@Override
			public void listCleared(ListClearEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			@Override
			public void listChanged(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
		};
	}

	
	// **************** Inner Class *******************************************
	
	class TestSubject extends AbstractModel {
		private List<String> strings;
		
		public TestSubject() {
			this.strings = new ArrayList<String>();
		}
		
		public TestSubject(List<String> strings) {
			this();
			this.setStrings(strings);
		}
		
		public String getString(int index) {
			return this.strings.get(index);
		}
		
		public ListIterator<String> strings() {
			return new ReadOnlyListIterator<String>(this.strings);
		}
		
		public void addString(int index, String string) {
			this.strings.add(index, string);
			this.fireStateChanged();
		}
		
		public void addString(String string) {
			this.addString(this.strings.size(), string);
		}
		
		public String removeString(int index) {
			String string = this.strings.get(index);
			this.removeString(string);
			return string;
		}
		
		public void removeString(String string) {
			this.strings.remove(string);
			this.fireStateChanged();
		}
		
		public void setStrings(List<String> strings) {
			this.strings = new ArrayList<String>(strings);
			this.fireStateChanged();
		}
		
		public void setStrings(String[] strings) {
			this.strings = CollectionTools.list(strings);
			this.fireStateChanged();
		}
	}
}
