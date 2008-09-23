/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import java.util.Iterator;

import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;

/**
 * PersistenceUnitPropertyListListener Notifies the given parent when a change
 * occured in JPT PersistenceUnit properties
 */
public class PersistenceUnitPropertyListListener implements ListChangeListener
{
	private PersistenceUnitProperties parent;

	// ********** constructors / initialization **********
	public PersistenceUnitPropertyListListener(PersistenceUnitProperties parent) {
		this.parent = parent;
	}

	// ********** Behavior **********
	protected boolean add(Property newItem, Model source) {
		if (this.model().itemIsProperty(newItem)) {
			String propertyName = this.model().propertyIdFor(newItem);
			this.model().propertyChanged(
				new PropertyChangeEvent(source, propertyName, null, newItem));
			return true;
		}
		return false;
	}

	protected boolean remove(Property item, Model source) {
		if (this.model().itemIsProperty(item)) {
			String propertyName = this.model().propertyIdFor(item);
			this.model().propertyChanged(
				new PropertyChangeEvent(source, propertyName, item, null)); // oldItem is the removed property
			return true;
		}
		return false;
	}

	// replace
	protected Property set(Property newItem, Model source) {
		if (this.model().itemIsProperty(newItem)) {
			String propertyName = this.model().propertyIdFor(newItem);
			this.model().propertyChanged(
				new PropertyChangeEvent(source, propertyName, null, newItem)); // oldItem unknown
			return newItem;
		}
		return null;
	}

	// ********** ListChangeListener implementation **********
	public void itemsAdded(ListChangeEvent e) {
		for (Iterator<Property> stream = this.items(e); stream.hasNext();) {
			this.add(stream.next(), e.getSource());
		}
	}

	public void itemsRemoved(ListChangeEvent e) {
		for (Iterator<Property> stream = this.items(e); stream.hasNext();) {
			this.remove(stream.next(), e.getSource());
		}
	}

	public void itemsReplaced(ListChangeEvent e) {
		// ItemAspectListValueModelAdapter(270) does not provide old value
		for (Iterator<Property> newStream = this.items(e); newStream.hasNext();) {
			this.set(newStream.next(), e.getSource());
		}
	}

	public void itemsMoved(ListChangeEvent e) {
		throw new UnsupportedOperationException("source: " + e.getSource() + " - aspect: " + e.getAspectName());
	}

	public void listCleared(ListChangeEvent e) {
		throw new UnsupportedOperationException("source: " + e.getSource() + " - aspect: " + e.getAspectName());
	}

	public void listChanged(ListChangeEvent e) {
		throw new UnsupportedOperationException("source: " + e.getSource() + " - aspect: " + e.getAspectName());
	}

	// ********** standard methods **********
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "( " + this.model().getClass().getSimpleName() + ")";
	}

	// ********** internal methods **********
	private PersistenceUnitProperties model() {
		return this.parent;
	}

	@SuppressWarnings("unchecked")
	private Iterator<Property> items(ListChangeEvent event) {
		return (Iterator<Property>) event.items();
	}
}
