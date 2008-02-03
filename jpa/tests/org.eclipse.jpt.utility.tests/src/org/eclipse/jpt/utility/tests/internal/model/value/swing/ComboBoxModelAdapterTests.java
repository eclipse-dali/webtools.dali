/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.ListModel;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ComboBoxModelAdapter;
import org.eclipse.jpt.utility.internal.swing.Displayable;
import org.eclipse.jpt.utility.internal.swing.SimpleDisplayable;
import org.eclipse.jpt.utility.tests.internal.model.value.CoordinatedList;

import junit.framework.TestCase;

public class ComboBoxModelAdapterTests extends TestCase {

	public ComboBoxModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// nothing yet...
	}

	@Override
	protected void tearDown() throws Exception {
		// nothing yet...
		super.tearDown();
	}

	public void testHasListeners() throws Exception {
		SimpleListValueModel<Displayable> listHolder = this.buildListHolder();
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		SimplePropertyValueModel<Object> selectionHolder = new SimplePropertyValueModel<Object>(listHolder.iterator().next());
		assertFalse(selectionHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		ComboBoxModel comboBoxModel = new ComboBoxModelAdapter(listHolder, selectionHolder);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(selectionHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(comboBoxModel);

		CoordinatedList<Displayable> synchList = new CoordinatedList<Displayable>(comboBoxModel);
		PropertyChangeListener selectionListener = this.buildSelectionListener();
		selectionHolder.addPropertyChangeListener(PropertyValueModel.VALUE, selectionListener);
		assertTrue(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(selectionHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasListeners(comboBoxModel);

		comboBoxModel.removeListDataListener(synchList);
		selectionHolder.removePropertyChangeListener(PropertyValueModel.VALUE, selectionListener);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(selectionHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(comboBoxModel);
	}

	private PropertyChangeListener buildSelectionListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent evt) {
				// do nothing...
			}
		};
	}

	private void verifyHasNoListeners(ListModel listModel) throws Exception {
		boolean hasNoListeners = ((Boolean) ClassTools.executeMethod(listModel, "hasNoListDataListeners")).booleanValue();
		assertTrue(hasNoListeners);
	}

	private void verifyHasListeners(ListModel listModel) throws Exception {
		boolean hasListeners = ((Boolean) ClassTools.executeMethod(listModel, "hasListDataListeners")).booleanValue();
		assertTrue(hasListeners);
	}

	private SimpleListValueModel<Displayable> buildListHolder() {
		return new SimpleListValueModel<Displayable>(this.buildList());
	}

	private List<Displayable> buildList() {
		List<Displayable> list = new ArrayList<Displayable>();
		this.populateCollection(list);
		return list;
	}

	private void populateCollection(Collection<Displayable> c) {
		c.add(new SimpleDisplayable("foo"));
		c.add(new SimpleDisplayable("bar"));
		c.add(new SimpleDisplayable("baz"));
		c.add(new SimpleDisplayable("joo"));
		c.add(new SimpleDisplayable("jar"));
		c.add(new SimpleDisplayable("jaz"));
	}

}
