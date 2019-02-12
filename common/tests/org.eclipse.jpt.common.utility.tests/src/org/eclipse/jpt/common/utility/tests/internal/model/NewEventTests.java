/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model;

import java.util.EventListener;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ChangeEvent;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

/**
 * test what it takes to add a new type of event to
 * model and change support
 */
public class NewEventTests
	extends TestCase
{
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

	interface FooChangeListener extends EventListener {
		void fooChanged(FooChangeEvent event);
	}

	static class FooChangeEvent
		extends ChangeEvent
	{
		private static final long serialVersionUID = 1L;
		public FooChangeEvent(FooModel source) {
			super(source);
		}
		public FooChangeEvent clone(Model newSource) {
			return new FooChangeEvent((FooModel) newSource);
		}
	}

	static class AbstractFooModel
		extends AbstractModel
		implements FooModel
	{
		protected synchronized FooChangeSupport getChangeSupport() {
			return (FooChangeSupport) this.changeSupport;
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

	static class FooChangeSupport
		extends ChangeSupport
	{
		FooChangeSupport(FooModel source) {
			super(source, RuntimeExceptionHandler.instance());
		}
		static final Class<FooChangeListener> FOO_CHANGE_LISTENER_CLASS = FooChangeListener.class;
		void addFooChangeListener(FooChangeListener listener) {
			this.addListener(FOO_CHANGE_LISTENER_CLASS, listener);
		}
		void removeFooChangeListener(FooChangeListener listener) {
			this.removeListener(FOO_CHANGE_LISTENER_CLASS, listener);
		}
		public boolean hasAnyFooChangeListeners() {
			return this.hasAnyListeners(FOO_CHANGE_LISTENER_CLASS);
		}
		private Iterable<FooChangeListener> getFooChangeListeners() {
			return this.getListenerList(FOO_CHANGE_LISTENER_CLASS);
		}
		private boolean hasFooChangeListener(FooChangeListener listener) {
			return IterableTools.contains(this.getFooChangeListeners(), listener);
		}
		public void fireFooChanged() {
			Iterable<FooChangeListener> listeners = this.getFooChangeListeners();
			if (listeners != null) {
				FooChangeEvent event = null;
				for (FooChangeListener listener : listeners) {
					if (this.hasFooChangeListener(listener)) {
						if (event == null) {
							// here's the reason for the duplicate code...
							event = new FooChangeEvent((FooModel) this.source);
						}
						listener.fooChanged(event);
					}
				}
			}
		}
	}

}
