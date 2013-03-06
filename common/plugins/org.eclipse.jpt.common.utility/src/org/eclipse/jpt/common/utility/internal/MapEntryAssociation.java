/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.util.Map;

/**
 * Adapt a {@link java.util.Map.Entry map entry} to the
 * {@link org.eclipse.jpt.common.utility.Association} interface.
 */
public class MapEntryAssociation<K, V>
	extends AbstractAssociation<K, V>
	implements Cloneable
{
	private final Map.Entry<K, V> mapEntry;


	/**
	 * Construct an association that gets its key and value from the specified
	 * {@link java.util.Map.Entry map entry}.
	 */
	public MapEntryAssociation(Map.Entry<K, V> mapEntry) {
		super();
		this.mapEntry = mapEntry;
	}

	public K getKey() {
		return this.mapEntry.getKey();
	}

	public synchronized V getValue() {
		return this.mapEntry.getValue();
	}

	public synchronized V setValue(V value) {
		return this.mapEntry.setValue(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized MapEntryAssociation<K, V> clone() {
		try {
			return (MapEntryAssociation<K, V>) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}
}
