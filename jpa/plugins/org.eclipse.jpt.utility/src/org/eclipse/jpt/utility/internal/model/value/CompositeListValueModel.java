/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * A <code>CompositeListValueModel</code> wraps another
 * {@link ListValueModel} and uses a {@link Transformer}
 * to convert each item in the wrapped list to yet another
 * {@link ListValueModel}. This composite list contains
 * the combined items from all these component lists.
 * <p>
 * Terminology:<ul>
 * <li><em>sources</em> - the items in the wrapped list value model; these
 *    are converted into component LVMs by the transformer
 * <li><em>component LVMs</em> - the component list value models that are combined
 *    by this composite list value model
 * <li><em>items</em> - the items held by the component LVMs
 * </ul>
 */
public class CompositeListValueModel<E1, E2>
	extends ListValueModelWrapper<E1>
	implements ListValueModel<E2>
{
	/**
	 * This is the (optional) user-supplied object that transforms
	 * the items in the wrapped list to list value models.
	 */
	private final Transformer<E1, ListValueModel<E2>> transformer;

	/**
	 * Cache of the sources, component LVMs, lists.
	 */
	private final ArrayList<Info> infoList = new ArrayList<Info>();
	protected class Info {
		// the object passed to the transformer
		final E1 source;
		// the list value model generated by the transformer
		final ListValueModel<E2> componentLVM;
		// cache of the items held by the component LVM
		final ArrayList<E2> items;
		// the component LVM's beginning index within the composite LVM
		int begin;
		Info(E1 source, ListValueModel<E2> componentLVM, ArrayList<E2> items, int begin) {
			super();
			this.source = source;
			this.componentLVM = componentLVM;
			this.items = items;
			this.begin = begin;
		}
	}

	/** Listener that listens to all the component list value models. */
	private final ListChangeListener componentLVMListener;

	/** Cache the size of the composite list. */
	private int size;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified wrapped
	 * list value model. Use this constructor if<ul>
	 * <li> the wrapped list value model already contains other
	 *       list value models, or
	 * <li> you want to override {@link #transform(E1)}
	 *       instead of building a {@link Transformer}
	 * </ul>
	 */
	public CompositeListValueModel(ListValueModel<? extends E1> listHolder) {
		this(listHolder, Transformer.Null.<E1, ListValueModel<E2>>instance());
	}

	/**
	 * Construct a list value model with the specified wrapped
	 * list value model and transformer.
	 */
	public CompositeListValueModel(ListValueModel<? extends E1> listHolder, Transformer<E1, ListValueModel<E2>> transformer) {
		super(listHolder);
		this.transformer = transformer;
		this.componentLVMListener = this.buildComponentLVMListener();
		this.size = 0;
	}

	/**
	 * Construct a list value model with the specified, unchanging, wrapped
	 * list. Use this constructor if<ul>
	 * <li> the wrapped list value model already contains other
	 *       list value models, or
	 * <li> you want to override {@link #transform(E1)}
	 *       instead of building a {@link Transformer}
	 * </ul>
	 */
	public CompositeListValueModel(List<? extends E1> list) {
		this(new StaticListValueModel<E1>(list));
	}

	/**
	 * Construct a list value model with the specified, unchanging, wrapped
	 * list and transformer.
	 */
	public CompositeListValueModel(List<? extends E1> list, Transformer<E1, ListValueModel<E2>> transformer) {
		this(new StaticListValueModel<E1>(list), transformer);
	}

	/**
	 * Construct a list value model with the specified, unchanging, wrapped
	 * list. Use this constructor if<ul>
	 * <li> the wrapped list value model already contains other
	 *       list value models, or
	 * <li> you want to override {@link #transform(E1)}
	 *       instead of building a {@link Transformer}
	 * </ul>
	 */
	public CompositeListValueModel(E1... list) {
		this(new StaticListValueModel<E1>(list));
	}

	/**
	 * Construct a list value model with the specified, unchanging, wrapped
	 * list and transformer.
	 */
	public CompositeListValueModel(E1[] list, Transformer<E1, ListValueModel<E2>> transformer) {
		this(new StaticListValueModel<E1>(list), transformer);
	}


	// ********** initialization **********

	protected ListChangeListener buildComponentLVMListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				CompositeListValueModel.this.componentItemsAdded(event);
			}		
			public void itemsRemoved(ListRemoveEvent event) {
				CompositeListValueModel.this.componentItemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				CompositeListValueModel.this.componentItemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				CompositeListValueModel.this.componentItemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				CompositeListValueModel.this.componentListCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				CompositeListValueModel.this.componentListChanged(event);
			}
			@Override
			public String toString() {
				return "component LVM listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ListValueModel implementation **********

	public E2 get(int index) {
		if ((index < 0) || (index >= this.size)) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// move backwards through the info list
		for (int i = this.infoList.size(); i-- > 0; ) {
			Info info = this.infoList.get(i);
			if (index >= info.begin) {
				return info.items.get(index - info.begin);
			}
		}
		throw new IllegalStateException();  // something is wack
	}

	public Iterator<E2> iterator() {
		return this.listIterator();
	}

	public ListIterator<E2> listIterator() {
		return new ReadOnlyCompositeListIterator<E2>(this.buildListsIterators());
	}

	protected ListIterator<ListIterator<E2>> buildListsIterators() {
		return new TransformationListIterator<Info, ListIterator<E2>>(this.infoList.listIterator()) {
			@Override
			protected ListIterator<E2> transform(Info info) {
				return info.items.listIterator();
			}
		};
	}

	public int size() {
		return this.size;
	}

	public Object[] toArray() {
		return CollectionTools.array(this.listIterator(), this.size);
	}


	// ********** ListValueModelWrapper overrides/implementation **********

	@Override
	protected void engageModel() {
		super.engageModel();
		// synch our cache *after* we start listening to the wrapped list,
		// since its value might change when a listener is added
		this.addComponentSources(0, this.listHolder, this.listHolder.size());
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// stop listening to the component LVMs...
		for (Info info : this.infoList) {
			info.componentLVM.removeListChangeListener(LIST_VALUES, this.componentLVMListener);
		}
		// ...and clear the cache
		this.infoList.clear();
		this.size = 0;
	}

	/**
	 * Some component sources were added; update our cache.
	 */
	@Override
	protected void itemsAdded(ListAddEvent event) {
		this.addComponentSources(event.getIndex(), this.getItems(event), event.getItemsSize(), true);  // true = fire event
	}

	/**
	 * Do not fire an event.
	 */
	protected void addComponentSources(int addedSourcesIndex, Iterable<? extends E1> addedSources, int addedSourcesSize) {
		this.addComponentSources(addedSourcesIndex, addedSources, addedSourcesSize, false);  // false = do not fire event
	}

	/**
	 * Add infos corresponding to the specified sources to our cache.
	 * Fire the appropriate event if requested.
	 */
	protected void addComponentSources(int addedSourcesIndex, Iterable<? extends E1> addedSources, int addedSourcesSize, boolean fireEvent) {
		ArrayList<Info> newInfoList = new ArrayList<Info>(addedSourcesSize);
		// the 'items' are either tacked on to the end or
		// at the 'begin' index of the first 'info' that is being pushed back
		int newItemsIndex = (addedSourcesIndex == this.infoList.size()) ? this.size : this.infoList.get(addedSourcesIndex).begin;

		int begin = newItemsIndex;
		for (E1 source : addedSources) {
			ListValueModel<E2> componentLVM = this.transform(source);
			componentLVM.addListChangeListener(LIST_VALUES, this.componentLVMListener);
			ArrayList<E2> items = new ArrayList<E2>(componentLVM.size());
			CollectionTools.addAll(items, componentLVM.listIterator());
			newInfoList.add(new Info(source, componentLVM, items, begin));
			begin += items.size();
		}
		this.infoList.addAll(addedSourcesIndex, newInfoList);
		int newItemsSize = begin - newItemsIndex;
		this.size += newItemsSize;

		// bump the 'begin' index for all the infos that were pushed back by the insert
		int movedInfosIndex = addedSourcesIndex + addedSourcesSize;
		for (int i = movedInfosIndex; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin += newItemsSize;
		}

		if (fireEvent) {
			ArrayList<E2> newItems = new ArrayList<E2>(newItemsSize);
			for (int i = addedSourcesIndex; i < movedInfosIndex; i++) {
				newItems.addAll(this.infoList.get(i).items);
			}
			this.fireItemsAdded(LIST_VALUES, newItemsIndex, newItems);
		}
	}

	/**
	 * Some component sources were removed; update our cache.
	 */
	@Override
	protected void itemsRemoved(ListRemoveEvent event) {
		this.removeComponentSources(event.getIndex(), event.getItemsSize(), true);  // true = fire event
	}

	/**
	 * Do not fire an event.
	 */
	protected void removeComponentSources(int removedSourcesIndex, int removedSourcesSize) {
		this.removeComponentSources(removedSourcesIndex, removedSourcesSize, false);  // false = do not fire event
	}

	/**
	 * Remove the infos corresponding to the specified sources from our cache.
	 */
	protected void removeComponentSources(int removedSourcesIndex, int removedSourcesSize, boolean fireEvent) {
		int removedItemsIndex = this.infoList.get(removedSourcesIndex).begin;
		int movedSourcesIndex = removedSourcesIndex + removedSourcesSize;
		int movedItemsIndex = (movedSourcesIndex == this.infoList.size()) ? this.size : this.infoList.get(movedSourcesIndex).begin;
		int removedItemsSize = movedItemsIndex - removedItemsIndex;
		this.size -= removedItemsSize;

		List<Info> subList = this.infoList.subList(removedSourcesIndex, removedSourcesIndex + removedSourcesSize);
		ArrayList<Info> removedInfoList = new ArrayList<Info>(subList);  // make a copy
		subList.clear();

		// decrement the 'begin' index for all the infos that were moved forward by the deletes
		for (int i = removedSourcesIndex; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin -= removedItemsSize;
		}

		for (Info removedInfo : removedInfoList) {
			removedInfo.componentLVM.removeListChangeListener(LIST_VALUES, this.componentLVMListener);
		}

		if (fireEvent) {
			ArrayList<E2> removedItems = new ArrayList<E2>(removedItemsSize);
			for (Info removedInfo : removedInfoList) {
				removedItems.addAll(removedInfo.items);
			}
			this.fireItemsRemoved(LIST_VALUES, removedItemsIndex, removedItems);
		}
	}

	/**
	 * Some component sources were replaced; update our cache.
	 */
	@Override
	protected void itemsReplaced(ListReplaceEvent event) {
		this.replaceComponentSources(event.getIndex(), this.getNewItems(event), event.getItemsSize(), true);  // true = fire event
	}

	/**
	 * Replaced component sources will not (typically) map to a set of replaced
	 * items, so we remove and add the corresponding lists of items, resulting in
	 * two events.
	 */
	protected void replaceComponentSources(int replacedSourcesIndex, Iterable<? extends E1> newSources, int replacedSourcesSize, boolean fireEvent) {
		this.removeComponentSources(replacedSourcesIndex, replacedSourcesSize, fireEvent);
		this.addComponentSources(replacedSourcesIndex, newSources, replacedSourcesSize, fireEvent);
	}

	/**
	 * Some component sources were moved; update our cache.
	 */
	@Override
	protected void itemsMoved(ListMoveEvent event) {
		this.moveComponentSources(event.getTargetIndex(), event.getSourceIndex(), event.getLength(), true);  // true = fire event
	}

	protected void moveComponentSources(int targetSourcesIndex, int sourceSourcesIndex, int movedSourcesLength, boolean fireEvent) {
		int sourceItemsIndex = this.infoList.get(sourceSourcesIndex).begin;

		int nextSourceSourceIndex = sourceSourcesIndex + movedSourcesLength;
		int nextSourceItemIndex = (nextSourceSourceIndex == this.infoList.size()) ? this.size : this.infoList.get(nextSourceSourceIndex).begin;
		int moveItemsLength = nextSourceItemIndex - sourceItemsIndex;

		int targetItemsIndex = -1;
		if (sourceSourcesIndex > targetSourcesIndex) {
			// move from high to low index
			targetItemsIndex = this.infoList.get(targetSourcesIndex).begin;
		} else {
			// move from low to high index (higher items move down during move)
			int nextTargetSourceIndex = targetSourcesIndex + movedSourcesLength;
			targetItemsIndex = (nextTargetSourceIndex == this.infoList.size()) ? this.size : this.infoList.get(nextTargetSourceIndex).begin;
			targetItemsIndex = targetItemsIndex - moveItemsLength;
		}

		CollectionTools.move(this.infoList, targetSourcesIndex, sourceSourcesIndex, movedSourcesLength);

		// update the 'begin' indexes of all the affected 'infos'
		int min = Math.min(targetSourcesIndex, sourceSourcesIndex);
		int max = Math.max(targetSourcesIndex, sourceSourcesIndex) + movedSourcesLength;
		int begin = Math.min(targetItemsIndex, sourceItemsIndex);
		for (int i = min; i < max; i++) {
			Info info = this.infoList.get(i);
			info.begin = begin;
			begin += info.componentLVM.size();
		}

		if (fireEvent) {
			this.fireItemsMoved(LIST_VALUES, targetItemsIndex, sourceItemsIndex, moveItemsLength);
		}
	}

	/**
	 * The component sources were cleared; clear our cache.
	 */
	@Override
	protected void listCleared(ListClearEvent event) {
		this.removeComponentSources(0, this.infoList.size());
		this.fireListCleared(LIST_VALUES);
	}

	/**
	 * The component sources changed; rebuild our cache.
	 */
	@Override
	protected void listChanged(ListChangeEvent event) {
		this.removeComponentSources(0, this.infoList.size());
		this.addComponentSources(0, this.listHolder, this.listHolder.size());
		this.fireListChanged(LIST_VALUES, CollectionTools.list(this.listIterator()));
	}

	@Override
	public void toString(StringBuilder sb) {
		StringTools.append(sb, this);
	}


	// ********** internal methods **********

	/**
	 * Transform the specified object into a list value model.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link Transformer}.
	 */
	protected ListValueModel<E2> transform(E1 value) {
		return this.transformer.transform(value);
	}

	/**
	 * Return the index of the specified component LVM.
	 */
	protected int indexOf(ListValueModel<E2> componentLVM) {
		for (int i = 0; i < this.infoList.size(); i++) {
			if (this.infoList.get(i).componentLVM == componentLVM) {
				return i;
			}
		}
		throw new IllegalArgumentException("invalid component LVM: " + componentLVM); //$NON-NLS-1$
	}

	/**
	 * Return the index of the specified event's component LVM.
	 */
	protected int indexFor(ListEvent event) {
		return this.indexOf(this.componentLVM(event));
	}

	/**
	 * Items were added to one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsAdded(ListAddEvent event) {
		// update the affected 'begin' indices
		int componentLVMIndex = this.indexFor(event);
		int newItemsSize = event.getItemsSize();
		for (int i = componentLVMIndex + 1; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin += newItemsSize;
		}
		this.size += newItemsSize;

		// synchronize the cached list
		Info info = this.infoList.get(componentLVMIndex);
		CollectionTools.addAll(info.items, event.getIndex(), this.getComponentItems(event), event.getItemsSize());

		// translate the event
		this.fireItemsAdded(event.clone(this, LIST_VALUES, info.begin));
	}

	/**
	 * Items were removed from one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsRemoved(ListRemoveEvent event) {
		// update the affected 'begin' indices
		int componentLVMIndex = this.indexFor(event);
		int removedItemsSize = event.getItemsSize();
		for (int i = componentLVMIndex + 1; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin -= removedItemsSize;
		}
		this.size -= removedItemsSize;

		// synchronize the cached list
		Info info = this.infoList.get(componentLVMIndex);
		int itemIndex = event.getIndex();
		info.items.subList(itemIndex, itemIndex + event.getItemsSize()).clear();

		// translate the event
		this.fireItemsRemoved(event.clone(this, LIST_VALUES, info.begin));
	}

	/**
	 * Items were replaced in one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsReplaced(ListReplaceEvent event) {
		// no changes to the 'begin' indices or size

		// synchronize the cached list
		int componentLVMIndex = this.indexFor(event);
		Info info = this.infoList.get(componentLVMIndex);
		int i = event.getIndex();
		for (E2 item : this.getComponentItems(event)) {
			info.items.set(i++, item);
		}

		// translate the event
		this.fireItemsReplaced(event.clone(this, LIST_VALUES, info.begin));
	}

	/**
	 * Items were moved in one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsMoved(ListMoveEvent event) {
		// no changes to the 'begin' indices or size

		// synchronize the cached list
		int componentLVMIndex = this.indexFor(event);
		Info info = this.infoList.get(componentLVMIndex);
		CollectionTools.move(info.items, event.getTargetIndex(), event.getSourceIndex(), event.getLength());

		// translate the event
		this.fireItemsMoved(event.clone(this, LIST_VALUES, info.begin));
	}

	/**
	 * One of the component lists was cleared;
	 * synchronize our cache.
	 */
	protected void componentListCleared(ListClearEvent event) {
		int componentLVMIndex = this.indexFor(event);
		this.clearComponentList(componentLVMIndex, this.infoList.get(componentLVMIndex));
	}

	protected void clearComponentList(int componentLVMIndex, Info info) {
		// update the affected 'begin' indices
		int removedItemsSize = info.items.size();
		for (int i = componentLVMIndex + 1; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin -= removedItemsSize;
		}
		this.size -= removedItemsSize;

		// synchronize the cached list
		ArrayList<E2> items = new ArrayList<E2>(info.items);
		info.items.clear();

		// translate the event
		this.fireItemsRemoved(LIST_VALUES, info.begin, items);
	}

	/**
	 * One of the component lists changed;
	 * synchronize our cache by clearing out the appropriate
	 * list and rebuilding it.
	 */
	protected void componentListChanged(ListChangeEvent event) {
		int componentLVMIndex = this.indexFor(event);
		Info info = this.infoList.get(componentLVMIndex);
		this.clearComponentList(componentLVMIndex, info);

		// update the affected 'begin' indices
		int newItemsSize = info.componentLVM.size();
		for (int i = componentLVMIndex + 1; i < this.infoList.size(); i++) {
			this.infoList.get(i).begin += newItemsSize;
		}
		this.size += newItemsSize;

		// synchronize the cached list
		CollectionTools.addAll(info.items, info.componentLVM.listIterator(), newItemsSize);

		// translate the event
		this.fireItemsAdded(LIST_VALUES, info.begin, info.items);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E2> getComponentItems(ListAddEvent event) {
		return (Iterable<E2>) event.getItems();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E2> getComponentItems(ListReplaceEvent event) {
		return (Iterable<E2>) event.getNewItems();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected ListValueModel<E2> componentLVM(ListEvent event) {
		return (ListValueModel<E2>) event.getSource();
	}

}
