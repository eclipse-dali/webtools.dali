/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CompositePropertyValueModelTests
	extends TestCase
{
	private SimplePropertyValueModel<Integer> pvm1;
	private ModifiablePropertyValueModel<Integer> pvm2;
	private ModifiablePropertyValueModel<Integer> pvm3;
	private ModifiablePropertyValueModel<Integer> pvm4;
	private Collection<PropertyValueModel<Integer>> collection;
	private SimpleCollectionValueModel<PropertyValueModel<Integer>> cvm;
	private PropertyValueModel<Integer> compositePVM;
	PropertyChangeEvent event;


	public CompositePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pvm1 = new SimplePropertyValueModel<>(Integer.valueOf(1));
		this.pvm2 = new SimplePropertyValueModel<>(Integer.valueOf(2));
		this.pvm3 = new SimplePropertyValueModel<>(Integer.valueOf(3));
		this.pvm4 = new SimplePropertyValueModel<>(Integer.valueOf(4));
		this.collection = new ArrayList<>();
		this.collection.add(this.pvm1);
		this.collection.add(this.pvm2);
		this.collection.add(this.pvm3);
		this.collection.add(this.pvm4);
		this.cvm = new LocalSimpleCollectionValueModel<>(this.collection);
		
		this.compositePVM = this.buildCompositePVM(this.cvm);
	}

	public static class LocalSimpleCollectionValueModel<E>
		extends SimpleCollectionValueModel<E>
	{
		public LocalSimpleCollectionValueModel(Collection<E> collection) {
			super(collection);
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new ChangeSupport(this, RuntimeExceptionHandler.instance());
		}
	}

	private PropertyValueModel<Integer> buildCompositePVM(CollectionValueModel<PropertyValueModel<Integer>> pvms) {
		return CollectionValueModelTools.compositePropertyValueModel(pvms, new LocalTransformer());
	}

	public static class LocalTransformer
		extends TransformerAdapter<Collection<Integer>, Integer>
	{
		@Override
		public Integer transform(Collection<Integer> integers) {
			int sum = 0;
			for (Integer integer : integers) {
				if (integer != null) {
					sum += integer.intValue();
				}
			}
			return Integer.valueOf(sum);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue1() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals(10, this.compositePVM.getValue().intValue());
	}

	public void testGetValue2() {
		ArrayList<PropertyValueModel<Integer>> list = new ArrayList<>();
		CollectionTools.addAll(list, this.cvm);
		this.compositePVM = CollectionValueModelTools.compositePropertyValueModel(list, new LocalTransformer());
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals(10, this.compositePVM.getValue().intValue());
	}

	public void testGetValue3() {
		this.compositePVM = CollectionValueModelTools.compositePropertyValueModel(new LocalTransformer(), this.pvm1, this.pvm2, this.pvm3, this.pvm4);
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals(10, this.compositePVM.getValue().intValue());
	}

	public void testValueAndListeners1() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals(10, this.compositePVM.getValue().intValue());
		this.compositePVM.removeChangeListener(listener);
		assertNull(this.compositePVM.getValue());
	}	

	public void testValueAndListeners2() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertEquals(10, this.compositePVM.getValue().intValue());
		this.compositePVM.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(this.compositePVM.getValue());
	}

	public void testPropertyChange1() {
		this.compositePVM.addChangeListener(this.buildListener());
		this.verifyPropertyChange();
	}

	public void testPropertyChange2() {
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.verifyPropertyChange();
	}

	private void verifyPropertyChange() {
		this.event = null;
		this.pvm1.setValue(Integer.valueOf(5));
		this.verifyEvent(10, 14);

		this.event = null;
		this.pvm4.setValue(Integer.valueOf(0));
		this.verifyEvent(14, 10);
	}

	public void testCollectionChange1() {
		this.compositePVM.addChangeListener(this.buildListener());
		this.verifyCollectionChange();
	}

	public void testCollectionChange2() {
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.verifyCollectionChange();
	}

	private void verifyCollectionChange() {
		this.event = null;
		ModifiablePropertyValueModel<Integer> pvm = new SimplePropertyValueModel<>(Integer.valueOf(77));
		this.cvm.add(pvm);
		this.verifyEvent(10, 87);

		this.event = null;
		this.cvm.remove(pvm);
		this.verifyEvent(87, 10);

		this.event = null;
		this.cvm.clear();
		this.verifyEvent(10, 0);

		Collection<PropertyValueModel<Integer>> c2 = new ArrayList<>();
		c2.add(this.pvm1);
		c2.add(this.pvm2);
		this.event = null;
		this.cvm.setValues(c2);
		this.verifyEvent(0, 3);
	}

	public void testLazyListening1() {
		assertFalse(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildListener();

		this.compositePVM.addChangeListener(listener);
		assertTrue(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.compositePVM.removeChangeListener(listener);
		assertFalse(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}	

	public void testLazyListening2() {
		assertFalse(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildListener();

		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.compositePVM.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(this.pvm1.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}	

	public void testCtor_NPE1() {
		boolean exCaught = false;
		try {
			this.compositePVM = this.buildCompositePVM(null);
			fail("bogus: " + this.compositePVM);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE2() {
		boolean exCaught = false;
		try {
			this.compositePVM = CollectionValueModelTools.compositePropertyValueModel((CollectionValueModel<PropertyValueModel<Integer>>) this.cvm, (TransformerAdapter<Collection<Integer>, Integer>) null);
			fail("bogus: " + this.compositePVM);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullPVM() {
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		boolean exCaught = false;
		try {
			this.cvm.add(null);
			fail("bogus: " + this.cvm);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDuplicatePVM() {
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		boolean exCaught = false;
		try {
			this.cvm.add(this.pvm1);
			fail("bogus: " + this.cvm);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				CompositePropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(int oldValue, int newValue) {
		assertNotNull(this.event);
		assertEquals(this.compositePVM, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(Integer.valueOf(oldValue), this.event.getOldValue());
		assertEquals(Integer.valueOf(newValue), this.event.getNewValue());
	}
}
