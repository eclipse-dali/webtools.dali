/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * An adapter that allows us to transform a {@link ListValueModel}
 * (or adapted {@link CollectionValueModel}) into a read-only {@link ListValueModel}
 * whose items are tranformations of the items in the wrapped
 * {@link ListValueModel}. It will keep its contents in synch with
 * the contents of the wrapped {@link ListValueModel} and notifies its
 * listeners of any changes.
 * <p>
 * The {@link Transformer} can be changed at any time; allowing the same
 * adapter to be used with different transformations.
 * <p>
 * As an alternative to building a {@link Transformer},
 * a subclass of <code>TransformationListValueModelAdapter</code> can
 * either override {@link #transformItem_(Object)} or,
 * if something other than null should be returned when the wrapped item
 * is null, override {@link #transformItem(Object)}.
 * <p>
 * <strong>NB:</strong> Since we only listen to the wrapped list when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * list while we are listening to it, results to various methods
 * (e.g. {@link #size()}, {@link #get(int)}) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, clients will also be listeners.
 * 
 * @see Transformer
 */
public class TransformationListValueModel<E1, E2>
	extends ListValueModelWrapper<E1>
	implements ListValueModel<E2>
{

	/** This transforms the items, unless the subclass overrides {@link #transformItem(Object)}). */
	protected Transformer<E1, E2> transformer;

	/** The list of transformed items. */
	protected final List<E2> transformedList;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified nested
	 * list value model and transformer.
	 */
	public TransformationListValueModel(ListValueModel<? extends E1> listHolder, Transformer<E1, E2> transformer) {
		super(listHolder);
		this.transformer = transformer;
		this.transformedList = new ArrayList<E2>();
	}

	/**
	 * Construct a list value model with the specified nested
	 * list value model and the default transformer.
	 * Use this constructor if you want to override
	 * {@link #transformItem_(Object)} or {@link #transformItem(Object)}
	 * method instead of building a {@link Transformer}.
	 */
	public TransformationListValueModel(ListValueModel<? extends E1> listHolder) {
		super(listHolder);
		this.transformer = this.buildTransformer();
		this.transformedList = new ArrayList<E2>();
	}

	/**
	 * Construct a list value model with the specified nested
	 * collection value model and transformer.
	 */
	public TransformationListValueModel(CollectionValueModel<? extends E1> collectionHolder, Transformer<E1, E2> transformer) {
		this(new CollectionListValueModelAdapter<E1>(collectionHolder), transformer);
	}

	/**
	 * Construct a list value model with the specified nested
	 * collection value model and the default transformer.
	 * Use this constructor if you want to override
	 * {@link #transformItem_(Object)} or {@link #transformItem(Object)}
	 * method instead of building a {@link Transformer}.
	 */
	public TransformationListValueModel(CollectionValueModel<? extends E1> collectionHolder) {
		this(new CollectionListValueModelAdapter<E1>(collectionHolder));
	}

	protected Transformer<E1, E2> buildTransformer() {
		return new DefaultTransformer();
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
	 * Transform the items in the specified list value model.
	 */
	protected List<E2> transformItems(ListValueModel<? extends E1> lvm) {
		return this.transformItems(lvm, lvm.size());
	}

	/**
	 * Transform the items associated with the specified event.
	 */
	protected List<E2> transformItems(ListAddEvent event) {
		return this.transformItems(this.getItems(event), event.getItemsSize());
	}

	/**
	 * Transform the items associated with the specified event.
	 */
	protected List<E2> transformItems(ListRemoveEvent event) {
		return this.transformItems(this.getItems(event), event.getItemsSize());
	}

	/**
	 * Transform the new items associated with the specified event.
	 */
	protected List<E2> transformNewItems(ListReplaceEvent event) {
		return this.transformItems(this.getNewItems(event), event.getItemsSize());
	}

	/**
	 * Transform the old items associated with the specified event.
	 */
	protected List<E2> transformOldItems(ListReplaceEvent event) {
		return this.transformItems(this.getOldItems(event), event.getItemsSize());
	}

	/**
	 * Transform the specified items.
	 */
	protected List<E2> transformItems(Iterable<? extends E1> items, int size) {
		List<E2> result = new ArrayList<E2>(size);
		for (E1 item : items) {
			result.add(this.transformItem(item));
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
	 * Transform the specified, non-null, item and return the result.
	 */
	protected E2 transformItem_(@SuppressWarnings("unused") E1 item) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
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
		this.synchronizeList(this.transformItems(this.listHolder), this.transformedList, LIST_VALUES);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.transformedList);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder.
	 * Transform them, add them to our transformation list,
	 * and notify our listeners.
	 */
    @Override
	protected void itemsAdded(ListAddEvent event) {
		this.addItemsToList(event.getIndex(), this.transformItems(event), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were removed from the wrapped list holder.
	 * Remove the corresponding items from our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsRemoved(ListRemoveEvent event) {
		this.removeItemsFromList(event.getIndex(), event.getItemsSize(), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were replaced in the wrapped list holder.
	 * Replace the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsReplaced(ListReplaceEvent event) {
		this.setItemsInList(event.getIndex(), this.transformNewItems(event), this.transformedList, LIST_VALUES);
	}

	/**
	 * Items were moved in the wrapped list holder.
	 * Move the corresponding items in our transformation list
	 * and notify our listeners.
	 */
    @Override
	protected void itemsMoved(ListMoveEvent event) {
    	this.moveItemsInList(event.getTargetIndex(), event.getSourceIndex(), event.getLength(), this.transformedList, LIST_VALUES);
	}

	/**
	 * The wrapped list holder was cleared.
	 * Clear our transformation list and notify our listeners.
	 */
    @Override
	protected void listCleared(ListClearEvent event) {
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


	// ********** default transformer **********

	/**
	 * The default transformer will return null if the wrapped item is null.
	 * If the wrapped item is not null, it is transformed by a subclass
	 * implementation of {@link TransformationListValueModel#transformItem_(Object)}.
	 */
	protected class DefaultTransformer implements Transformer<E1, E2> {
		public E2 transform(E1 item) {
			return (item == null) ? null : TransformationListValueModel.this.transformItem_(item);
		}
	}

}
