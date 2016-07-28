/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CollectionValueModelToolsTests
	extends TestCase
{
	public CollectionValueModelToolsTests(String name) {
		super(name);
	}

	public void testFirstElementPVMAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<String> pvm = CollectionValueModelTools.firstElementPropertyValueModel(cvm);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(pvm.getValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertEquals("foo", pvm.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertTrue(pvm.getValue().equals("foo") || pvm.getValue().equals("bar"));

		listener.event = null;
		cvm.remove("foo");
		assertEquals("bar", pvm.getValue());
		// unpredictable
		// assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		cvm.remove("bar");
		assertNull(pvm.getValue());
		assertNull(listener.event.getNewValue());
	}

	public void testLastElementPVMAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<String> pvm = CollectionValueModelTools.lastElementPropertyValueModel(cvm);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(pvm.getValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertEquals("foo", pvm.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertTrue(pvm.getValue().equals("foo") || pvm.getValue().equals("bar"));

		listener.event = null;
		cvm.remove("foo");
		assertEquals("bar", pvm.getValue());
		// unpredictable
		// assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		cvm.remove("bar");
		assertNull(pvm.getValue());
		assertNull(listener.event.getNewValue());
	}

	public void testSingleElementPVMAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<String> pvm = CollectionValueModelTools.singleElementPropertyValueModel(cvm);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(pvm.getValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertEquals("foo", pvm.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertNull(pvm.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		cvm.remove("foo");
		assertEquals("bar", pvm.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		cvm.remove("bar");
		assertNull(pvm.getValue());
		assertNull(listener.event.getNewValue());
	}

	public void testIsNotEmptyPropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<Boolean> pvm = CollectionValueModelTools.isNotEmptyPropertyValueModel(cvm);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("foo");
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());
	}

	public void testIsNotEmptyModifiablePropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		BooleanClosure.Adapter adapter = new NotEmptyBooleanClosureAdapter(cvm);
		ModifiablePropertyValueModel<Boolean> pvm = CollectionValueModelTools.isNotEmptyModifiablePropertyValueModel(cvm, adapter);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		pvm.setValue(Boolean.FALSE);
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());
		assertEquals(0, cvm.size());

		listener.event = null;
		pvm.setValue(Boolean.TRUE);
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());
		assertEquals(2, cvm.size());
		assertTrue(cvm.contains("baz"));
		assertTrue(cvm.contains("xxx"));

		listener.event = null;
		cvm.remove("baz");
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("xxx");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());
	}

	public static class NotEmptyBooleanClosureAdapter
		implements BooleanClosure.Adapter
	{
		final SimpleCollectionValueModel<String> cvm;
		public NotEmptyBooleanClosureAdapter(SimpleCollectionValueModel<String> cvm) {
			this.cvm = cvm;
		}
		public void execute(boolean argument) {
			if (argument) {
				ArrayList<String> list = new ArrayList<>();
				list.add("baz");
				list.add("xxx");
				this.cvm.setValues(list);
			} else {
				this.cvm.clear();
			}
		}
	}

	public void testIsEmptyPropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<Boolean> pvm = CollectionValueModelTools.isEmptyPropertyValueModel(cvm);
		this.verifyIsEmptyPropertyValueModelAdapter(cvm, pvm);
	}

	private void verifyIsEmptyPropertyValueModelAdapter(SimpleCollectionValueModel<String> cvm, PropertyValueModel<Boolean> pvm) {
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("foo");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("bar");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());
	}

	public void testBooleanPVM() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<Boolean> pvm = CollectionValueModelTools.booleanPropertyValueModel(cvm, PredicateTools.collectionIsEmptyPredicate());
		this.verifyIsEmptyPropertyValueModelAdapter(cvm, pvm);
	}

	public void testIsEmptyModifiablePropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		BooleanClosure.Adapter adapter = new EmptyBooleanClosureAdapter(cvm);
		ModifiablePropertyValueModel<Boolean> pvm = CollectionValueModelTools.isEmptyModifiablePropertyValueModel(cvm, adapter);
		this.verifyIsEmptyModifiablePropertyValueModelAdapter(cvm, pvm);
	}

	public void testBooleanModifiablePVM() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		BooleanClosure.Adapter adapter = new EmptyBooleanClosureAdapter(cvm);
		ModifiablePropertyValueModel<Boolean> pvm = CollectionValueModelTools.booleanModifiablePropertyValueModel(cvm, PredicateTools.collectionIsEmptyPredicate(), adapter);
		this.verifyIsEmptyPropertyValueModelAdapter(cvm, pvm);
	}

	private void verifyIsEmptyModifiablePropertyValueModelAdapter(SimpleCollectionValueModel<String> cvm, ModifiablePropertyValueModel<Boolean> pvm) {
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertTrue(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		pvm.setValue(Boolean.TRUE);
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());
		assertEquals(0, cvm.size());

		listener.event = null;
		pvm.setValue(Boolean.FALSE);
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());
		assertEquals(2, cvm.size());
		assertTrue(cvm.contains("baz"));
		assertTrue(cvm.contains("xxx"));

		listener.event = null;
		cvm.remove("baz");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.remove("xxx");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());
	}

	public static class EmptyBooleanClosureAdapter
		implements BooleanClosure.Adapter
	{
		final SimpleCollectionValueModel<String> cvm;
		public EmptyBooleanClosureAdapter(SimpleCollectionValueModel<String> cvm) {
			this.cvm = cvm;
		}
		public void execute(boolean argument) {
			if (argument) {
				this.cvm.clear();
			} else {
				ArrayList<String> list = new ArrayList<>();
				list.add("baz");
				list.add("xxx");
				this.cvm.setValues(list);
			}
		}
	}

	public void testContainsSingleElementPropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<Boolean> pvm = CollectionValueModelTools.containsSingleElementPropertyValueModel(cvm);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		cvm.remove("foo");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.remove("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());
	}

	public void testSizeEqualsPropertyValueModelAdapter() {
		SimpleCollectionValueModel<String> cvm = new SimpleCollectionValueModel<>();
		PropertyValueModel<Boolean> pvm = CollectionValueModelTools.sizeEqualsPropertyValueModel(cvm, 2);
		LocalPropertyChangeListener listener = new LocalPropertyChangeListener();
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("foo");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);

		listener.event = null;
		cvm.add("bar");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.add("baz");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		cvm.remove("baz");
		assertTrue(pvm.getValue().booleanValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		cvm.remove("foo");
		assertFalse(pvm.getValue().booleanValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		cvm.remove("bar");
		assertFalse(pvm.getValue().booleanValue());
		assertNull(listener.event);
	}

	public static class LocalPropertyChangeListener
		extends PropertyChangeAdapter
	{
		public PropertyChangeEvent event;
		public LocalPropertyChangeListener() {
			super();
		}
		@Override
		public void propertyChanged(PropertyChangeEvent e) {
			this.event = e;
		}
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object o = ClassTools.newInstance(CollectionValueModelTools.class);
			fail("bogus: " + o);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}
}