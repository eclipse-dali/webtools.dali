/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;

/**
 * This extension of AspectAdapter provides ListChange support
 * by adapting a subject's state change events to a minimum set
 * of list change events.
 * 
 * The typical subclass will override the following methods:
 * #getValueForRecord()
 * 	at the very minimum, override this method to return an iterator
 * 	on the subject's collection state; it does not need to be overridden if
 * 	#value() is overridden and its behavior changed
 */
public abstract class ListCurator 
	extends AspectAdapter
	implements ListValueModel
{
	/** How the list looked before the last state change */
	private final ArrayList record;

	/** A listener that listens for the subject's state to change */
	private final StateChangeListener stateChangeListener;


	// ********** constructors **********

	/**
	 * Construct a Curator for the specified subject.
	 */
	protected ListCurator(Model subject) {
		this(new ReadOnlyPropertyValueModel(subject));
	}

	/**
	 * Construct a curator for the specified subject holder.
	 * The subject holder cannot be null.
	 */
	protected ListCurator(ValueModel subjectHolder) {
		super(subjectHolder);
		this.record = new ArrayList();
		this.stateChangeListener = this.buildStateChangeListener();
	}


	// ********** initialization **********

	/**
	 * The subject's state has changed, do inventory and report to listeners.
	 */
	protected StateChangeListener buildStateChangeListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent e) {
				ListCurator.this.submitInventoryReport();
			}
			@Override
			public String toString() {
				return "state change listener";
			}
		};
	}


	// ********** ValueModel implementation **********

	@Override
	public Object value() {
		return new ReadOnlyListIterator(this.record);
	}


	// ********** ListValueModel implementation **********

	/**
	 * Return the item at the specified index of the subject's list aspect.
	 */
	public Object getItem(int index) {
		return this.record.get(index);
	}

	/**
	 * Return the size of the subject's list aspect.
	 */
	public int size() {
		return this.record.size();
	}

	/**
	 * Unsupported in this implementation
	 */
	public void addItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unsupported in this implementation
	 */
	public void addItems(int index, List items) {
		for (int i = 0; i < items.size(); i++) {
			this.addItem(index + i, items.get(i));
		}
	}

	/**
	 * Unsupported in this implementation
	 */
	public Object removeItem(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unsupported in this implementation
	 */
	public List removeItems(int index, int length) {
		List removedItems = new ArrayList(length);
		for (int i = 0; i < length; i++) {
			removedItems.add(this.removeItem(index));
		}
		return removedItems;
	}

	/**
	 * Unsupported in this implementation
	 */
	public Object replaceItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unsupported in this implementation
	 */
	public List replaceItems(int index, List items) {
		List replacedItems = new ArrayList(items.size());
		for (int i = 0; i < items.size(); i++) {
			replacedItems.add(this.replaceItem(index + i, items.get(i)));
		}
		return replacedItems;
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Class<? extends ChangeListener> listenerClass() {
		return ListChangeListener.class;
	}

	@Override
	protected String listenerAspectName() {
		return VALUE;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(VALUE);
	}

	/**
	 * The aspect has changed, notify listeners appropriately.
	 */
	@Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireListChanged(VALUE);
	}

	/**
	 * The subject is not null - add our listener.
	 */
	@Override
	protected void engageNonNullSubject() {
		((Model) this.subject).addStateChangeListener(this.stateChangeListener);
		// synch our list *after* we start listening to the subject,
		// since its value might change when a listener is added
		CollectionTools.addAll(this.record, this.getValueForRecord());
	}

	/**
	 * The subject is not null - remove our listener.
	 */
	@Override
	protected void disengageNonNullSubject() {
		((Model) this.subject).removeStateChangeListener(this.stateChangeListener);
		// clear out the list when we are not listening to the subject
		this.record.clear();
	}


	// ********** ListCurator contract **********

	/**
	 * This is intended to be different from #ValueModel.getValue().
	 * It is intended to be used only when the subject changes or the subject's state changes.
	 */
	protected abstract Iterator getValueForRecord();


	// ********** behavior **********

	void submitInventoryReport() {
		List newRecord = CollectionTools.list(this.getValueForRecord());
		int recordIndex = 0;

		// add items from the new record
		for (Iterator newItems = newRecord.iterator(); newItems.hasNext(); ) {
			this.inventoryNewItem(recordIndex, newItems.next());
			recordIndex ++;
		}

		// clean out items that are no longer in the new record
		for (recordIndex = 0; recordIndex < this.record.size(); ) {
			Object item = this.record.get(recordIndex);

			if (! newRecord.contains(item)) {
				this.removeItemFromInventory(recordIndex, item);
			}
			else {
				recordIndex ++;
			}
		}
	}

	private void inventoryNewItem(int recordIndex, Object newItem) {
		List rec = new ArrayList(this.record);

		if (recordIndex < rec.size() && rec.get(recordIndex).equals(newItem)) {
			return;
		}
		else if (! rec.contains(newItem)) {
			this.addItemToInventory(recordIndex, newItem);
		}
		else {
			this.removeItemFromInventory(recordIndex, rec.get(recordIndex));
			this.inventoryNewItem(recordIndex, newItem);
		}
	}

	private void addItemToInventory(int index, Object item) {
		this.addItemToList(index, item, this.record, VALUE);
	}

	private void removeItemFromInventory(int index, Object item) {
		this.removeItemFromList(index, this.record, VALUE);
	}

}
