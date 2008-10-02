/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.swt;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.ui.internal.swt.AbstractComboModelAdapter;
import org.eclipse.jpt.ui.internal.swt.AbstractComboModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.ui.internal.swt.AbstractComboModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.swing.SimpleDisplayable;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public abstract class AbstractComboModelAdapterTest {

	private Model model;
	private WritablePropertyValueModel<SimpleDisplayable> selectedItemHolder;
	private Shell shell;
	private WritablePropertyValueModel<Model> subjectHolder;

	protected abstract AbstractComboModelAdapter<SimpleDisplayable> buildEditableComboModelAdapter();

	protected final ListValueModel<SimpleDisplayable> buildEmptyListHolder() {
		return new SimpleListValueModel<SimpleDisplayable>();
	}

	private List<SimpleDisplayable> buildList() {
		List<SimpleDisplayable> list = new ArrayList<SimpleDisplayable>();
		populateCollection(list);
		return list;
	}

	protected final ListValueModel<SimpleDisplayable> buildListHolder() {
		return new ListAspectAdapter<Model, SimpleDisplayable>(subjectHolder, Model.ITEMS_LIST) {
			@Override
			protected ListIterator<SimpleDisplayable> listIterator_() {
				return subject.items();
			}

			@Override
			protected int size_() {
				return subject.itemsSize();
			}
		};
	}

	protected abstract AbstractComboModelAdapter<SimpleDisplayable> buildReadOnlyComboModelAdapter();

	private SimpleDisplayable buildSelectedItem() {
		return new SimpleDisplayable("baz");
	}

	private WritablePropertyValueModel<SimpleDisplayable> buildSelectedItemHolder() {
		return new PropertyAspectAdapter<Model, SimpleDisplayable>(subjectHolder, Model.ITEM_PROPERTY) {
			@Override
			protected SimpleDisplayable buildValue_() {
				return subject.getItem();
			}

			@Override
			protected void setValue_(SimpleDisplayable value) {
				subject.setItem(value);
			}
		};
	}

	protected final StringConverter<SimpleDisplayable> buildStringConverter() {
		return new StringConverter<SimpleDisplayable>() {
			public String convertToString(SimpleDisplayable value) {
				return (value == null) ? "" : value.displayString();
			}
		};
	}

	private WritablePropertyValueModel<Model> buildSubjectHolder() {
		return new SimplePropertyValueModel<Model>();
	}

	public abstract String comboSelectedItem();

	protected abstract boolean emptyComboCanHaveSelectedValue();

	protected abstract String itemAt(int index);

	protected abstract int itemCounts();

	private void populateCollection(Collection<SimpleDisplayable> c) {
		c.add(new SimpleDisplayable("foo"));
		c.add(new SimpleDisplayable("bar"));
		c.add(new SimpleDisplayable("baz"));
		c.add(new SimpleDisplayable("joo"));
		c.add(new SimpleDisplayable("jar"));
		c.add(new SimpleDisplayable("jaz"));
	}

	protected final WritablePropertyValueModel<SimpleDisplayable> selectedItemHolder() {
		return selectedItemHolder;
	}

	@Before
	public void setUp() throws Exception {

		shell              = new Shell(Display.getCurrent());
		model              = new Model();
		subjectHolder      = buildSubjectHolder();
		selectedItemHolder = buildSelectedItemHolder();
	}

	protected final Shell shell() {
		return shell;
	}

	protected final WritablePropertyValueModel<Model> subjectHolder() {
		return subjectHolder;
	}

	@After
	public void tearDown() throws Exception {

		if (!shell.isDisposed()) {
			shell.dispose();
		}

		shell              = null;
		subjectHolder      = null;
		selectedItemHolder = null;
	}

	private void testItems() {

		assertEquals(
			"The count of items is not in sync with the model",
			model.itemsSize(),
			itemCounts()
		);

		for (int index = 0; index < model.itemsSize(); index++) {
			assertEquals(
				"The item at index " + index + " is not the same between the model and the combo",
				model.itemAt(index).displayString(),
				itemAt(index)
			);
		}
	}

	@Test
	public void testNonNullSubjectAfter_AddedAfter_ReadOnly() throws Exception {

		buildReadOnlyComboModelAdapter();
		testRemoveItems_AddedAfter();
	}

	@Test
	public void testNonNullSubjectAfter_AddedAfter_RemoveItems_Editable() throws Exception {

		buildEditableComboModelAdapter();
		testRemoveItems_AddedAfter();
	}

	@Test
	public void testNonNullSubjectAfter_AddedBefore_RemoveItems_Editable() throws Exception {

		subjectHolder.setValue(model);
		model.addItems(buildList());

		buildEditableComboModelAdapter();
		testSelectedItem(null);
		testItems();

		ArrayList<SimpleDisplayable> items = new ArrayList<SimpleDisplayable>();
		items.add(model.itemAt(0));
		items.add(model.itemAt(3));
		model.removeItems(items.iterator());
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_AddedBefore_RemoveItems_ReadOnly() throws Exception {

		subjectHolder.setValue(model);
		model.addItems(buildList());

		buildReadOnlyComboModelAdapter();
		testSelectedItem(null);
		testItems();

		ArrayList<SimpleDisplayable> items = new ArrayList<SimpleDisplayable>();
		items.add(model.itemAt(0));
		items.add(model.itemAt(3));
		model.removeItems(items.iterator());
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsAfterAfter_Editable() throws Exception {

		buildEditableComboModelAdapter();

		subjectHolder.setValue(model);
		model.addItems(buildList());

		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsAfterAfter_ReadOnly() throws Exception {

		buildReadOnlyComboModelAdapter();

		subjectHolder.setValue(model);
		model.addItems(buildList());

		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsAfterBefore_Editable() throws Exception {

		buildEditableComboModelAdapter();

		model.addItems(buildList());
		subjectHolder.setValue(model);

		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsAfterBefore_ReadOnly() throws Exception {

		buildReadOnlyComboModelAdapter();

		model.addItems(buildList());
		subjectHolder.setValue(model);

		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsBefore_Editable() throws Exception {

		model.addItems(buildList());
		buildEditableComboModelAdapter();

		subjectHolder.setValue(model);
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_ItemsBefore_ReadOnly() throws Exception {

		model.addItems(buildList());
		buildReadOnlyComboModelAdapter();

		subjectHolder.setValue(model);
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_NoItems_Editable() throws Exception {

		buildEditableComboModelAdapter();

		subjectHolder.setValue(model);
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_NullSelectedItem_NoItems_ReadOnly() throws Exception {

		buildReadOnlyComboModelAdapter();

		subjectHolder.setValue(model);
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectAfter_SelectedItemChanged_ReadOnly() throws Exception {

		subjectHolder.setValue(model);
		model.addItems(buildList());

		SimpleDisplayable selectedItem = model.itemAt(0);

		AbstractComboModelAdapter<SimpleDisplayable> adapter = buildEditableComboModelAdapter();
		SelectionListener selectionListener = new SelectionListener();
		adapter.addSelectionChangeListener(selectionListener);

		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());
		testSelectedItem(null);

		testSelectedItemChanged(selectedItem, selectionListener);
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemAfter_Editable() throws Exception {

		SimpleDisplayable selectedItem = buildSelectedItem();
		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemAfter_Items_Editable() throws Exception {

		SimpleDisplayable selectedItem = buildSelectedItem();
		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemAfter_Items_ReadOnly() throws Exception {

		List<SimpleDisplayable> list = buildList();
		SimpleDisplayable selectedItem = list.get(0);
		subjectHolder.setValue(model);

		buildReadOnlyComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemAfter_ReadOnly() throws Exception {

		List<SimpleDisplayable> list = buildList();
		SimpleDisplayable selectedItem = list.get(0);
		subjectHolder.setValue(model);

		buildReadOnlyComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemBefore_Editable() throws Exception {

		model.addItems(buildList());
		SimpleDisplayable selectedItem = model.itemAt(0);
		subjectHolder.setValue(model);

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		model.clearItemCalledFlag();
		buildEditableComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemBefore_Items_Editable() throws Exception {

		model.addItems(buildList());
		SimpleDisplayable selectedItem = model.itemAt(0);
		subjectHolder.setValue(model);

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		model.clearItemCalledFlag();
		buildEditableComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemBefore_Items_ReadOnly() throws Exception {

		List<SimpleDisplayable> list = buildList();
		SimpleDisplayable selectedItem = list.get(0);
		subjectHolder.setValue(model);

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		model.clearItemCalledFlag();
		buildReadOnlyComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NonNullSelectedItemBefore_ReadOnly() throws Exception {

		List<SimpleDisplayable> list = buildList();
		SimpleDisplayable selectedItem = list.get(0);
		subjectHolder.setValue(model);

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());

		model.clearItemCalledFlag();
		buildReadOnlyComboModelAdapter();
		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());

		testSelectedItem(selectedItem);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NullSelectedItem_Items_Editable() throws Exception {

		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();

		assertFalse("The item wasn't supposed to be modified", model.isSetItemCalled());
		assertNull("The selected item is supposed to remain null", model.getItem());
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NullSelectedItem_Items_ReadOnly() throws Exception {

		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();

		assertFalse("The item wasn't supposed to be modified", model.isSetItemCalled());
		assertNull("The selected item is supposed to remain null", model.getItem());
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NullSelectedItem_NoItems_Editable() throws Exception {

		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();

		assertFalse("The item wasn't supposed to be modified", model.isSetItemCalled());
		assertNull("The selected item is supposed to remain null", model.getItem());
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_NullSelectedItem_NoItems_ReadOnly() throws Exception {

		subjectHolder.setValue(model);

		buildEditableComboModelAdapter();

		assertFalse("The item wasn't supposed to be modified", model.isSetItemCalled());
		assertNull("The selected item is supposed to remain null", model.getItem());
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNonNullSubjectBefore_SelectedItemChanged_Editable() throws Exception {

		model.addItems(buildList());

		SimpleDisplayable selectedItem = model.itemAt(3);
		subjectHolder.setValue(model);

		AbstractComboModelAdapter<SimpleDisplayable> adapter = buildEditableComboModelAdapter();
		SelectionListener selectionListener = new SelectionListener();
		adapter.addSelectionChangeListener(selectionListener);

		assertFalse("The selected item wasn't supposed to be modified", model.isSetItemCalled());
		testSelectedItem(null);

		testSelectedItemChanged(selectedItem, selectionListener);
	}

	private void testNullSubject() throws Exception {

		assertNull("The selected item should be null", selectedItemHolder.getValue());

		selectedItemHolder.setValue(buildSelectedItem());
		assertFalse("The item wasn't supposed to be modified", model.isSetItemCalled());

		// Null because the subject holder doesn't have the subject set
		testSelectedItem(null);
		testItems();
	}

	@Test
	public void testNullSubject_NullSelectedItem_Items_Editable() throws Exception {
		buildEditableComboModelAdapter();
		testNullSubject();
		testItems();
		testSelectedItem(null);
	}

	@Test
	public void testNullSubject_NullSelectedItem_Items_ReadOnly() throws Exception {
		buildReadOnlyComboModelAdapter();
		testNullSubject();
		testItems();
		testSelectedItem(null);
	}

	@Test
	public void testNullSubject_NullSelectedItem_NoItems_Editable() throws Exception {
		buildEditableComboModelAdapter();
		testNullSubject();
		testItems();
		testSelectedItem(null);
	}

	@Test
	public void testNullSubject_NullSelectedItem_NoItems_ReadOnly() throws Exception {
		buildReadOnlyComboModelAdapter();
		testNullSubject();
		testItems();
		testSelectedItem(null);
	}

	private void testRemoveItems_AddedAfter() {

		subjectHolder.setValue(model);
		model.addItems(buildList());

		testSelectedItem(null);
		testItems();
		testSelectedItem(null);

		ArrayList<SimpleDisplayable> items = new ArrayList<SimpleDisplayable>();
		items.add(model.itemAt(0));
		items.add(model.itemAt(3));
		model.removeItems(items.iterator());

		testItems();
		testSelectedItem(null);
	}

	private void testSelectedItem(SimpleDisplayable selectedItem) {

		if (selectedItem == null) {

			assertNull(
				"The selected item is supposed to be null",
				model.getItem()
			);

			assertEquals(
				"The combo's selected item should be null",
				"",
				comboSelectedItem()
			);
		}
		else if (!emptyComboCanHaveSelectedValue()) {

			assertEquals(
				"The selected item wasn't set correctly",
				selectedItem,
				model.getItem()
			);

			assertEquals(
				"The combo's selected item should be null",
				"",
				comboSelectedItem()
			);
		}
		else {

			assertEquals(
				"The selected item wasn't set correctly",
				selectedItem,
				model.getItem()
			);

			assertEquals(
				"The selected item wasn't set correctly",
				selectedItem.displayString(),
				comboSelectedItem()
			);
		}
	}

	private void testSelectedItemChanged(SimpleDisplayable selectedItem,
	                                     SelectionListener selectionListener) {

		// Test 1
		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());
		assertTrue("The SelectionListener was supposed to be notified", selectionListener.isListenerNotified());
		assertSame("The SelectionListener was supposed to be notified", selectedItem, selectionListener.getItem());
		testSelectedItem(selectedItem);

		// Test 2
		selectedItem = model.itemAt(1);
		model.clearItemCalledFlag();
		selectionListener.clearInfo();

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());
		assertTrue("The SelectionListener was supposed to be notified", selectionListener.isListenerNotified());
		assertSame("The SelectionListener was supposed to be notified", selectedItem, selectionListener.getItem());
		testSelectedItem(selectedItem);

		// Test 3
		selectedItem = null;
		model.clearItemCalledFlag();
		selectionListener.clearInfo();

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertNull("The selected item wasn't set properly", model.getItem());
		assertTrue("The SelectionListener was supposed to be notified", selectionListener.isListenerNotified());
		assertSame("The SelectionListener was supposed to be notified", selectedItem, selectionListener.getItem());
		testSelectedItem(selectedItem);

		// Test 3
		selectedItem = model.itemAt(2);
		model.clearItemCalledFlag();
		selectionListener.clearInfo();

		model.setItem(selectedItem);
		assertTrue("The selected item was supposed to be modified", model.isSetItemCalled());
		assertSame("The selected item wasn't set properly", selectedItem, model.getItem());
		assertTrue("The SelectionListener was supposed to be notified", selectionListener.isListenerNotified());
		assertSame("The SelectionListener was supposed to be notified", selectedItem, selectionListener.getItem());
		testSelectedItem(selectedItem);
	}

	private static class Model extends AbstractModel {

		private SimpleDisplayable item;
		private List<SimpleDisplayable> items;
		private boolean setItemCalled;

		static final String ITEM_PROPERTY = "item";
		static final String ITEMS_LIST = "items";

		void addItems(Iterator<SimpleDisplayable> items) {
			addItemsToList(items, this.items, ITEMS_LIST);
		}

		void addItems(List<SimpleDisplayable> items) {
			addItemsToList(items, this.items, ITEMS_LIST);
		}

		void clearItemCalledFlag() {
			setItemCalled = false;
		}

		SimpleDisplayable getItem() {
			return item;
		}

		@Override
		protected void initialize() {
			super.initialize();
			items = new ArrayList<SimpleDisplayable>();
		}

		boolean isSetItemCalled() {
			return setItemCalled;
		}

		SimpleDisplayable itemAt(int index) {
			return this.items.get(index);
		}

		ListIterator<SimpleDisplayable> items() {
			return items.listIterator();
		}

		int itemsSize() {
			return items.size();
		}

		void removeItems(Iterator<SimpleDisplayable> items) {
			removeItemsFromList(items, this.items, ITEMS_LIST);
		}

		void setItem(SimpleDisplayable item) {
			setItemCalled = true;

			SimpleDisplayable oldItem = this.item;
			this.item = item;
			firePropertyChanged(ITEM_PROPERTY, oldItem, item);
		}
	}

	private class SelectionListener implements SelectionChangeListener<SimpleDisplayable> {

		private SimpleDisplayable item;
		private boolean listenerNotified;

		void clearInfo() {
			this.listenerNotified = false;
			this.item = null;
		}

		SimpleDisplayable getItem() {
			return item;
		}

		public boolean isListenerNotified() {
			return listenerNotified;
		}

		public void selectionChanged(SelectionChangeEvent<SimpleDisplayable> e) {
			listenerNotified = true;
			item = e.selectedItem();
		}
	}
}
