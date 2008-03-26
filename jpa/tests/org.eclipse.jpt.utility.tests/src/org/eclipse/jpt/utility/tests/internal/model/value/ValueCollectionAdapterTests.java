/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueCollectionAdapter;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ValueCollectionAdapterTests extends TestCase {
	private Junk junk;
	private SimplePropertyValueModel<Junk> junkHolder;
	private ValueCollectionAdapter<Junk> junkHolder2;


	public ValueCollectionAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.junk = new Junk("foo");
		this.junkHolder = new SimplePropertyValueModel<Junk>(this.junk);
		this.junkHolder2 = new ValueCollectionAdapter<Junk>(this.junkHolder, Junk.STUFF_COLLECTION);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWrappedPVM() {
		Junk junk2 = new Junk("bar");
		LocalListener l = new LocalListener(this.junkHolder2, this.junk, junk2);
		this.junkHolder2.addPropertyChangeListener(l);
		this.junkHolder.setValue(junk2);
		assertTrue(l.eventReceived());
	}

	public void testHasPropertyChangeListeners() throws Exception {
		assertFalse(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		LocalListener l = new LocalListener(this.junkHolder2, null, this.junk);
		this.junkHolder2.addPropertyChangeListener(l);
		assertTrue(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.junkHolder2.removePropertyChangeListener(l);
		assertFalse(this.junkHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(this.junkHolder2.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testHasStateChangeListeners() throws Exception {
		assertFalse(this.junk.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertFalse(this.junkHolder2.hasAnyStateChangeListeners());

		LocalListener l = new LocalListener(this.junkHolder2, null, this.junk);
		this.junkHolder2.addStateChangeListener(l);
		assertTrue(this.junk.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertTrue(this.junkHolder2.hasAnyStateChangeListeners());

		this.junkHolder2.removeStateChangeListener(l);
		assertFalse(this.junk.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertFalse(this.junkHolder2.hasAnyStateChangeListeners());
	}

	public void testCollectionAdd() {
		LocalListener l = new LocalListener(this.junkHolder2);
		this.junkHolder2.addStateChangeListener(l);
		this.junk.addStuff("bar");
		assertTrue(l.eventReceived());
	}

	public void testCollectionRemove() {
		LocalListener l = new LocalListener(this.junkHolder2);
		this.junkHolder2.addStateChangeListener(l);
		this.junk.removeStuff("foo");
		assertTrue(l.eventReceived());
	}


	private class LocalListener implements PropertyChangeListener, StateChangeListener {
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
		public void propertyChanged(PropertyChangeEvent e) {
			this.eventReceived = true;
			assertEquals(this.source, e.getSource());
			assertEquals(this.oldValue, e.getOldValue());
			assertEquals(this.newValue, e.getNewValue());
			assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		}
		public void stateChanged(StateChangeEvent e) {
			this.eventReceived = true;
			assertEquals(this.source, e.getSource());
			assertNull(e.getAspectName());
		}
		boolean eventReceived() {
			return this.eventReceived;
		}
	}


	private class Junk extends AbstractModel {
		private Collection<String> stuff;
			public static final String STUFF_COLLECTION = "stuff";

		public Junk(String stuffItem) {
			this.stuff = new ArrayList<String>();
			this.stuff.add(stuffItem);
		}
	
		public void addStuff(String stuffItem) {
			this.addItemToCollection(stuffItem, this.stuff, STUFF_COLLECTION);
		}
		
		public void removeStuff(String stuffItem) {
			this.removeItemFromCollection(stuffItem, this.stuff, STUFF_COLLECTION);
		}
	
		@Override
		public String toString() {
			return "Junk(" + this.stuff + ")";
		}
	
	}

}
