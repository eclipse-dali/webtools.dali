/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValuePropertyAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ValuePropertyAdapterTests extends TestCase {
	private Junk junk;
	private SimplePropertyValueModel<Junk> junkHolder;
	private ValuePropertyAdapter<Junk> junkHolder2;


	public ValuePropertyAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.junk = new Junk("foo");
		this.junkHolder = new SimplePropertyValueModel<Junk>(this.junk);
		this.junkHolder2 = new ValuePropertyAdapter<Junk>(this.junkHolder, Junk.NAME_PROPERTY);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWrappedPVM() {
		Junk junk2 = new Junk("bar");
		LocalListener l = new LocalListener(this.junkHolder2, this.junk, junk2);
		this.junkHolder2.addChangeListener(l);
		this.junkHolder.setValue(junk2);
		assertTrue(l.eventReceived());
	}

	public void testHasPropertyChangeListeners() throws Exception {
		assertFalse(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		LocalListener l = new LocalListener(this.junkHolder2, null, this.junk);
		this.junkHolder2.addChangeListener(l);
		assertTrue(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.junkHolder2.removeChangeListener(l);
		assertFalse(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testHasStateChangeListeners() throws Exception {
		assertFalse(this.junk.hasAnyPropertyChangeListeners(Junk.NAME_PROPERTY));
		assertFalse(this.junkHolder2.hasAnyStateChangeListeners());

		LocalListener l = new LocalListener(this.junkHolder2, null, this.junk);
		this.junkHolder2.addStateChangeListener(l);
		assertTrue(this.junk.hasAnyPropertyChangeListeners(Junk.NAME_PROPERTY));
		assertTrue(this.junkHolder2.hasAnyStateChangeListeners());

		this.junkHolder2.removeStateChangeListener(l);
		assertFalse(this.junk.hasAnyPropertyChangeListeners(Junk.NAME_PROPERTY));
		assertFalse(this.junkHolder2.hasAnyStateChangeListeners());
	}

	public void testChangeProperty() {
		LocalListener l = new LocalListener(this.junkHolder2, null, this.junk);
		this.junkHolder2.addStateChangeListener(l);
		this.junk.setName("bar");
		assertTrue(l.eventReceived());
	}


	class LocalListener extends ChangeAdapter {
		private boolean eventReceived = false;
		private final Object source;
		private final Object oldValue;
		private final Object newValue;
		LocalListener(Object source) {
			this(source, null, null);
		}
		LocalListener(Object source, Object oldValue, Object newValue) {
			super();
			this.source = source;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}
		@Override
		public void propertyChanged(PropertyChangeEvent e) {
			this.eventReceived = true;
			assertEquals(this.source, e.getSource());
			assertEquals(this.oldValue, e.getOldValue());
			assertEquals(this.newValue, e.getNewValue());
			assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		}
		@Override
		public void stateChanged(StateChangeEvent e) {
			this.eventReceived = true;
			assertEquals(this.source, e.getSource());
		}
		boolean eventReceived() {
			return this.eventReceived;
		}
	}

	class Junk extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";

		public Junk(String name) {
			this.name = name;
		}
	
		public void setName(String name) {
			String old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		
		@Override
		public String toString() {
			return "Junk(" + this.name + ")";
		}
	
	}

}
