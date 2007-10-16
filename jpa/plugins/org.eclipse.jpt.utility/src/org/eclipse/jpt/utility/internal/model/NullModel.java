/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.io.Serializable;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

/**
 * Null implementation that never notifies any listeners of any changes
 * because it never changes.
 */
public class NullModel
	implements Model, Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;


	/**
	 * Default constructor.
	 */
	public NullModel() {
		super();
	}


	// ********** Model implementation **********

	public void addStateChangeListener(StateChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeStateChangeListener(StateChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addCollectionChangeListener(CollectionChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addListChangeListener(ListChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addListChangeListener(String listName, ListChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeListChangeListener(ListChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeListChangeListener(String listName, ListChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addTreeChangeListener(TreeChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeTreeChangeListener(TreeChangeListener listener) {
		// ignore listeners - nothing ever changes
	}

	public void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		// ignore listeners - nothing ever changes
	}


	// ********** Object overrides **********

	@Override
	public synchronized NullModel clone() {
		try {
			return (NullModel) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return ClassTools.shortClassNameForObject(this);
	}

}
