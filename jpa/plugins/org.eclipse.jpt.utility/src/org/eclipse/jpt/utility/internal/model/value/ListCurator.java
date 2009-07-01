/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * This extension of AspectAdapter provides ListChange support
 * by adapting a subject's state change events to a minimum set
 * of list change events.
 */
public abstract class ListCurator<S extends Model, E>
	extends AspectAdapter<S>
	implements ListValueModel<E>
{
	/** How the list looked before the last state change */
	private final ArrayList<E> record;

	/** A listener that listens for the subject's state to change */
	private final StateChangeListener stateChangeListener;


	// ********** constructors **********

	/**
	 * Construct a Curator for the specified subject.
	 */
	protected ListCurator(S subject) {
		this(new StaticPropertyValueModel<S>(subject));
	}

	/**
	 * Construct a curator for the specified subject holder.
	 * The subject holder cannot be null.
	 */
	protected ListCurator(PropertyValueModel<? extends S> subjectHolder) {
		super(subjectHolder);
		this.record = new ArrayList<E>();
		this.stateChangeListener = this.buildStateChangeListener();
	}


	// ********** initialization **********

	/**
	 * The subject's state has changed, do inventory and report to listeners.
	 */
	protected StateChangeListener buildStateChangeListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				ListCurator.this.submitInventoryReport();
			}
			@Override
			public String toString() {
				return "state change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ListValueModel implementation **********

	public ListIterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.record);
	}

	/**
	 * Return the item at the specified index of the subject's list aspect.
	 */
	public E get(int index) {
		return this.record.get(index);
	}

	/**
	 * Return the size of the subject's list aspect.
	 */
	public int size() {
		return this.record.size();
	}

	/**
	 * Return an array manifestation of the subject's list aspect.
	 */
	public Object[] toArray() {
		return this.record.toArray();
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected ListIterator<E> getValue() {
		return this.iterator();
	}

	@Override
	protected Class<? extends ChangeListener> getListenerClass() {
		return ListChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return LIST_VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(LIST_VALUES);
	}

	/**
	 * The aspect has changed, notify listeners appropriately.
	 */
	@Override
	protected void fireAspectChanged(Object oldValue, Object newValue) {
		this.fireListChanged(LIST_VALUES, this.record);
	}

	/**
	 * The subject is not null - add our listener.
	 */
	@Override
	protected void engageSubject_() {
		((Model) this.subject).addStateChangeListener(this.stateChangeListener);
		// synch our list *after* we start listening to the subject,
		// since its value might change when a listener is added
		CollectionTools.addAll(this.record, this.iteratorForRecord());
	}

	/**
	 * The subject is not null - remove our listener.
	 */
	@Override
	protected void disengageSubject_() {
		((Model) this.subject).removeStateChangeListener(this.stateChangeListener);
		// clear out the list when we are not listening to the subject
		this.record.clear();
	}


	// ********** ListCurator protocol **********

	/**
	 * This is intended to be different from #ListValueModel.iterator().
	 * It is intended to be used only when the subject changes or the
	 * subject's "state" changes (as signified by a state change event).
	 */
	protected abstract Iterator<E> iteratorForRecord();


	// ********** behavior **********

	void submitInventoryReport() {
		List<E> newRecord = CollectionTools.list(this.iteratorForRecord());
		int recordIndex = 0;

		// add items from the new record
		for (E newItem : newRecord) {
			this.inventoryNewItem(recordIndex, newItem);
			recordIndex++;
		}

		// clean out items that are no longer in the new record
		for (recordIndex = 0; recordIndex < this.record.size(); ) {
			E item = this.record.get(recordIndex);

			if (newRecord.contains(item)) {
				recordIndex++;
			} else {
				this.removeItemFromInventory(recordIndex, item);
			}
		}
	}

	private void inventoryNewItem(int recordIndex, E newItem) {
		List<E> rec = new ArrayList<E>(this.record);

		if ((recordIndex < rec.size()) && rec.get(recordIndex).equals(newItem)) {
			return;
		}
		if (rec.contains(newItem)) {
			this.removeItemFromInventory(recordIndex, rec.get(recordIndex));
			this.inventoryNewItem(recordIndex, newItem);
		} else {
			this.addItemToInventory(recordIndex, newItem);
		}
	}

	private void addItemToInventory(int index, E item) {
		this.addItemToList(index, item, this.record, LIST_VALUES);
	}

	private void removeItemFromInventory(int index, @SuppressWarnings("unused") E item) {
		this.removeItemFromList(index, this.record, LIST_VALUES);
	}

}
