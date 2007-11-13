/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListCurator;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public final class ListCuratorTests 
	extends TestCase 
{
	private TestSubject subject1;
	private PropertyValueModel subjectHolder1;
	
	private ListCurator curator;
	private ListChangeListener listener1;
	private ListChangeEvent event1;
	
	private TestSubject subject2;

	public ListCuratorTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject(this.subject1Names());
		this.subjectHolder1 = new SimplePropertyValueModel(this.subject1);
		this.curator = this.buildListCurator(this.subjectHolder1);
		this.listener1 = this.buildListChangeListener1();
		this.curator.addListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		this.event1 = null;
		
		this.subject2 = new TestSubject(this.subject2Names());
	}
	
	private List subject1Names() {
		ArrayList list = new ArrayList();
		list.add("alpha");
		list.add("bravo");
		list.add("charlie");
		list.add("delta");
		return list;
	}
	
	private List subject2Names() {
		ArrayList list = new ArrayList();
		list.add("echo");
		list.add("foxtrot");
		list.add("glove");
		list.add("hotel");
		return list;
	}
	
	private ListCurator buildListCurator(ValueModel subjectHolder) {
		return new ListCurator(subjectHolder) {
			public Iterator getValueForRecord() {
				return ((TestSubject) this.subject).strings();
			}
		};
	}

	private ListChangeListener buildListChangeListener1() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void listCleared(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListCuratorTests.this.value1Changed(e);
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
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject2Names(), CollectionTools.list((ListIterator) this.curator.values()));
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertFalse(((Iterator) this.curator.values()).hasNext());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(-1, this.event1.index());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
	}

	public void testAdd() {
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
		assertNull(this.event1);

		this.subject1.addString("echo");
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(this.subject1Names().size(), this.event1.index());
		assertEquals("echo", this.event1.items().next());
		List stringsPlus = this.subject1Names();
		stringsPlus.add("echo");
		assertEquals(stringsPlus, CollectionTools.list((ListIterator) this.curator.values()));

		this.event1 = null;
		this.subject1.addString(0, "zulu");
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(0, this.event1.index());
		assertEquals("zulu", this.event1.items().next());
		stringsPlus.add(0, "zulu");
		assertEquals(stringsPlus, CollectionTools.list((ListIterator) this.curator.values()));
	}
	
	public void testRemove() {
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
		assertNull(this.event1);

		String removedString = this.subject1.removeString(0);	// should be "alpha"
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(0, this.event1.index());
		assertEquals(removedString, this.event1.items().next());
		List stringsMinus = this.subject1Names();
		stringsMinus.remove(0);
		assertEquals(stringsMinus, CollectionTools.list((ListIterator) this.curator.values()));
		
		removedString = this.subject1.removeString(2);	// should be "delta"
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(2, this.event1.index());
		assertEquals(removedString, this.event1.items().next());
		stringsMinus.remove(2);
		assertEquals(stringsMinus, CollectionTools.list((ListIterator) this.curator.values()));
	}
	
	public void testCompleteListChange() {
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
		assertNull(this.event1);
		
		this.subject1.setStrings(this.subject2Names());
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		List newStrings = this.subject2Names();
		assertEquals(newStrings, CollectionTools.list((ListIterator) this.curator.values()));
	}
	
	public void testPartialListChange() {
		List startingList = CollectionTools.list((ListIterator) this.curator.values());
		assertEquals(this.subject1Names(), startingList);
		assertNull(this.event1);
		
		String identicalString = (String) startingList.get(1);  // should be "bravo"
		String nonidenticalString = (String) startingList.get(0); // should be "alpha"
		List newStrings = CollectionTools.list(new String[] {new String("bravo"), new String("alpha"), "echo", "delta", "foxtrot"});
		this.subject1.setStrings(newStrings);
		
		List finalList = CollectionTools.list((ListIterator) this.curator.values());
		assertNotNull(this.event1);
		assertEquals(this.curator, this.event1.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event1.listName());
		assertEquals(newStrings, finalList);
		assertTrue(identicalString == finalList.get(0));
		assertTrue(nonidenticalString != finalList.get(1));
	}
	
	public void testValues() {
		assertEquals(this.subject1Names(), CollectionTools.list(this.subject1.strings()));
		assertEquals(this.subject1Names(), CollectionTools.list((ListIterator) this.curator.values()));
	}
	
	public void testGet() {
		assertEquals(this.subject1Names().get(0), this.subject1.getString(0));
		assertEquals(this.subject1Names().get(0), this.curator.get(0));
	}
	
	public void testSize() {
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.subject1.strings()));
		assertEquals(this.subject1Names().size(), CollectionTools.size((ListIterator) this.curator.values()));
		assertEquals(this.subject1Names().size(), this.curator.size());
	}
	
	public void testHasListeners() {
		assertTrue(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyStateChangeListeners());
		this.curator.removeListChangeListener(ListValueModel.LIST_VALUES, this.listener1);
		assertFalse(this.subject1.hasAnyStateChangeListeners());
		assertFalse(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ListChangeListener listener2 = this.buildListChangeListener1();
		this.curator.addListChangeListener(listener2);
		assertTrue(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.subject1.hasAnyStateChangeListeners());
		this.curator.removeListChangeListener(listener2);
		assertFalse(this.subject1.hasAnyStateChangeListeners());
		assertFalse(this.curator.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}
	
	
	// **************** Inner Class *******************************************
	
	private class TestSubject 
		extends AbstractModel
	{
		private List strings;
		
		public TestSubject() {
			this.strings = new ArrayList();
		}
		
		public TestSubject(List strings) {
			this();
			this.setStrings(strings);
		}
		
		public String getString(int index) {
			return (String) this.strings.get(index);
		}
		
		public ListIterator strings() {
			return new ReadOnlyListIterator(this.strings);
		}
		
		public void addString(int index, String string) {
			this.strings.add(index, string);
			this.fireStateChanged();
		}
		
		public void addString(String string) {
			this.addString(this.strings.size(), string);
		}
		
		public String removeString(int index) {
			String string = (String) this.strings.get(index);
			this.removeString(string);
			return string;
		}
		
		public void removeString(String string) {
			this.strings.remove(string);
			this.fireStateChanged();
		}
		
		public void setStrings(List strings) {
			this.strings = new ArrayList(strings);
			this.fireStateChanged();
		}
		
		public void setStrings(String[] strings) {
			this.strings = CollectionTools.list(strings);
			this.fireStateChanged();
		}
	}
}
