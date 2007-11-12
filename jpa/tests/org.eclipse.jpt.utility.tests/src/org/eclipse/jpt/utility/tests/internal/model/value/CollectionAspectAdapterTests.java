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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class CollectionAspectAdapterTests extends TestCase {
	private TestSubject subject1;
	private PropertyValueModel subjectHolder1;
	private CollectionAspectAdapter aa1;
	private CollectionChangeEvent event1;
	private CollectionChangeListener listener1;
	private String event1Type;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String CHANGE = "change";
	private static final String CLEAR = "clear";

	private TestSubject subject2;

	public CollectionAspectAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject();
		this.subject1.addNames(this.subject1Names());
		this.subject1.addDescriptions(this.subject1Descriptions());
		this.subjectHolder1 = new SimplePropertyValueModel(this.subject1);
		this.aa1 = this.buildAspectAdapter(this.subjectHolder1);
		this.listener1 = this.buildValueChangeListener1();
		this.aa1.addCollectionChangeListener(CollectionValueModel.VALUES, this.listener1);
		this.event1 = null;
		this.event1Type = null;

		this.subject2 = new TestSubject();
		this.subject2.addNames(this.subject2Names());
		this.subject2.addDescriptions(this.subject2Descriptions());
	}

	private Collection<String> subject1Names() {
		Collection<String> result = new HashBag<String>();
		result.add("foo");
		result.add("bar");
		return result;
	}

	private Collection<String> subject1Descriptions() {
		Collection<String> result = new HashBag<String>();
		result.add("this.subject1 description1");
		result.add("this.subject1 description2");
		return result;
	}

	private Collection<String> subject2Names() {
		Collection<String> result = new HashBag<String>();
		result.add("baz");
		result.add("bam");
		return result;
	}

	private Collection<String> subject2Descriptions() {
		Collection<String> result = new HashBag<String>();
		result.add("this.subject2 description1");
		result.add("this.subject2 description2");
		return result;
	}

	private CollectionAspectAdapter buildAspectAdapter(ValueModel subjectHolder) {
		return new CollectionAspectAdapter(subjectHolder, TestSubject.NAMES_COLLECTION) {
			// this is not a typical aspect adapter - the value is determined by the aspect name
			@Override
			protected Iterator<String> getValueFromSubject() {
				if (this.collectionName == TestSubject.NAMES_COLLECTION) {
					return ((TestSubject) this.subject).names();
				} else if (this.collectionName == TestSubject.DESCRIPTIONS_COLLECTION) {
					return ((TestSubject) this.subject).descriptions();
				} else {
					throw new IllegalStateException("invalid aspect name: " + this.collectionName);
				}
			}
			@Override
			public void add(Object item) {
				if (this.collectionName == TestSubject.NAMES_COLLECTION) {
					((TestSubject) this.subject).addName((String) item);
				} else if (this.collectionName == TestSubject.DESCRIPTIONS_COLLECTION) {
					((TestSubject) this.subject).addDescription((String) item);
				} else {
					throw new IllegalStateException("invalid aspect name: " + this.collectionName);
				}
			}
			@Override
			public void remove(Object item) {
				if (this.collectionName == TestSubject.NAMES_COLLECTION) {
					((TestSubject) this.subject).removeName((String) item);
				} else if (this.collectionName == TestSubject.DESCRIPTIONS_COLLECTION) {
					((TestSubject) this.subject).removeDescription((String) item);
				} else {
					throw new IllegalStateException("invalid aspect name: " + this.collectionName);
				}
			}
		};
	}

	private CollectionChangeListener buildValueChangeListener1() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				CollectionAspectAdapterTests.this.value1Changed(e, ADD);
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				CollectionAspectAdapterTests.this.value1Changed(e, REMOVE);
			}
			public void collectionCleared(CollectionChangeEvent e) {
				CollectionAspectAdapterTests.this.value1Changed(e, CLEAR);
			}
			public void collectionChanged(CollectionChangeEvent e) {
				CollectionAspectAdapterTests.this.value1Changed(e, CHANGE);
			}
		};
	}

	void value1Changed(CollectionChangeEvent e, String eventType) {
		this.event1 = e;
		this.event1Type = eventType;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSubjectHolder() {
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.event1Type, CHANGE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject2Names(), CollectionTools.bag((Iterator) this.aa1.values()));
		
		this.event1 = null;
		this.event1Type = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.event1Type, CHANGE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertFalse(this.event1.items().hasNext());
		assertFalse(((Iterator) this.aa1.values()).hasNext());
		
		this.event1 = null;
		this.event1Type = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.event1Type, CHANGE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertFalse(this.event1.items().hasNext());
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
	}

	public void testAdd() {
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
		assertNull(this.event1);

		this.subject1.addName("jam");
		assertNotNull(this.event1);
		assertEquals(this.event1Type, ADD);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertEquals("jam", this.event1.items().next());
		Collection namesPlus = this.subject1Names();
		namesPlus.add("jam");
		assertEquals(namesPlus, CollectionTools.bag((Iterator) this.aa1.values()));

		this.event1 = null;
		this.event1Type = null;
		this.aa1.addAll(Collections.singleton("jaz"));
		assertNotNull(this.event1);
		assertEquals(this.event1Type, ADD);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertEquals("jaz", this.event1.items().next());
		namesPlus.add("jaz");
		assertEquals(namesPlus, CollectionTools.bag((Iterator) this.aa1.values()));
	}

	public void testRemove() {
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
		assertNull(this.event1);

		this.subject1.removeName("foo");
		assertNotNull(this.event1);
		assertEquals(this.event1Type, REMOVE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertEquals("foo", this.event1.items().next());
		Collection namesMinus = this.subject1Names();
		namesMinus.remove("foo");
		assertEquals(namesMinus, CollectionTools.bag((Iterator) this.aa1.values()));

		this.event1 = null;
		this.event1Type = null;
		this.aa1.removeAll(Collections.singleton("bar"));
		assertNotNull(this.event1);
		assertEquals(this.event1Type, REMOVE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertEquals("bar", this.event1.items().next());
		namesMinus.remove("bar");
		assertEquals(namesMinus, CollectionTools.bag((Iterator) this.aa1.values()));
	}

	public void testCollectionChange() {
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
		assertNull(this.event1);

		this.subject1.addTwoNames("jam", "jaz");
		assertNotNull(this.event1);
		assertEquals(this.event1Type, CHANGE);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event1.collectionName());
		assertFalse(this.event1.items().hasNext());
		Collection namesPlus2 = this.subject1Names();
		namesPlus2.add("jam");
		namesPlus2.add("jaz");
		assertEquals(namesPlus2, CollectionTools.bag((Iterator) this.aa1.values()));
	}

	public void testValues() {
		assertEquals(this.subject1Names(), CollectionTools.bag(this.subject1.names()));
		assertEquals(this.subject1Names(), CollectionTools.bag((Iterator) this.aa1.values()));
	}

	public void testSize() {
		assertEquals(this.subject1Names().size(), CollectionTools.size(this.subject1.names()));
		assertEquals(this.subject1Names().size(), CollectionTools.size((Iterator) this.aa1.values()));
	}

	public void testHasListeners() {
		assertTrue(this.aa1.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(this.subject1.hasAnyCollectionChangeListeners(TestSubject.NAMES_COLLECTION));
		this.aa1.removeCollectionChangeListener(CollectionValueModel.VALUES, this.listener1);
		assertFalse(this.subject1.hasAnyCollectionChangeListeners(TestSubject.NAMES_COLLECTION));
		assertFalse(this.aa1.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		CollectionChangeListener listener2 = this.buildValueChangeListener1();
		this.aa1.addCollectionChangeListener(listener2);
		assertTrue(this.aa1.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(this.subject1.hasAnyCollectionChangeListeners(TestSubject.NAMES_COLLECTION));
		this.aa1.removeCollectionChangeListener(listener2);
		assertFalse(this.subject1.hasAnyCollectionChangeListeners(TestSubject.NAMES_COLLECTION));
		assertFalse(this.aa1.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}

// ********** inner class **********

private class TestSubject extends AbstractModel {
	private Collection<String> names;
	public static final String NAMES_COLLECTION = "names";
	private Collection<String> descriptions;
	public static final String DESCRIPTIONS_COLLECTION = "descriptions";

	public TestSubject() {
		this.names = new HashBag<String>();
		this.descriptions = new HashBag<String>();
	}
	public Iterator<String> names() {
		return new ReadOnlyIterator<String>(this.names);
	}
	public void addName(String name) {
		if (this.names.add(name)) {
			this.fireItemAdded(NAMES_COLLECTION, name);
		}
	}
	public void addNames(Iterator<String> newNames) {
		while (newNames.hasNext()) {
			this.addName(newNames.next());
		}
	}
	public void addNames(Collection<String> newNames) {
		this.addNames(newNames.iterator());
	}
	public void addTwoNames(String name1, String name2) {
		if (this.names.add(name1) | this.names.add(name2)) {
			this.fireCollectionChanged(NAMES_COLLECTION);
		}
	}
	public void removeName(String name) {
		if (this.names.remove(name)) {
			this.fireItemRemoved(NAMES_COLLECTION, name);
		}
	}
	public Iterator<String> descriptions() {
		return new ReadOnlyIterator<String>(this.descriptions);
	}
	public void addDescription(String description) {
		if (this.descriptions.add(description)) {
			this.fireItemAdded(DESCRIPTIONS_COLLECTION, description);
		}
	}
	public void addDescriptions(Iterator<String> newDescriptions) {
		while (newDescriptions.hasNext()) {
			this.addDescription(newDescriptions.next());
		}
	}
	public void addDescriptions(Collection<String> newDescriptions) {
		this.addDescriptions(newDescriptions.iterator());
	}
	public void removeDescription(String description) {
		if (this.descriptions.remove(description)) {
			this.fireItemRemoved(DESCRIPTIONS_COLLECTION, description);
		}
	}
}

}
