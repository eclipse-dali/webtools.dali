/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jpt.utility.internal.IdentityHashBag;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

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
 * <b>NB:</b> The wrapped collection must not contain any duplicates
 * or this class will throw an exception.
 * <p>
 */
public abstract class CompositePropertyValueModel<V>
	extends CollectionPropertyValueModelAdapter<V>
{
	/**
	 * Cache the component property value models so we can stop listening to
	 * them when they are removed from the collection value model.
	 */
	protected final IdentityHashBag<PropertyValueModel<?>> componentPVMs = 
			new IdentityHashBag<PropertyValueModel<?>>();

	/**
	 * Listen to every property value model in the collection value model.
	 * If one changes, we need to re-calculate our value.
	 */
	protected final PropertyChangeListener propertyChangeListener;


	// ********** constructors **********

	/**
	 * Construct a property value model that is a composite of the specified
	 * property valuve models.
	 */
	public CompositePropertyValueModel(PropertyValueModel<?>... collection) {
		this(Arrays.asList(collection));
	}

	/**
	 * Construct a property value model that is a composite of the specified
	 * property valuve models.
	 */
	public <E extends PropertyValueModel<?>> CompositePropertyValueModel(Collection<E> collection) {
		this(new StaticCollectionValueModel<E>(collection));
	}

	/**
	 * Construct a property value model that is a composite of the specified
	 * property valuve models.
	 */
	public CompositePropertyValueModel(CollectionValueModel<? extends PropertyValueModel<?>> collectionHolder) {
		super(collectionHolder);
		this.propertyChangeListener = this.buildPropertyChangeListener();
	}


	// ********** initialization **********

	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				CompositePropertyValueModel.this.propertyChanged(event);
			}
			@Override
			public String toString() {
				return "property change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	/**
	 * Subclasses can override this method if the event can be used to improve
	 * the performance of building a new value (e.g. some property changes may
	 * not necessitate the re-calculation of the value).
	 */
	protected void propertyChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {
		this.propertyChanged();
	}


	// ********** CollectionPropertyValueModelAdapter overrides **********

	@Override
	protected void engageModel_() {
		super.engageModel_();
		this.addComponentPVMs(this.getCollectionHolder());
	}

	protected <E extends PropertyValueModel<?>> void addComponentPVMs(Iterable<E> pvms) {
		for (PropertyValueModel<?> each : pvms) {
			this.componentPVMs.add(each);
			each.addPropertyChangeListener(VALUE, this.propertyChangeListener);
		}
	}

	@Override
	protected void disengageModel_() {
		this.removeComponentPVMs(this.getCollectionHolder());
		super.disengageModel_();
	}

	protected <E extends PropertyValueModel<?>> void removeComponentPVMs(Iterable<E> pvms) {
		for (PropertyValueModel<?> each : pvms) {
			each.removePropertyChangeListener(VALUE, this.propertyChangeListener);
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
		ArrayList<PropertyValueModel<?>> copy = new ArrayList<PropertyValueModel<?>>(this.componentPVMs);
		this.removeComponentPVMs(copy);
	}

	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.removeAllComponentPVMs();
		this.addComponentPVMs(this.getCollectionHolder());
		super.collectionChanged(event);
	}


	// ********** convenience methods **********

	/**
	 * Our constructor accepts only a {@link CollectionValueModel}{@code<? extends }{@link PropertyValueModel}{@code<?>>}.
	 */
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected CollectionValueModel<? extends PropertyValueModel<?>> getCollectionHolder() {
		return (CollectionValueModel<? extends PropertyValueModel<?>>) this.collectionHolder;
	}

	/**
	 * Our constructor accepts only a {@link CollectionValueModel}{@code<? extends }{@link PropertyValueModel}{@code<?>>}.
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<? extends PropertyValueModel<?>> getItems(CollectionAddEvent event) {
		return (Iterable<? extends PropertyValueModel<?>>) event.getItems();
	}

	/**
	 * Our constructor accepts only a {@link CollectionValueModel}{@code<? extends }{@link PropertyValueModel}{@code<?>>}.
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<? extends PropertyValueModel<?>> getItems(CollectionRemoveEvent event) {
		return (Iterable<? extends PropertyValueModel<?>>) event.getItems();
	}

}
