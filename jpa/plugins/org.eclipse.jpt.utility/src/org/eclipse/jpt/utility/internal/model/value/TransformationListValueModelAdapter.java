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
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;

/**
 * An adapter that allows us to transform a ListValueModel
 * (or CollectionValueModel) into a read-only ListValueModel
 * whose items are tranformations of the items in the wrapped
 * ListValueModel. It will keep its contents in synch with
 * the contents of the wrapped ListValueModel and notifies its
 * listeners of any changes.
 * 
 * To use, supply a <code>Transformer</code> or subclass
 * <code>TransformationListValueModelAdapter</code>
 * and override the <code>transformItem(Object)</code> method.
 * 
 * NB: Since we only listen to the wrapped list when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * list while we are listening to it, results to various methods
 * (e.g. #size(), getItem(int)) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class TransformationListValueModelAdapter extends ListValueModelWrapper {

	/** This transforms the items, unless the subclass overrides #transformItem(Object). */
	protected final Transformer transformer;

	/** The list of transformed items. */
	protected List transformedList;


	// ********** constructors **********

	/**
	 * Constructor - the list holder is required.
	 */
	public TransformationListValueModelAdapter(ListValueModel listHolder, Transformer transformer) {
		super(listHolder);
		this.transformer = transformer;
	}

	/**
	 * Constructor - the list holder is required.
	 */
	public TransformationListValueModelAdapter(ListValueModel listHolder) {
		this(listHolder, Transformer.Disabled.instance());
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public TransformationListValueModelAdapter(CollectionValueModel collectionHolder, Transformer transformer) {
		this(new CollectionListValueModelAdapter(collectionHolder), transformer);
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public TransformationListValueModelAdapter(CollectionValueModel collectionHolder) {
		this(collectionHolder, Transformer.Disabled.instance());
	}


	// ********** initialization **********

	@Override
	protected void initialize() {
		super.initialize();
		this.transformedList = new ArrayList();
	}


	// ********** ValueModel implementation **********

	public Object getValue() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyListIterator(this.transformedList);
	}


	// ********** ListValueModel implementation **********

	public void addItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public void addItems(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object removeItem(int index) {
		throw new UnsupportedOperationException();
	}

	public List removeItems(int index, int length) {
		throw new UnsupportedOperationException();
	}

	public Object replaceItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public List replaceItems(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object getItem(int index) {
		return this.transformedList.get(index);
	}

	public int size() {
		return this.transformedList.size();
	}


	// ********** behavior **********

    @Override
	protected void engageModel() {
		super.engageModel();
		// synch the transformed list *after* we start listening to the list holder,
		// since its value might change when a listener is added
		this.transformedList.addAll(this.transformItems(this.listHolder));
	}

    @Override
	protected void disengageModel() {
		super.disengageModel();
		// clear out the list when we are not listening to the collection holder
		this.transformedList.clear();
	}

	/**
	 * Transform the items associated with the specified event.
	 */
	protected List transformItems(ListChangeEvent e) {
		return this.transformItems(e.items(), e.itemsSize());
	}

	/**
	 * Transform the items in the specified list value model.
	 */
	protected List transformItems(ListValueModel lvm) {
		return this.transformItems((ListIterator) lvm.getValue(), lvm.size());
	}

	/**
	 * Transform the replaced items associated with the specified event.
	 */
	protected List transformReplacedItems(ListChangeEvent e) {
		return this.transformItems(e.replacedItems(), e.itemsSize());
	}

	/**
	 * Transform the specified items.
	 */
	protected List transformItems(ListIterator items, int size) {
		List result = new ArrayList(size);
		while (items.hasNext()) {
			result.add(this.transformItem(items.next()));
		}
		return result;
	}

	/**
	 * Transform the specified item.
	 */
	protected Object transformItem(Object item) {
		return this.transformer.transform(item);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder.
	 * Transform them, add them to our transformation list,
	 * and notify our listeners.
	 */
    @Override
	protected void itemsAdded(ListChangeEvent e) {
		this.addItemsToList(e.index(), this.transformItems(e), this.transformedList, VALUE);
	}

	/**
	 * Items were removed from the wrapped list holder.
	 * Remove the corresponding items from our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsRemoved(ListChangeEvent e) {
		this.removeItemsFromList(e.index(), e.itemsSize(), this.transformedList, VALUE);
	}

	/**
	 * Items were replaced in the wrapped list holder.
	 * Replace the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsReplaced(ListChangeEvent e) {
		this.setItemsInList(e.index(), this.transformItems(e), this.transformedList, VALUE);
	}

	/**
	 * Items were moved in the wrapped list holder.
	 * Move the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsMoved(ListChangeEvent e) {
    	this.moveItemsInList(e.targetIndex(), e.sourceIndex(), e.moveLength(), this.transformedList, VALUE);
	}

	/**
	 * The wrapped list holder was cleared.
	 * Clear our transformation list and notify our listeners.
	 */
    @Override
	protected void listCleared(ListChangeEvent e) {
    	this.clearList(this.transformedList, VALUE);
	}

	/**
	 * The wrapped list holder has changed in some dramatic fashion.
	 * Rebuild our transformation list and notify our listeners.
	 */
    @Override
	protected void listChanged(ListChangeEvent e) {
		this.transformedList.clear();
		this.transformedList.addAll(this.transformItems(this.listHolder));
		this.fireListChanged(VALUE);
	}

}
