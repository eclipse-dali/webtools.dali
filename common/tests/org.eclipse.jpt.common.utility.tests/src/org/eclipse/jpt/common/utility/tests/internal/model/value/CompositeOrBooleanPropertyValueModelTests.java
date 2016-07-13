/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

public class CompositeOrBooleanPropertyValueModelTests
	extends TestCase
{
	private SimplePropertyValueModel<Boolean> pvm1;
	private ModifiablePropertyValueModel<Boolean> pvm2;
	private ModifiablePropertyValueModel<Boolean> pvm3;
	private ModifiablePropertyValueModel<Boolean> pvm4;
	private Collection<ModifiablePropertyValueModel<Boolean>> collection;
	private SimpleCollectionValueModel<ModifiablePropertyValueModel<Boolean>> cvm;
	private PropertyValueModel<Boolean> compositePVM;
	PropertyChangeEvent event;


	public CompositeOrBooleanPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pvm1 = new SimplePropertyValueModel<>(Boolean.FALSE);
		this.pvm2 = new SimplePropertyValueModel<>(Boolean.FALSE);
		this.pvm3 = new SimplePropertyValueModel<>(Boolean.FALSE);
		this.pvm4 = new SimplePropertyValueModel<>(Boolean.FALSE);
		this.collection = new ArrayList<>();
		this.collection.add(this.pvm1);
		this.collection.add(this.pvm2);
		this.collection.add(this.pvm3);
		this.collection.add(this.pvm4);
		this.cvm = new SimpleCollectionValueModel<>(this.collection);
		
		this.compositePVM = this.buildCompositePVM(this.cvm);
	}

	private PropertyValueModel<Boolean> buildCompositePVM(CollectionValueModel<ModifiablePropertyValueModel<Boolean>> pvms) {
		return CollectionValueModelTools.or(pvms);
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
		assertFalse(this.compositePVM.getValue().booleanValue());
	}

	public void testGetValue2() {
		this.compositePVM = PropertyValueModelTools.or(this.pvm1, this.pvm2, this.pvm3, this.pvm4);
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertFalse(this.compositePVM.getValue().booleanValue());
	}

	public void testGetValue3() {
		this.compositePVM = PropertyValueModelTools.or(Arrays.asList(this.pvm1, this.pvm2, this.pvm3, this.pvm4));
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertFalse(this.compositePVM.getValue().booleanValue());
	}

	public void testValueAndListeners1() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertFalse(this.compositePVM.getValue().booleanValue());
		this.compositePVM.removeChangeListener(listener);
		assertNull(this.compositePVM.getValue());
	}	

	public void testValueAndListeners2() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(this.compositePVM.getValue().booleanValue());
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
		this.pvm1.setValue(Boolean.TRUE);
		this.verifyEvent(false, true);

		this.event = null;
		this.pvm2.setValue(Boolean.TRUE);
		assertNull(this.event);  // no change

		this.event = null;
		this.pvm2.setValue(Boolean.FALSE);
		assertNull(this.event);  // no change

		this.event = null;
		this.pvm1.setValue(Boolean.FALSE);
		this.verifyEvent(true, false);

		this.event = null;
		this.pvm4.setValue(Boolean.TRUE);
		this.verifyEvent(false, true);
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
		ModifiablePropertyValueModel<Boolean> pvm = new SimplePropertyValueModel<>(Boolean.TRUE);
		this.cvm.add(pvm);
		this.verifyEvent(false, true);

		this.event = null;
		this.cvm.remove(pvm);
		this.verifyEvent(true, false);

		this.event = null;
		this.cvm.add(pvm);
		this.verifyEvent(false, true);

		this.event = null;
		this.cvm.clear();
		this.verifyEvent(true, false);

		this.event = null;
		this.cvm.add(pvm);
		this.verifyEvent(false, true);

		Collection<ModifiablePropertyValueModel<Boolean>> c2 = new ArrayList<>();
		c2.add(this.pvm1);
		c2.add(this.pvm2);
		this.event = null;
		this.cvm.setValues(c2);
		this.verifyEvent(true, false);
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
				CompositeOrBooleanPropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(boolean oldValue, boolean newValue) {
		this.verifyEvent(Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
	}

	private void verifyEvent(Boolean oldValue, Boolean newValue) {
		assertNotNull(this.event);
		assertEquals(this.compositePVM, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(oldValue, this.event.getOldValue());
		assertEquals(newValue, this.event.getNewValue());
	}

}
