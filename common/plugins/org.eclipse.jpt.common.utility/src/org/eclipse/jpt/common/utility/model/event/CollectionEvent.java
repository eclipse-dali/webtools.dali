/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.event;

import org.eclipse.jpt.common.utility.model.Model;
// TODO add "item/original/nested event" for item changed?
/**
 * A "collection" event gets delivered whenever a model changes a "bound"
 * or "constrained" collection. A <code>CollectionEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener}.
 * The intent is that any listener
 * can keep itself synchronized with the model's collection via the collection
 * events it receives and need not maintain a reference to the original
 * collection.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class CollectionEvent extends ChangeEvent {

	/** Name of the collection that changed. */
	final String collectionName;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a new collection event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 */
	public CollectionEvent(Model source, String collectionName) {
		super(source);
		if (collectionName == null) {
			throw new NullPointerException();
		}
		this.collectionName = collectionName;
	}

	/**
	 * Return the programmatic name of the collection that was changed.
	 */
	public String getCollectionName() {
		return this.collectionName;
	}

	@Override
	protected void toString(StringBuilder sb) {
		sb.append(this.collectionName);
	}

}
