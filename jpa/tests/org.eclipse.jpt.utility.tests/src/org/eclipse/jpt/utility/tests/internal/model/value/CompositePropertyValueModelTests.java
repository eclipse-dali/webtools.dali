/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.model.value.CompositePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class CompositePropertyValueModelTests extends TestCase {
	private SimplePropertyValueModel<Integer> pvm1;
	private WritablePropertyValueModel<Integer> pvm2;
	private WritablePropertyValueModel<Integer> pvm3;
	private WritablePropertyValueModel<Integer> pvm4;
	private Collection<WritablePropertyValueModel<Integer>> collection;
	private SimpleCollectionValueModel<WritablePropertyValueModel<Integer>> cvm;
	private PropertyValueModel<Integer> compositePVM;
	PropertyChangeEvent event;


	public CompositePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pvm1 = new SimplePropertyValueModel<Integer>(Integer.valueOf(1));
		this.pvm2 = new SimplePropertyValueModel<Integer>(Integer.valueOf(2));
		this.pvm3 = new SimplePropertyValueModel<Integer>(Integer.valueOf(3));
		this.pvm4 = new SimplePropertyValueModel<Integer>(Integer.valueOf(4));
		this.collection = new ArrayList<WritablePropertyValueModel<Integer>>();
		this.collection.add(this.pvm1);
		this.collection.add(this.pvm2);
		this.collection.add(this.pvm3);
		this.collection.add(this.pvm4);
		this.cvm = new SimpleCollectionValueModel<WritablePropertyValueModel<Integer>>(this.collection);
		
		this.compositePVM = this.buildCompositePVM(cvm);
	}

	private <T extends PropertyValueModel<?>> PropertyValueModel<Integer> buildCompositePVM(CollectionValueModel<T> pvms) {
		return new CompositePropertyValueModel<Integer>(pvms) {
			@SuppressWarnings("unchecked")
			@Override
			protected Integer buildValue() {
				int sum = 0;
				for (Object each : this.collectionHolder) {
					sum += ((PropertyValueModel<Integer>) each).getValue().intValue();
				}
				return Integer.valueOf(sum);
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue() {
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
		WritablePropertyValueModel<Integer> pvm = new SimplePropertyValueModel<Integer>(Integer.valueOf(77));
		this.cvm.add(pvm);
		this.verifyEvent(10, 87);

		this.event = null;
		this.cvm.remove(pvm);
		this.verifyEvent(87, 10);

		this.event = null;
		this.cvm.clear();
		this.verifyEvent(10, 0);

		Collection<WritablePropertyValueModel<Integer>> c2 = new ArrayList<WritablePropertyValueModel<Integer>>();
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

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				CompositePropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(int oldValue, int newValue) {
		assertEquals(this.compositePVM, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(Integer.valueOf(oldValue), this.event.getOldValue());
		assertEquals(Integer.valueOf(newValue), this.event.getNewValue());
	}

}
