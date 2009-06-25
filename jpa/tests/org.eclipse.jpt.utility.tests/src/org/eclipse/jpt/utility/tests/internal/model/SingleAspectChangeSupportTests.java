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

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeAdapter;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

@SuppressWarnings("nls")
public class SingleAspectChangeSupportTests extends TestCase {

	public SingleAspectChangeSupportTests(String name) {
		super(name);
	}

	public void testAddPropertyChangeListenerInvalidClass() {
		Model model = new StateTestModel();
		boolean exCaught = false;
		PropertyChangeListener listener = new PropertyChangeAdapter();
		try {
			model.addPropertyChangeListener(listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddPropertyChangeListenerInvalidAspect() {
		Model model = new PropertyTestModel();
		boolean exCaught = false;
		PropertyChangeListener listener = new PropertyChangeAdapter();
		try {
			model.addPropertyChangeListener("bar", listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddCollectionChangeListenerInvalidClass() {
		Model model = new StateTestModel();
		boolean exCaught = false;
		CollectionChangeListener listener = new CollectionChangeAdapter();
		try {
			model.addCollectionChangeListener(listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddCollectionChangeListenerInvalidAspect() {
		Model model = new CollectionTestModel();
		boolean exCaught = false;
		CollectionChangeListener listener = new CollectionChangeAdapter();
		try {
			model.addCollectionChangeListener("bar", listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddListChangeListenerInvalidClass() {
		Model model = new StateTestModel();
		boolean exCaught = false;
		ListChangeListener listener = new ListChangeAdapter();
		try {
			model.addListChangeListener(listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddListChangeListenerInvalidAspect() {
		Model model = new ListTestModel();
		boolean exCaught = false;
		ListChangeListener listener = new ListChangeAdapter();
		try {
			model.addListChangeListener("bar", listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddTreeChangeListenerInvalidClass() {
		Model model = new StateTestModel();
		boolean exCaught = false;
		TreeChangeListener listener = new TreeChangeAdapter();
		try {
			model.addTreeChangeListener(listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddTreeChangeListenerInvalidAspect() {
		Model model = new TreeTestModel();
		boolean exCaught = false;
		TreeChangeListener listener = new TreeChangeAdapter();
		try {
			model.addTreeChangeListener("bar", listener);
			fail("bogus listener: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** test models **********

	static class StateTestModel extends AbstractModel {
		StateTestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new SingleAspectChangeSupport(this, StateChangeListener.class, null);
		}
	}

	static class PropertyTestModel extends AbstractModel {
		PropertyTestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new SingleAspectChangeSupport(this, PropertyChangeListener.class, "foo");
		}
	}

	static class CollectionTestModel extends AbstractModel {
		CollectionTestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new SingleAspectChangeSupport(this, CollectionChangeListener.class, "foo");
		}
	}

	static class ListTestModel extends AbstractModel {
		ListTestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new SingleAspectChangeSupport(this, ListChangeListener.class, "foo");
		}
	}

	static class TreeTestModel extends AbstractModel {
		TreeTestModel() {
			super();
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new SingleAspectChangeSupport(this, TreeChangeListener.class, "foo");
		}
	}

}
