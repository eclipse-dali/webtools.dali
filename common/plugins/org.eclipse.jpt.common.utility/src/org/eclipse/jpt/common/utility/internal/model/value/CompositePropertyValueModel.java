/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.collection.IdentityHashBag;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * A <code>CompositePropertyValueModel</code> adapts a
 * {@link CollectionValueModel} holding other {@link PropertyValueModel}s
 * to a single {@link PropertyValueModel}.
 * <p>
 * Subclasses must implement:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     component values
 * </ul>
 * <strong>NB:</strong> The wrapped collection must not contain any duplicates
 * or this class will throw an exception.
 * 
 * @param <V> the type of the model's value
 * @param <E> the type of the wrapped collection value model's
 * property value model's values
 */
public abstract class CompositePropertyValueModel<V, E>
	extends CollectionPropertyValueModelAdapter<V, PropertyValueModel<? extends E>>
{
	/**
	 * Cache the component property value models so we can stop listening to
	 * them when they are removed from the collection value model.
	 */
	protected final IdentityHashBag<PropertyValueModel<? extends E>> componentPVMs = 
			new IdentityHashBag<PropertyValueModel<? extends E>>();

	/**
	 * Listen to every property value model in the collection value model.
	 * If one changes, we need to re-calculate our value.
	 */
	protected final PropertyChangeListener componentListener;


	// ********** constructors **********

	/**
	 * Construct a property value model that is a composite of the specified
	 * property value models.
	 */
	public CompositePropertyValueModel(PropertyValueModel<? extends E>... collection) {
		this(Arrays.asList(collection));
	}

	/**
	 * Construct a property value model that is a composite of the specified
	 * property value models.
	 */
	public <P extends PropertyValueModel<? extends E>> CompositePropertyValueModel(Collection<? extends P> collection) {
		this(new StaticCollectionValueModel<P>(collection));
	}

	/**
	 * Construct a property value model that is a composite of the specified
	 * property value models.
	 */
	public <P extends PropertyValueModel<? extends E>> CompositePropertyValueModel(CollectionValueModel<P> collectionModel) {
		super(collectionModel);
		this.componentListener = this.buildComponentListener();
	}


	// ********** initialization **********

	protected PropertyChangeListener buildComponentListener() {
		return new ComponentListener();
	}

	protected class ComponentListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			CompositePropertyValueModel.this.componentChanged(event);
		}
	}


	// ********** behavior **********

	/**
	 * Subclasses can override this method if the event can be used to improve
	 * the performance of building a new value (e.g. some property changes may
	 * not necessitate the re-calculation of the value).
	 */
	protected void componentChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {
		this.propertyChanged();
	}


	// ********** CollectionPropertyValueModelAdapter overrides **********

	@Override
	protected void engageModel_() {
		super.engageModel_();
		this.addComponentPVMs(this.collectionModel);
	}

	protected <P extends PropertyValueModel<? extends E>> void addComponentPVMs(Iterable<P> pvms) {
		for (P each : pvms) {
			this.componentPVMs.add(each);
			each.addPropertyChangeListener(VALUE, this.componentListener);
		}
	}

	@Override
	protected void disengageModel_() {
		this.removeComponentPVMs(this.collectionModel);
		super.disengageModel_();
	}

	protected <P extends PropertyValueModel<? extends E>> void removeComponentPVMs(Iterable<P> pvms) {
		for (P each : pvms) {
			each.removePropertyChangeListener(VALUE, this.componentListener);
			this.componentPVMs.remove(each);
		}
	}

	@Override
	protected void itemsAdded(CollectionAddEvent event) {
		this.addComponentPVMs(this.getItems(event));
		super.itemsAdded(event);
	}

	@Override
	protected void itemsRemoved(CollectionRemoveEvent event) {
		this.removeComponentPVMs(this.getItems(event));
		super.itemsRemoved(event);
	}

	@Override
	protected void collectionCleared(CollectionClearEvent event) {
		this.removeAllComponentPVMs();
		super.collectionCleared(event);
	}

	protected void removeAllComponentPVMs() {
		// copy the list so we don't eat our own tail
		ArrayList<PropertyValueModel<? extends E>> copy = new ArrayList<PropertyValueModel<? extends E>>(this.componentPVMs);
		this.removeComponentPVMs(copy);
	}

	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.removeAllComponentPVMs();
		this.addComponentPVMs(this.collectionModel);
		super.collectionChanged(event);
	}


	// ********** convenience methods **********

	/**
	 * Our constructor accepts only a
	 * {@link CollectionValueModel}{@code<? extends }{@link PropertyValueModel}{@code<? extends E>>}.
	 */
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<? extends PropertyValueModel<? extends E>> getItems(CollectionAddEvent event) {
		return (Iterable<? extends PropertyValueModel<? extends E>>) event.getItems();
	}

	/**
	 * Our constructor accepts only a
	 * {@link CollectionValueModel}{@code<? extends }{@link PropertyValueModel}{@code<? extends E>>}.
	 */
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<? extends PropertyValueModel<? extends E>> getItems(CollectionRemoveEvent event) {
		return (Iterable<? extends PropertyValueModel<? extends E>>) event.getItems();
	}
}
