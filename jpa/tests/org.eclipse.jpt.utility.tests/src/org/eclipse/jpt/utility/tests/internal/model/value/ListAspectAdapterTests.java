/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ListAspectAdapterTests extends TestCase {
	private TestSubject subject1;
	private WritablePropertyValueModel<TestSubject> subjectHolder1;
	private LocalListAspectAdapter aa1;
	private ListChangeEvent event1;
	private ListChangeListener listener1;

	private TestSubject subject2;

	public ListAspectAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject();
		this.subject1.addNames(this.subject1Names());
		this.subject1.addDescriptions(this.subject1Descriptions());
		this.subjectHolder1 = new SimplePropertyValueModel<TestSubject>(this.subject1);
		this.aa1 = this.buildAspectAdapter(this.subjectHolder1);
		this.listener1 = this.buildValueChangeListener1();
		this.aa1.addListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		this.event1 = null;

		this.subject2 = new TestSubject();
		this.subject2.addNames(this.subject2Names());
		this.subject2.addDescriptions(this.subject2Descriptions());
	}

	private List<String> subject1Names() {
		List<String> result = new ArrayList<String>();
		result.add("foo");
		result.add("bar");
		result.add("baz");
		result.add("bam");
		return result;
	}

	private List<String> subject1Descriptions() {
		List<String> result = new ArrayList<String>();
		result.add("this.subject1 description1");
		result.add("this.subject1 description2");
		return result;
	}

	private List<String> subject2Names() {
		List<String> result = new ArrayList<String>();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		result.add("jam");
		return result;
	}

	private List<String> subject2Descriptions() {
		List<String> result = new ArrayList<String>();
		result.add("this.subject2 description1");
		result.add("this.subject2 description2");
		return result;
	}

	private LocalListAspectAdapter buildAspectAdapter(PropertyValueModel<TestSubject> subjectHolder) {
		return new LocalListAspectAdapter(subjectHolder);
	}

	private ListChangeListener buildValueChangeListener1() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
			public void listCleared(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListAspectAdapterTests.this.value1Changed(e);
			}
		};
	}

	void value1Changed(ListChangeEvent e) {
		this.event1 = e;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSubjectHolder() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject2Names(), CollectionTools.list(this.aa1.listIterator()));
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertFalse(this.aa1.iterator().hasNext());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
	}

	public void testAdd() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		this.subject1.addName("jam");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(this.subject1Names().size(), this.event1.index());
		assertEquals("jam", this.event1.items().next());
		List<String> namesPlus = this.subject1Names();
		namesPlus.add("jam");
		assertEquals(namesPlus, CollectionTools.list(this.aa1.listIterator()));

		this.event1 = null;
		this.aa1.add(2, "jaz");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(2, this.event1.index());
		assertEquals("jaz", this.event1.items().next());
		namesPlus.add(2, "jaz");
		assertEquals(namesPlus, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testDefaultAdd() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		List<String> items = new ArrayList<String>();
		items.add("joo");
		items.add("jar");
		items.add("jaz");
		items.add("jam");

		this.event1 = null;
		this.aa1.addAll(2, items);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(5, this.event1.index());		// only the last "add" event will still be there
		assertEquals("jam", this.event1.items().next());
		List<String> namesPlus = this.subject1Names();
		namesPlus.addAll(2, items);
		assertEquals(namesPlus, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testRemove() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		String removedName = this.subject1.removeName(0);	// should be "foo"
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(0, this.event1.index());
		assertEquals(removedName, this.event1.items().next());
		List<String> namesMinus = this.subject1Names();
		namesMinus.remove(0);
		assertEquals(namesMinus, CollectionTools.list(this.aa1.listIterator()));

		this.event1 = null;
		Object removedItem = this.aa1.remove(0);	
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(0, this.event1.index());
		assertEquals(removedItem, this.event1.items().next());
		namesMinus.remove(0);
		assertEquals(namesMinus, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testDefaultLength() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		List<String> items = new ArrayList<String>();
		items.add("bar");
		items.add("baz");

		this.event1 = null;
		this.aa1.remove(1, 2);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(1, this.event1.index());		// only the last "remove" event will still be there
		assertEquals("baz", this.event1.items().next());
		List<String> namesPlus = this.subject1Names();
		namesPlus.remove(1);
		namesPlus.remove(1);
		assertEquals(namesPlus, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testReplace() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		String replacedName = this.subject1.setName(0, "jelly");	// should be "foo"
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(0, this.event1.index());
		assertEquals("jelly", this.event1.items().next());
		assertEquals(replacedName, this.event1.replacedItems().next());
		List<String> namesChanged = this.subject1Names();
		namesChanged.set(0, "jelly");
		assertEquals(namesChanged, CollectionTools.list(this.aa1.listIterator()));

		this.event1 = null;
		replacedName = this.subject1.setName(1, "roll");	// should be "bar"
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(1, this.event1.index());
		assertEquals("roll", this.event1.items().next());
		assertEquals(replacedName, this.event1.replacedItems().next());
		namesChanged = this.subject1Names();
		namesChanged.set(0, "jelly");
		namesChanged.set(1, "roll");
		assertEquals(namesChanged, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testListChange() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
		assertNull(this.event1);

		this.subject1.addTwoNames("jam", "jaz");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		List<String> namesPlus2 = this.subject1Names();
		namesPlus2.add(0, "jaz");
		namesPlus2.add(0, "jam");
		assertEquals(namesPlus2, CollectionTools.list(this.aa1.listIterator()));
	}

	public void testIterator() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.subject1.names()));
		assertEquals(this.subject1Names(), CollectionTools.list(this.aa1.listIterator()));
	}

	public void testGet() {
		assertEquals(this.subject1Names().get(0), this.subject1.getName(0));
		assertEquals(this.subject1Names().get(0), this.aa1.get(0));
	}

	public void testSize() {
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.subject1.names()));
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.aa1.listIterator()));
	}

	public void testHasListeners() {
		assertTrue(this.aa1.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyListChangeListeners(TestSubject.NAMES_LIST));
		this.aa1.removeListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		assertFalse(this.subject1.hasAnyListChangeListeners(TestSubject.NAMES_LIST));
		assertFalse(this.aa1.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ListChangeListener listener2 = this.buildValueChangeListener1();
		this.aa1.addListChangeListener(listener2);
		assertTrue(this.aa1.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyListChangeListeners(TestSubject.NAMES_LIST));
		this.aa1.removeListChangeListener(listener2);
		assertFalse(this.subject1.hasAnyListChangeListeners(TestSubject.NAMES_LIST));
		assertFalse(this.aa1.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}


	// ********** inner class **********

	private class TestSubject extends AbstractModel {
		private List<String> names;
		public static final String NAMES_LIST = "names";
		private List<String> descriptions;
		public static final String DESCRIPTIONS_LIST = "descriptions";
	
		public TestSubject() {
			this.names = new ArrayList<String>();
			this.descriptions = new ArrayList<String>();
		}
		public ListIterator<String> names() {
			return new ReadOnlyListIterator<String>(this.names);
		}
		public String getName(int index) {
			return this.names.get(index);
		}
		public void addName(int index, String name) {
			this.names.add(index, name);
			this.fireItemAdded(NAMES_LIST, index, name);
		}
		public void addName(String name) {
			this.addName(this.names.size(), name);
		}
		public void addNames(ListIterator<String> newNames) {
			while (newNames.hasNext()) {
				this.addName(newNames.next());
			}
		}
		public void addNames(List<String> newNames) {
			this.addNames(newNames.listIterator());
		}
		public void addTwoNames(String name1, String name2) {
			this.names.add(0, name2);
			this.names.add(0, name1);
			this.fireListChanged(NAMES_LIST);
		}
		public String removeName(int index) {
			String removedName = this.names.remove(index);
			this.fireItemRemoved(NAMES_LIST, index, removedName);
			return removedName;
		}
		public String setName(int index, String name) {
			String replacedName = this.names.set(index, name);
			this.fireItemReplaced(NAMES_LIST, index, name, replacedName);
			return replacedName;
		}
		public ListIterator<String> descriptions() {
			return new ReadOnlyListIterator<String>(this.descriptions);
		}
		public String getDescription(int index) {
			return this.descriptions.get(index);
		}
		public void addDescription(int index, String description) {
			this.descriptions.add(index, description);
			this.fireItemAdded(DESCRIPTIONS_LIST, index, description);
		}
		public void addDescription(String description) {
			this.addDescription(this.descriptions.size(), description);
		}
		public void addDescriptions(ListIterator<String> newDescriptions) {
			while (newDescriptions.hasNext()) {
				this.addDescription(newDescriptions.next());
			}
		}
		public void addDescriptions(List<String> newDescriptions) {
			this.addDescriptions(newDescriptions.listIterator());
		}
		public String removeDescription(int index) {
			String removedDescription = this.descriptions.remove(index);
			this.fireItemRemoved(DESCRIPTIONS_LIST, index, removedDescription);
			return removedDescription;
		}
		public String setDescription(int index, String description) {
			String replacedDescription = this.descriptions.set(index, description);
			this.fireItemReplaced(DESCRIPTIONS_LIST, index, description, replacedDescription);
			return replacedDescription;
		}
	}


	// this is not a typical aspect adapter - the value is determined by the aspect name
	private class LocalListAspectAdapter extends ListAspectAdapter<TestSubject, String> {

		LocalListAspectAdapter(PropertyValueModel<TestSubject> subjectHolder) {
			super(subjectHolder, TestSubject.NAMES_LIST);
		}

		@Override
		protected ListIterator<String> listIterator_() {
			if (this.listNames[0] == TestSubject.NAMES_LIST) {
				return this.subject.names();
			} else if (this.listNames[0] == TestSubject.DESCRIPTIONS_LIST) {
				return this.subject.descriptions();
			} else {
				throw new IllegalStateException("invalid aspect name: " + this.listNames[0]);
			}
		}

		public void add(int index, Object item) {
			if (this.listNames[0] == TestSubject.NAMES_LIST) {
				this.subject.addName(index, (String) item);
			} else if (this.listNames[0] == TestSubject.DESCRIPTIONS_LIST) {
				this.subject.addDescription(index, (String) item);
			} else {
				throw new IllegalStateException("invalid aspect name: " + this.listNames[0]);
			}
		}

		public void addAll(int index, List<String> items) {
			for (int i = 0; i < items.size(); i++) {
				this.add(index + i, items.get(i));
			}
		}

		public String remove(int index) {
			if (this.listNames[0] == TestSubject.NAMES_LIST) {
				return this.subject.removeName(index);
			} else if (this.listNames[0] == TestSubject.DESCRIPTIONS_LIST) {
				return this.subject.removeDescription(index);
			} else {
				throw new IllegalStateException("invalid aspect name: " + this.listNames[0]);
			}
		}

		public List<String> remove(int index, int length) {
			List<String> removedItems = new ArrayList<String>(length);
			for (int i = 0; i < length; i++) {
				removedItems.add(this.remove(index));
			}
			return removedItems;
		}

		public Object replace(int index, Object item) {
			if (this.listNames[0] == TestSubject.NAMES_LIST) {
				return this.subject.setName(index, (String) item);
			} else if (this.listNames[0] == TestSubject.DESCRIPTIONS_LIST) {
				return this.subject.setDescription(index, (String) item);
			} else {
				throw new IllegalStateException("invalid aspect name: " + this.listNames[0]);
			}
		}
	}

}
