/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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

import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * An adapter that allows us to transform a ListValueModel
 * (or CollectionValueModel) into a read-only ListValueModel
 * whose items are tranformations of the items in the wrapped
 * ListValueModel. It will keep its contents in synch with
 * the contents of the wrapped ListValueModel and notifies its
 * listeners of any changes.
 * <p>
 * The transformer can be changed at any time; allowing the same
 * adapter to be used with different transformations.
 * <p>
 * NB: Since we only listen to the wrapped list when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * list while we are listening to it, results to various methods
 * (e.g. #size(), #getItem(int)) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class TransformationListValueModelAdapter<E1, E2>
	extends ListValueModelWrapper<E1>
	implements ListValueModel<E2>
{

	/** This transforms the items, unless the subclass overrides #transformItem(Object). */
	protected Transformer<E1, E2> transformer;

	/** The list of transformed items. */
	protected final List<E2> transformedList;


	// ********** constructors **********

	/**
	 * Constructor - the list holder is required.
	 */
	public TransformationListValueModelAdapter(ListValueModel<? extends E1> listHolder, Transformer<E1, E2> transformer) {
		super(listHolder);
		this.transformer = transformer;
		this.transformedList = new ArrayList<E2>();
	}

	/**
	 * Constructor - the list holder is required.
	 */
	public TransformationListValueModelAdapter(ListValueModel<? extends E1> listHolder) {
		this(listHolder, Transformer.Null.<E1, E2>instance());
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public TransformationListValueModelAdapter(CollectionValueModel<? extends E1> collectionHolder, Transformer<E1, E2> transformer) {
		this(new CollectionListValueModelAdapter<E1>(collectionHolder), transformer);
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	public TransformationListValueModelAdapter(CollectionValueModel<? extends E1> collectionHolder) {
		this(new CollectionListValueModelAdapter<E1>(collectionHolder));
	}


	// ********** ListValueModel implementation **********

	public Iterator<E2> iterator() {
		return this.listIterator();
	}

	public ListIterator<E2> listIterator() {
		return new ReadOnlyListIterator<E2>(this.transformedList);
	}

	public E2 get(int index) {
		return this.transformedList.get(index);
	}

	public int size() {
		return this.transformedList.size();
	}

	public Object[] toArray() {
		return this.transformedList.toArray();
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
	protected List<E2> transformItems(ListChangeEvent event) {
		return this.transformItems(this.items(event), event.itemsSize());
	}

	/**
	 * Transform the items in the specified list value model.
	 */
	protected List<E2> transformItems(ListValueModel<? extends E1> lvm) {
		return this.transformItems(lvm.listIterator(), lvm.size());
	}

	/**
	 * Transform the replaced items associated with the specified event.
	 */
	protected List<E2> transformReplacedItems(ListChangeEvent event) {
		return this.transformItems(this.replacedItems(event), event.itemsSize());
	}

	/**
	 * Transform the specified items.
	 */
	protected List<E2> transformItems(ListIterator<? extends E1> items, int size) {
		List<E2> result = new ArrayList<E2>(size);
		while (items.hasNext()) {
			result.add(this.transformItem(items.next()));
		}
		return result;
	}

	/**
	 * Transform the specified item.
	 */
	protected E2 transformItem(E1 item) {
		return this.transformer.transform(item);
	}

	/**
	 * Change the transformer and rebuild the collection.
	 */
	public void setTransformer(Transformer<E1, E2> transformer) {
		this.transformer = transformer;
		this.rebuildTransformedList();
	}

	/**
	 * Synchronize our cache with the wrapped collection.
	 */
	protected void rebuildTransformedList() {
		this.transformedList.clear();
		this.transformedList.addAll(this.transformItems(this.listHolder));
		this.fireListChanged(LIST_VALUES);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder.
	 * Transform them, add them to our transformation list,
	 * and notify our listeners.
	 */
    @Override
	protected void itemsAdded(ListChangeEvent event) {
		this.addItemsToList(event.getIndex(), this.transformItems(event), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were removed from the wrapped list holder.
	 * Remove the corresponding items from our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsRemoved(ListChangeEvent event) {
		this.removeItemsFromList(event.getIndex(), event.itemsSize(), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were replaced in the wrapped list holder.
	 * Replace the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsReplaced(ListChangeEvent event) {
		this.setItemsInList(event.getIndex(), this.transformItems(event), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were moved in the wrapped list holder.
	 * Move the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsMoved(ListChangeEvent event) {
    	this.moveItemsInList(event.getTargetIndex(), event.getSourceIndex(), event.getMoveLength(), this.transformedList, LIST_VALUES);
	}

	/**
	 * The wrapped list holder was cleared.
	 * Clear our transformation list and notify our listeners.
	 */
    @Override
	protected void listCleared(ListChangeEvent event) {
    	this.clearList(this.transformedList, LIST_VALUES);
	}

	/**
	 * The wrapped list holder has changed in some dramatic fashion.
	 * Rebuild our transformation list and notify our listeners.
	 */
    @Override
	protected void listChanged(ListChangeEvent event) {
		this.rebuildTransformedList();
	}

}
