/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.tests.internal.TestTools;

/**
 * test what it takes to add a new type of event to
 * model and change support
 */
public class NewEventTests extends TestCase {
	private Foo foo;

	public NewEventTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.foo = new Foo();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testHasNoFooChangeListeners() {
		assertTrue(this.foo.hasNoFooChangeListeners());
		LocalListener listener = new LocalListener();
		this.foo.addFooChangeListener(listener);
		assertFalse(this.foo.hasNoFooChangeListeners());
		this.foo.removeFooChangeListener(listener);
		assertTrue(this.foo.hasNoFooChangeListeners());
	}

	public void testHasAnyFooChangeListeners() {
		assertFalse(this.foo.hasAnyFooChangeListeners());
		LocalListener listener = new LocalListener();
		this.foo.addFooChangeListener(listener);
		assertTrue(this.foo.hasAnyFooChangeListeners());
		this.foo.removeFooChangeListener(listener);
		assertFalse(this.foo.hasAnyFooChangeListeners());
	}

	public void testFireFooChangeEvent() {
		LocalListener listener = new LocalListener();
		assertFalse(listener.receivedFooEvent);
		this.foo.addFooChangeListener(listener);
		this.foo.foo();
		assertTrue(listener.receivedFooEvent);
	}


	// ********** harness classes **********

	class Foo extends AbstractFooModel {
		Foo() {
			super();
		}
		void foo() {
			this.fireFooChangeEvent();
		}
	}

	class LocalListener implements FooChangeListener {
		boolean receivedFooEvent = false;
		LocalListener() {
			super();
		}
		public void fooChanged(FooChangeEvent event) {
			this.receivedFooEvent = true;
		}
	}

	interface FooModel extends Model {
		void addFooChangeListener(FooChangeListener listener);
		void removeFooChangeListener(FooChangeListener listener);
	}

	interface FooChangeListener extends ChangeListener {
		void fooChanged(FooChangeEvent event);
	}

	static class FooChangeEvent extends ChangeEvent {
		private static final long serialVersionUID = 1L;
		public FooChangeEvent(FooModel source) {
			super(source);
		}
		@Override
		public String getAspectName() {
			return null;  // the point of the event is that the name is unknown...
		}
		@Override
		public FooChangeEvent cloneWithSource(Model newSource) {
			return new FooChangeEvent((FooModel) newSource);
		}
	}

	static class AbstractFooModel extends AbstractModel implements FooModel {
		@Override
		protected synchronized FooChangeSupport getChangeSupport() {
			return (FooChangeSupport) super.getChangeSupport();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new FooChangeSupport(this);
		}
		public void addFooChangeListener(FooChangeListener listener) {
			this.getChangeSupport().addFooChangeListener(listener);
		}
		public void removeFooChangeListener(FooChangeListener listener) {
			this.getChangeSupport().removeFooChangeListener(listener);
		}
		protected void fireFooChangeEvent() {
			this.getChangeSupport().fireFooChanged();
		}
		public boolean hasAnyFooChangeListeners() {
			return this.getChangeSupport().hasAnyFooChangeListeners();
		}
		public boolean hasNoFooChangeListeners() {
			return ! this.hasAnyFooChangeListeners();
		}
	}

	static class FooChangeSupport extends ChangeSupport {
		FooChangeSupport(FooModel source) {
			super(source);
		}
		protected static final Class<FooChangeListener> FOO_CHANGE_LISTENER_CLASS = FooChangeListener.class;
		void addFooChangeListener(FooChangeListener listener) {
			this.addListener(FOO_CHANGE_LISTENER_CLASS, listener);
		}
		void removeFooChangeListener(FooChangeListener listener) {
			this.removeListener(FOO_CHANGE_LISTENER_CLASS, listener);
		}
		public boolean hasAnyFooChangeListeners() {
			return this.hasAnyListeners(FOO_CHANGE_LISTENER_CLASS);
		}
		private FooChangeListener[] fooChangeListeners() {
			return (FooChangeListener[]) this.getListeners(FOO_CHANGE_LISTENER_CLASS);
		}
		public void fireFooChanged() {
			FooChangeListener[] targets = null;
			synchronized (this) {
				FooChangeListener[] fooChangeListeners = this.fooChangeListeners();
				if (fooChangeListeners != null) {
					targets = fooChangeListeners.clone();
				}
			}
			if (targets != null) {
				FooChangeEvent event = null;
				for (FooChangeListener target : targets) {
					boolean stillListening;
					synchronized (this) {
						stillListening = CollectionTools.contains(this.fooChangeListeners(), target);
					}
					if (stillListening) {
						if (event == null) {
							// here's the reason for the duplicate code...
							event = new FooChangeEvent((FooModel) this.source);
						}
						target.fooChanged(event);
					}
				}
			}
			this.aspectChanged(null);
		}
	}

}
