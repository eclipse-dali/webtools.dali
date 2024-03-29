/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * Extend {@link ItemAspectListValueModelAdapter} to listen to one or more
 * properties of each item in the wrapped list model.
 */
public class ItemPropertyListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the items' properties that we listen to. */
	protected final String[] propertyNames;

	/** Listener that listens to all the items in the list. */
	protected final PropertyChangeListener itemPropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel<E> listHolder, String... propertyNames) {
		super(listHolder);
		if (propertyNames == null) {
			throw new NullPointerException();
		}
		this.propertyNames = propertyNames;
		this.itemPropertyListener = this.buildItemPropertyListener();
	}

	/**
	 * Construct an adapter for the specified item properties.
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... propertyNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), propertyNames);
	}


	// ********** initialization **********

	protected PropertyChangeListener buildItemPropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				ItemPropertyListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item property listener: " + Arrays.asList(ItemPropertyListValueModelAdapter.this.propertyNames); //$NON-NLS-1$
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void engageItem_(Model item) {
		for (String propertyName : this.propertyNames) {
			item.addPropertyChangeListener(propertyName, this.itemPropertyListener);
		}
	}

	@Override
	protected void disengageItem_(Model item) {
		for (String propertyName : this.propertyNames) {
			item.removePropertyChangeListener(propertyName, this.itemPropertyListener);
		}
	}

	/**
	 * bug 342171
	 * Override to just fire an itemReplaced event instead of a listChanged event.
	 * An aspect of the item has changed, so no reason to say that the entire list has changed.
	 * Added a LocalChangeSupport so that I can fire a ListReplacedEvent for an old list
	 * and new list containing the same item.
	 */
	@Override
	protected void itemAspectChanged(EventObject event) {
		Object item = event.getSource();
		this.changeSupport.fireItemsReplaced(new ListReplaceEvent(this, LIST_VALUES, IterableTools.indexOf(this.listModel, item), item, item));
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new LocalChangeSupport(this, ListChangeListener.class, ListValueModel.LIST_VALUES);
	}

	/* CU private */ class LocalChangeSupport
		extends SingleAspectChangeSupport
	{
		// TODO remove
		LocalChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName) {
			this(source, validListenerClass, validAspectName, DefaultExceptionHandler.instance());
		}
		LocalChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName, ExceptionHandler exceptionHandler) {
			super(source, validListenerClass, validAspectName, exceptionHandler);
		}
		@Override
		public boolean fireItemsReplaced(ListReplaceEvent event) {
			this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
			if (event.getItemsSize() != 0) {
				this.fireItemsReplaced_(event);
				return true;
			}
			return false;
		}
	}
}
