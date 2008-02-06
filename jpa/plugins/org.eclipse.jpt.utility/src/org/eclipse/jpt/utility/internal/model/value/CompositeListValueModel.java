/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * A <code>CompositeListValueModel</code> wraps another
 * <code>ListValueModel</code> and uses a <code>Transformer</code>
 * to convert each item in the wrapped list to yet another
 * <code>ListValueModel</code>. This composite list contains
 * the combined items from all these component lists.
 * 
 * Terminology:
 * - sources - the items in the wrapped list value model; these
 * 	are converted into components by the transformer
 * - components - the component list value models that are combined
 * 	by this composite list value model
 * - items - the items held by the component list value models
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
	 * Cache of the sources, components, lists.
	 */
	private final ArrayList<Element> elements;
	protected class Element {
		// the item passed to the transformer
		final E1 source;
		// the list value model returned by the transformer
		final ListValueModel<E2> component;
		// the items held by the list value model
		final ArrayList<E2> items;
		// the component's beginning index within the composite
		int begin;
		Element(E1 source, ListValueModel<E2> component, ArrayList<E2> items, int begin) {
			super();
			this.source = source;
			this.component = component;
			this.items = items;
			this.begin = begin;
		}
	}

	/** Listener that listens to all the component list value models. */
	private final ListChangeListener componentListener;

	/** Cache the size of the composite list. */
	private int size;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified wrapped
	 * list value model. Use this constructor if
	 *     - the wrapped list value model already contains other
	 *       list value models or
	 *     - you want to override the <code>transform(E1)</code> method
	 *       instead of building a <code>Transformer</code>
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
		this.elements = new ArrayList<Element>();
		this.componentListener = this.buildComponentListener();
		this.size = 0;
	}

	/**
	 * Construct a list value model with the specified, unchanging, wrapped
	 * list. Use this constructor if
	 *     - the wrapped list already contains list value models or
	 *     - you want to override the <code>transform(E1)</code> method
	 *       instead of building a <code>Transformer</code>
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


	// ********** initialization **********

	protected ListChangeListener buildComponentListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				CompositeListValueModel.this.componentItemsAdded(event);
			}		
			public void itemsRemoved(ListChangeEvent event) {
				CompositeListValueModel.this.componentItemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				CompositeListValueModel.this.componentItemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				CompositeListValueModel.this.componentItemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				CompositeListValueModel.this.componentListCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				CompositeListValueModel.this.componentListChanged(event);
			}
			@Override
			public String toString() {
				return "component listener";
			}
		};
	}


	// ********** ListValueModel implementation **********

	public E2 get(int index) {
		if ((index < 0) || (index >= this.size)) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
		}
		// move backwards through the elements
		for (int i = this.elements.size(); i-- > 0; ) {
			Element element = this.elements.get(i);
			if (index >= element.begin) {
				return element.items.get(index - element.begin);
			}
		}
		throw new IllegalStateException();  // something is whack
	}

	public Iterator<E2> iterator() {
		return this.listIterator();
	}

	public ListIterator<E2> listIterator() {
		return new ReadOnlyCompositeListIterator<E2>(this.buildListsIterators());
	}

	protected ListIterator<ListIterator<E2>> buildListsIterators() {
		return new TransformationListIterator<Element, ListIterator<E2>>(this.elements.listIterator()) {
			@Override
			protected ListIterator<E2> transform(Element element) {
				return element.items.listIterator();
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
		this.addComponentSources(0, this.listHolder.listIterator(), this.listHolder.size());
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// stop listening to the components...
		for (Element element : this.elements) {
			element.component.removeListChangeListener(LIST_VALUES, this.componentListener);
		}
		// ...and clear the cache
		this.elements.clear();
		this.size = 0;
	}

	/**
	 * Some component sources were added; update our cache.
	 */
	@Override
	protected void itemsAdded(ListChangeEvent event) {
		this.addComponentSources(event.index(), this.items(event), event.itemsSize(), true);  // true = fire event
	}

	/**
	 * Do not fire an event.
	 */
	protected void addComponentSources(int addedSourcesIndex, ListIterator<? extends E1> addedSources, int addedSourcesSize) {
		this.addComponentSources(addedSourcesIndex, addedSources, addedSourcesSize, false);  // false = do not fire event
	}

	/**
	 * Add elements corresponding to the specified sources to our cache.
	 * Fire the appropriate event if requested.
	 */
	protected void addComponentSources(int addedSourcesIndex, ListIterator<? extends E1> addedSources, int addedSourcesSize, boolean fireEvent) {
		ArrayList<Element> newElements = new ArrayList<Element>(addedSourcesSize);
		// the 'items' are either tacked on to the end or
		// at the 'begin' index of the first 'element' that is being pushed back
		int newItemsIndex = (addedSourcesIndex == this.elements.size()) ? this.size : this.elements.get(addedSourcesIndex).begin;

		int begin = newItemsIndex;
		while (addedSources.hasNext()) {
			E1 source = addedSources.next();
			ListValueModel<E2> component = this.transform(source);
			component.addListChangeListener(LIST_VALUES, this.componentListener);
			ArrayList<E2> items = new ArrayList<E2>(component.size());
			CollectionTools.addAll(items, component.listIterator());
			newElements.add(new Element(source, component, items, begin));
			begin += items.size();
		}
		this.elements.addAll(addedSourcesIndex, newElements);
		int newItemsSize = begin - newItemsIndex;
		this.size += newItemsSize;

		// bump the 'begin' index for all the elements that were pushed back by the insert
		int movedElementsIndex = addedSourcesIndex + addedSourcesSize;
		for (int i = movedElementsIndex; i < this.elements.size(); i++) {
			this.elements.get(i).begin += newItemsSize;
		}

		if (fireEvent) {
			ArrayList<E2> newItems = new ArrayList<E2>(newItemsSize);
			for (int i = addedSourcesIndex; i < movedElementsIndex; i++) {
				newItems.addAll(this.elements.get(i).items);
			}
			this.fireItemsAdded(LIST_VALUES, newItemsIndex, newItems);
		}
	}

	/**
	 * Some component sources were removed; update our cache.
	 */
	@Override
	protected void itemsRemoved(ListChangeEvent event) {
		this.removeComponentSources(event.index(), event.itemsSize(), true);  // true = fire event
	}

	/**
	 * Do not fire an event.
	 */
	protected void removeComponentSources(int removedSourcesIndex, int removedSourcesSize) {
		this.removeComponentSources(removedSourcesIndex, removedSourcesSize, false);  // false = do not fire event
	}

	/**
	 * Remove the elements corresponding to the specified sources from our cache.
	 */
	protected void removeComponentSources(int removedSourcesIndex, int removedSourcesSize, boolean fireEvent) {
		int removedItemsIndex = this.elements.get(removedSourcesIndex).begin;
		int movedSourcesIndex = removedSourcesIndex + removedSourcesSize;
		int movedItemsIndex = (movedSourcesIndex == this.elements.size()) ? this.size : this.elements.get(movedSourcesIndex).begin;
		int removedItemsSize = movedItemsIndex - removedItemsIndex;
		this.size -= removedItemsSize;

		List<Element> subList = this.elements.subList(removedSourcesIndex, removedSourcesIndex + removedSourcesSize);
		ArrayList<Element> removedElements = new ArrayList<Element>(subList);  // make a copy
		subList.clear();

		// decrement the 'begin' index for all the elements that were moved forward by the deletes
		for (int i = removedSourcesIndex; i < this.elements.size(); i++) {
			this.elements.get(i).begin -= removedItemsSize;
		}

		for (Element removedElement : removedElements) {
			removedElement.component.removeListChangeListener(LIST_VALUES, this.componentListener);
		}

		if (fireEvent) {
			ArrayList<E2> removedItems = new ArrayList<E2>(removedItemsSize);
			for (Element removedElement : removedElements) {
				removedItems.addAll(removedElement.items);
			}
			this.fireItemsRemoved(LIST_VALUES, removedItemsIndex, removedItems);
		}
	}

	/**
	 * Some component sources were replaced; update our cache.
	 */
	@Override
	protected void itemsReplaced(ListChangeEvent event) {
		this.replaceComponentSources(event.index(), this.items(event), event.itemsSize(), true);  // true = fire event
	}

	/**
	 * Replaced component sources will not (typically) map to a set of replaced
	 * items, so we remove and add the corresponding lists of items, resulting in
	 * two events.
	 */
	protected void replaceComponentSources(int replacedSourcesIndex, ListIterator<? extends E1> newSources, int replacedSourcesSize, boolean fireEvent) {
		this.removeComponentSources(replacedSourcesIndex, replacedSourcesSize, fireEvent);
		this.addComponentSources(replacedSourcesIndex, newSources, replacedSourcesSize, fireEvent);
	}

	/**
	 * Some component sources were moved; update our cache.
	 */
	@Override
	protected void itemsMoved(ListChangeEvent event) {
		this.moveComponentSources(event.targetIndex(), event.sourceIndex(), event.moveLength(), true);  // true = fire event
	}

	protected void moveComponentSources(int targetSourcesIndex, int sourceSourcesIndex, int movedSourcesLength, boolean fireEvent) {
		int targetItemsIndex = (targetSourcesIndex == this.elements.size()) ? this.size : this.elements.get(targetSourcesIndex).begin;
		int sourceItemsIndex = this.elements.get(sourceSourcesIndex).begin;
		int moveItemsLength = this.elements.get(sourceSourcesIndex + movedSourcesLength).begin - sourceItemsIndex;

		CollectionTools.move(this.elements, targetSourcesIndex, sourceSourcesIndex, movedSourcesLength);

		// update the 'begin' indexes of all the affected 'elements'
		int min = Math.min(targetSourcesIndex, sourceSourcesIndex);
		int max = Math.max(targetSourcesIndex, sourceSourcesIndex) + movedSourcesLength;
		int begin = Math.min(targetItemsIndex, sourceItemsIndex);
		for (int i = min; i < max; i++) {
			Element element = this.elements.get(i);
			element.begin = begin;
			begin += element.component.size();
		}

		if (fireEvent) {
			this.fireItemsMoved(LIST_VALUES, targetItemsIndex, sourceItemsIndex, moveItemsLength);
		}
	}

	/**
	 * The component sources were cleared; clear our cache.
	 */
	@Override
	protected void listCleared(ListChangeEvent event) {
		this.removeComponentSources(0, this.elements.size());
		this.fireListCleared(LIST_VALUES);
	}

	/**
	 * The component sources changed; rebuild our cache.
	 */
	@Override
	protected void listChanged(ListChangeEvent event) {
		this.removeComponentSources(0, this.elements.size());
		this.addComponentSources(0, this.listHolder.listIterator(), this.listHolder.size());
		this.fireListChanged(LIST_VALUES);
	}


	// ********** internal methods **********

	/**
	 * Transform the specified object into a list value model.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Transformer</code>.
	 */
	protected ListValueModel<E2> transform(E1 value) {
		return this.transformer.transform(value);
	}

	/**
	 * Return the index of the specified component list value model.
	 */
	protected int indexOf(ListValueModel<E2> component) {
		for (int i = 0; i < this.elements.size(); i++) {
			if (this.elements.get(i).component == component) {
				return i;
			}
		}
		throw new IllegalArgumentException("invalid component: " + component);
	}

	/**
	 * Return the index of the specified event's component list value model.
	 */
	protected int indexFor(ListChangeEvent event) {
		return this.indexOf(this.componentLVM(event));
	}

	/**
	 * Items were added to one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsAdded(ListChangeEvent event) {
		// update the affected 'begin' indices
		int componentIndex = this.indexFor(event);
		int newItemsSize = event.itemsSize();
		for (int i = componentIndex + 1; i < this.elements.size(); i++) {
			this.elements.get(i).begin += newItemsSize;
		}
		this.size += newItemsSize;

		// synchronize the cached list
		Element element = this.elements.get(componentIndex);
		CollectionTools.addAll(element.items, event.index(), this.componentItems(event), event.itemsSize());

		// translate the event
		this.fireItemsAdded(event.cloneWithSource(this, LIST_VALUES, element.begin));
	}

	/**
	 * Items were removed from one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsRemoved(ListChangeEvent event) {
		// update the affected 'begin' indices
		int componentIndex = this.indexFor(event);
		int removedItemsSize = event.itemsSize();
		for (int i = componentIndex + 1; i < this.elements.size(); i++) {
			this.elements.get(i).begin -= removedItemsSize;
		}
		this.size -= removedItemsSize;

		// synchronize the cached list
		Element element = this.elements.get(componentIndex);
		int itemIndex = event.index();
		element.items.subList(itemIndex, itemIndex + event.itemsSize()).clear();

		// translate the event
		this.fireItemsRemoved(event.cloneWithSource(this, LIST_VALUES, element.begin));
	}

	/**
	 * Items were replaced in one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsReplaced(ListChangeEvent event) {
		// no changes to the 'begin' indices or size

		// synchronize the cached list
		int componentIndex = this.indexFor(event);
		Element element = this.elements.get(componentIndex);
		int i = event.index();
		for (Iterator<E2> stream = this.componentItems(event); stream.hasNext(); ) {
			element.items.set(i++, stream.next());
		}

		// translate the event
		this.fireItemsReplaced(event.cloneWithSource(this, LIST_VALUES, element.begin));
	}

	/**
	 * Items were moved in one of the component lists;
	 * synchronize our cache.
	 */
	protected void componentItemsMoved(ListChangeEvent event) {
		// no changes to the 'begin' indices or size

		// synchronize the cached list
		int componentIndex = this.indexFor(event);
		Element element = this.elements.get(componentIndex);
		CollectionTools.move(element.items, event.targetIndex(), event.sourceIndex(), event.itemsSize());

		// translate the event
		this.fireItemsMoved(event.cloneWithSource(this, LIST_VALUES, element.begin));
	}

	/**
	 * One of the component lists was cleared;
	 * synchronize our cache.
	 */
	protected void componentListCleared(ListChangeEvent event) {
		int componentIndex = this.indexFor(event);
		this.clearComponentList(componentIndex, this.elements.get(componentIndex));
	}

	protected void clearComponentList(int componentIndex, Element element) {
		// update the affected 'begin' indices
		int removedItemsSize = element.items.size();
		for (int i = componentIndex + 1; i < this.elements.size(); i++) {
			this.elements.get(i).begin -= removedItemsSize;
		}
		this.size -= removedItemsSize;

		// synchronize the cached list
		ArrayList<E2> items = new ArrayList<E2>(element.items);
		element.items.clear();

		// translate the event
		this.fireItemsRemoved(LIST_VALUES, element.begin, items);
	}

	/**
	 * One of the component lists changed;
	 * synchronize our cache by clearing out the appropriate
	 * list and rebuilding it.
	 */
	protected void componentListChanged(ListChangeEvent event) {
		int componentIndex = this.indexFor(event);
		Element element = this.elements.get(componentIndex);
		this.clearComponentList(componentIndex, element);

		// update the affected 'begin' indices
		int newItemsSize = element.component.size();
		for (int i = componentIndex + 1; i < this.elements.size(); i++) {
			this.elements.get(i).begin += newItemsSize;
		}
		this.size += newItemsSize;

		// synchronize the cached list
		CollectionTools.addAll(element.items, element.component.listIterator(), newItemsSize);

		// translate the event
		this.fireItemsAdded(LIST_VALUES, 0, element.items);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected ListIterator<E2> componentItems(ListChangeEvent event) {
		return (ListIterator<E2>) event.items();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected ListValueModel<E2> componentLVM(ListChangeEvent event) {
		return (ListValueModel<E2>) event.getSource();
	}

}
