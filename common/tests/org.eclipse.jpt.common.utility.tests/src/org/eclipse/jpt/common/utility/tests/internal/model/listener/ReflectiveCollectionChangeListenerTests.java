/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.command.ParameterizedCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ReflectiveChangeListener;

@SuppressWarnings("nls")
public class ReflectiveCollectionChangeListenerTests extends TestCase {
	
	public ReflectiveCollectionChangeListenerTests(String name) {
		super(name);
	}

	private CollectionChangeListener buildZeroArgumentListener(Object target) {
		return ReflectiveChangeListener.buildCollectionChangeListener(target, "itemAddedZeroArgument", "itemRemovedZeroArgument", "collectionClearedZeroArgument", "collectionChangedZeroArgument");
	}

	private CollectionChangeListener buildSingleArgumentListener(Object target) {
		return ReflectiveChangeListener.buildCollectionChangeListener(target, "itemAddedSingleArgument", "itemRemovedSingleArgument", "collectionClearedSingleArgument", "collectionChangedSingleArgument");
	}

	public void testItemAddedZeroArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildZeroArgumentListener(target));
		testModel.addString(string);
		assertTrue(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testItemAddedSingleArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildSingleArgumentListener(target));
		testModel.addString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertTrue(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testItemRemovedZeroArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildZeroArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertTrue(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testItemRemovedSingleArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildSingleArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertTrue(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testCollectionClearedZeroArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildZeroArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertTrue(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testCollectionClearedSingleArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildSingleArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertTrue(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testCollectionChangedZeroArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildZeroArgumentListener(target));
		testModel.replaceStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertTrue(target.collectionChangedZeroArgumentFlag);
		assertFalse(target.collectionChangedSingleArgumentFlag);
	}

	public void testCollectionChangedSingleArgumentNamedCollection() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		testModel.addCollectionChangeListener(TestModel.STRINGS_COLLECTION, this.buildSingleArgumentListener(target));
		testModel.replaceStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.collectionClearedZeroArgumentFlag);
		assertFalse(target.collectionClearedSingleArgumentFlag);
		assertFalse(target.collectionChangedZeroArgumentFlag);
		assertTrue(target.collectionChangedSingleArgumentFlag);
	}

	public void testBogusDoubleArgument1() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		boolean exCaught = false;
		try {
			CollectionChangeListener listener = ReflectiveChangeListener.buildCollectionChangeListener(target, "collectionChangedDoubleArgument");
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getCause().getClass() == NoSuchMethodException.class) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testBogusDoubleArgument2() throws Exception {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		Method method = ObjectTools.method(target, "collectionChangedDoubleArgument", new Class[] {CollectionChangeEvent.class, Object.class});
		boolean exCaught = false;
		try {
			CollectionChangeListener listener = ReflectiveChangeListener.buildCollectionChangeListener(target, method);
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getMessage().equals(method.toString())) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testListenerMismatch() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_COLLECTION, string);
		// build a COLLECTION change listener and hack it so we
		// can add it as a LIST change listener
		Object listener = ReflectiveChangeListener.buildCollectionChangeListener(target, "collectionEventSingleArgument");
		testModel.addListChangeListener("bogus list", (ListChangeListener) listener);

		boolean exCaught = false;
		try {
			testModel.changeList();
			fail("listener mismatch: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	class TestModel extends AbstractModel {
		private Collection<String> strings = new ArrayList<String>();
			public static final String STRINGS_COLLECTION = "strings";
		TestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new ChangeSupport(this, RuntimeExceptionHandler.instance());
		}
		Iterator<String> strings() {
			return IteratorTools.clone(this.strings, new ParameterizedCommand<String>() {
				public void execute(String s) {
					TestModel.this.removeString(s);
				}
			});
		}
		void addString(String string) {
			this.addItemToCollection(string, this.strings, STRINGS_COLLECTION);
		}
		void removeString(String string) {
			this.removeItemFromCollection(string, this.strings, STRINGS_COLLECTION);
		}
		void clearStrings() {
			this.clearCollection(this.strings, STRINGS_COLLECTION);
		}
		void replaceStrings(String[] newStrings) {
			this.strings.clear();
			CollectionTools.addAll(this.strings, newStrings);
			this.fireCollectionChanged(STRINGS_COLLECTION, this.strings);
		}
		void changeList() {
			this.fireListChanged("bogus list", Collections.emptyList());
		}
	}

	class Target {
		TestModel testModel;
		String collectionName;
		String string;
		boolean itemAddedZeroArgumentFlag = false;
		boolean itemAddedSingleArgumentFlag = false;
		boolean itemRemovedZeroArgumentFlag = false;
		boolean itemRemovedSingleArgumentFlag = false;
		boolean collectionClearedZeroArgumentFlag = false;
		boolean collectionClearedSingleArgumentFlag = false;
		boolean collectionChangedZeroArgumentFlag = false;
		boolean collectionChangedSingleArgumentFlag = false;
		boolean collectionEventSingleArgumentFlag = false;
		Target(TestModel testModel, String collectionName, String string) {
			super();
			this.testModel = testModel;
			this.collectionName = collectionName;
			this.string = string;
		}
		void itemAddedZeroArgument() {
			this.itemAddedZeroArgumentFlag = true;
		}
		void itemAddedSingleArgument(CollectionAddEvent e) {
			this.itemAddedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.collectionName, e.getCollectionName());
			assertEquals(this.string, e.getItems().iterator().next());
		}
		void itemRemovedZeroArgument() {
			this.itemRemovedZeroArgumentFlag = true;
		}
		void itemRemovedSingleArgument(CollectionRemoveEvent e) {
			this.itemRemovedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.collectionName, e.getCollectionName());
			assertEquals(this.string, e.getItems().iterator().next());
		}
		void collectionClearedZeroArgument() {
			this.collectionClearedZeroArgumentFlag = true;
		}
		void collectionClearedSingleArgument(CollectionClearEvent e) {
			this.collectionClearedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.collectionName, e.getCollectionName());
		}
		void collectionChangedZeroArgument() {
			this.collectionChangedZeroArgumentFlag = true;
		}
		void collectionChangedSingleArgument(CollectionChangeEvent e) {
			this.collectionChangedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.collectionName, e.getCollectionName());
		}
		void collectionEventSingleArgument(CollectionEvent e) {
			this.collectionEventSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.collectionName, e.getCollectionName());
		}
		void collectionChangedDoubleArgument(CollectionChangeEvent e, Object o) {
			fail("bogus event: " + e + " object: " + o);
		}
	}

}
