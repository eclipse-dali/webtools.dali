/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
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
	 * bug 310720
	 * Override to just fire an itemReplaced event instead of a listChanged event.
	 * An aspect of the item as changed, so no reason to say that the entire list has changed.
	 * Added a LocalChangeSupport so that I can fire a ListReplacedEvent for an old list
	 * and new list containing the same item.
	 */
	@Override
	protected void itemAspectChanged(EventObject event) {
		Object item = event.getSource();
		this.getChangeSupport().fireItemsReplaced(
			new ListReplaceEvent(this, LIST_VALUES, CollectionTools.indexOf(this.listHolder, item), item, item));
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new LocalChangeSupport(this, ListChangeListener.class, ListValueModel.LIST_VALUES);
	}

	private class LocalChangeSupport extends SingleAspectChangeSupport {
		public LocalChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName) {
			super(source, validListenerClass, validAspectName);
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
