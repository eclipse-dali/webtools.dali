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
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ListCompositePropertyValueModelTests
	extends TestCase
{
	private SimplePropertyValueModel<String> pvm1;
	private ModifiablePropertyValueModel<String> pvm2;
	private ModifiablePropertyValueModel<String> pvm3;
	private ModifiablePropertyValueModel<String> pvm4;
	private List<PropertyValueModel<String>> collection;
	private SimpleListValueModel<PropertyValueModel<String>> lvm;
	private PropertyValueModel<String> compositePVM;
	PropertyChangeEvent event;


	public ListCompositePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pvm1 = new SimplePropertyValueModel<>("111");
		this.pvm2 = new SimplePropertyValueModel<>("222");
		this.pvm3 = new SimplePropertyValueModel<>("333");
		this.pvm4 = new SimplePropertyValueModel<>("444");
		this.collection = new ArrayList<>();
		this.collection.add(this.pvm1);
		this.collection.add(this.pvm2);
		this.collection.add(this.pvm3);
		this.collection.add(this.pvm4);
		this.lvm = new LocalSimpleListValueModel<>(this.collection);
		
		this.compositePVM = this.buildCompositePVM(this.lvm);
	}

	public static class LocalSimpleListValueModel<E>
		extends SimpleListValueModel<E>
	{
		public LocalSimpleListValueModel(List<E> list) {
			super(list);
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new ChangeSupport(this, RuntimeExceptionHandler.instance());
		}
	}

	private PropertyValueModel<String> buildCompositePVM(ListValueModel<PropertyValueModel<String>> pvms) {
		return ListValueModelTools.compositePropertyValueModel(pvms, new LocalTransformer());
	}

	public static class LocalTransformer
		extends TransformerAdapter<List<String>, String>
	{
		@Override
		public String transform(List<String> strings) {
			StringBuilder sb = new StringBuilder();
			for (Iterator<String> stream = strings.iterator(); stream.hasNext(); ) {
				String string = stream.next();
				sb.append(string);
				if (stream.hasNext()) {
					sb.append("-");
				}
			}
			return sb.toString();
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
		assertEquals("111-222-333-444", this.compositePVM.getValue());
	}

	public void testGetValue2() {
		ArrayList<PropertyValueModel<String>> list = new ArrayList<>();
		ListTools.addAll(list, 0, this.lvm);
		this.compositePVM = ListValueModelTools.compositePropertyValueModel(list, new LocalTransformer());
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals("111-222-333-444", this.compositePVM.getValue());
	}

	public void testGetValue3() {
		this.compositePVM = ListValueModelTools.compositePropertyValueModel(new LocalTransformer(), this.pvm1, this.pvm2, this.pvm3, this.pvm4);
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals("111-222-333-444", this.compositePVM.getValue());
	}

	public void testValueAndListeners1() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addChangeListener(listener);
		assertEquals("111-222-333-444", this.compositePVM.getValue());
		this.compositePVM.removeChangeListener(listener);
		assertNull(this.compositePVM.getValue());
	}	

	public void testValueAndListeners2() {
		assertNull(this.compositePVM.getValue());
		ChangeListener listener = this.buildListener();
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertEquals("111-222-333-444", this.compositePVM.getValue());
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
		this.pvm1.setValue("555");
		this.verifyEvent("111-222-333-444", "555-222-333-444");

		this.event = null;
		this.pvm4.setValue("000");
		this.verifyEvent("555-222-333-444", "555-222-333-000");
	}

	public void testListChange1() {
		this.compositePVM.addChangeListener(this.buildListener());
		this.verifyListChange();
	}

	public void testListChange2() {
		this.compositePVM.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.verifyListChange();
	}

	private void verifyListChange() {
		this.event = null;
		ModifiablePropertyValueModel<String> pvm7 = new SimplePropertyValueModel<>("777");
		this.lvm.add(pvm7);
		this.verifyEvent("111-222-333-444", "111-222-333-444-777");

		this.event = null;
		this.lvm.remove(pvm7);
		this.verifyEvent("111-222-333-444-777", "111-222-333-444");

		this.event = null;
		ModifiablePropertyValueModel<String> pvmX = new SimplePropertyValueModel<>("XXX");
		this.lvm.add(2, pvmX);
		this.verifyEvent("111-222-333-444", "111-222-XXX-333-444");

		this.event = null;
		this.lvm.move(3, 2);
		this.verifyEvent("111-222-XXX-333-444", "111-222-333-XXX-444");

		this.event = null;
		ModifiablePropertyValueModel<String> pvmZ = new SimplePropertyValueModel<>("ZZZ");
		this.lvm.set(3, pvmZ);
		this.verifyEvent("111-222-333-XXX-444", "111-222-333-ZZZ-444");

		this.event = null;
		this.lvm.remove(3);
		this.verifyEvent("111-222-333-ZZZ-444", "111-222-333-444");

		this.event = null;
		this.lvm.clear();
		this.verifyEvent("111-222-333-444", "");

		List<PropertyValueModel<String>> c2 = new ArrayList<>();
		c2.add(this.pvm1);
		c2.add(this.pvm2);
		this.event = null;
		this.lvm.setListValues(c2);
		this.verifyEvent("", "111-222");
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
			this.compositePVM = ListValueModelTools.compositePropertyValueModel((ListValueModel<PropertyValueModel<String>>) this.lvm, (TransformerAdapter<List<String>, String>) null);
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
			this.lvm.add(null);
			fail("bogus: " + this.lvm);
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
			this.lvm.add(this.pvm1);
			fail("bogus: " + this.lvm);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				ListCompositePropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String oldValue, String newValue) {
		assertNotNull(this.event);
		assertEquals(this.compositePVM, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(oldValue, this.event.getOldValue());
		assertEquals(newValue, this.event.getNewValue());
	}
}
