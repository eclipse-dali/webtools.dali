/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.model.value.CompositeBooleanPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class CompositeBooleanPropertyValueModelTests extends TestCase {
	private SimplePropertyValueModel<Boolean> pvm1;
	private WritablePropertyValueModel<Boolean> pvm2;
	private WritablePropertyValueModel<Boolean> pvm3;
	private WritablePropertyValueModel<Boolean> pvm4;
	private Collection<WritablePropertyValueModel<Boolean>> collection;
	private SimpleCollectionValueModel<WritablePropertyValueModel<Boolean>> cvm;
	private PropertyValueModel<Boolean> compositePVM;
	PropertyChangeEvent event;


	public CompositeBooleanPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pvm1 = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.pvm2 = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.pvm3 = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.pvm4 = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.collection = new ArrayList<WritablePropertyValueModel<Boolean>>();
		this.collection.add(this.pvm1);
		this.collection.add(this.pvm2);
		this.collection.add(this.pvm3);
		this.collection.add(this.pvm4);
		this.cvm = new SimpleCollectionValueModel<WritablePropertyValueModel<Boolean>>(this.collection);
		
		this.compositePVM = this.buildCompositePVM(cvm);
	}

	private PropertyValueModel<Boolean> buildCompositePVM(CollectionValueModel<WritablePropertyValueModel<Boolean>> pvms) {
		return CompositeBooleanPropertyValueModel.and(pvms);
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
		assertTrue(this.compositePVM.getValue().booleanValue());
	}

	public void testValueAndListeners1() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertTrue(this.compositePVM.getValue().booleanValue());
		this.compositePVM.removeChangeListener(listener);
		assertNull(this.compositePVM.getValue());
	}	

	public void testValueAndListeners2() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(this.compositePVM.getValue().booleanValue());
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
		this.pvm1.setValue(Boolean.FALSE);
		this.verifyEvent(true, false);

		this.event = null;
		this.pvm2.setValue(Boolean.FALSE);
		assertNull(this.event);  // no change

		this.event = null;
		this.pvm2.setValue(Boolean.TRUE);
		assertNull(this.event);  // no change

		this.event = null;
		this.pvm1.setValue(Boolean.TRUE);
		this.verifyEvent(false, true);

		this.event = null;
		this.pvm4.setValue(Boolean.FALSE);
		this.verifyEvent(true, false);
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
		WritablePropertyValueModel<Boolean> pvm = new SimplePropertyValueModel<Boolean>(Boolean.FALSE);
		this.cvm.add(pvm);
		this.verifyEvent(true, false);

		this.event = null;
		this.cvm.remove(pvm);
		this.verifyEvent(false, true);

		this.event = null;
		this.cvm.clear();
		this.verifyEvent(Boolean.TRUE, null);

		Collection<WritablePropertyValueModel<Boolean>> c2 = new ArrayList<WritablePropertyValueModel<Boolean>>();
		c2.add(this.pvm1);
		c2.add(this.pvm2);
		this.event = null;
		this.cvm.setValues(c2);
		this.verifyEvent(null, Boolean.TRUE);
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
				CompositeBooleanPropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(boolean oldValue, boolean newValue) {
		this.verifyEvent(Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
	}

	private void verifyEvent(Boolean oldValue, Boolean newValue) {
		assertEquals(this.compositePVM, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(oldValue, this.event.getOldValue());
		assertEquals(newValue, this.event.getNewValue());
	}

}
