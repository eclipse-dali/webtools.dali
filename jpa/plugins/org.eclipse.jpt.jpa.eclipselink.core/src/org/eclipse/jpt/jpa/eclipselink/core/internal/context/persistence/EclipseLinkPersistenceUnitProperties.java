/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.Set;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnitProperties;

/**
 * EclipseLinkPersistenceUnitProperties
 * 
 * Listens to the propertyListAdapter
 */
public abstract class EclipseLinkPersistenceUnitProperties extends AbstractPersistenceUnitProperties
{
	// ********** constructors **********
	protected EclipseLinkPersistenceUnitProperties(PersistenceUnit parent) {
		super(parent);
	}

	// ******** Convenience methods ********
	
	protected Set<PersistenceUnit.Property> getPropertiesSetWithPrefix(String keyPrefix) {
		return CollectionTools.hashSet(this.getPersistenceUnit().getPropertiesWithNamePrefix(keyPrefix));
	}

	/**
	 * Extracts the entityName of the specified property name. If the property name
	 * has no suffix, return an empty string.
	 */
	protected String extractEntityNameOf(PersistenceUnit.Property property) {
		return extractEntityNameOf(property.getName());
	}

	/**
	 * Extracts the entityName of the specified string. If the string
	 * has no suffix, return an empty string.
	 */
	protected String extractEntityNameOf(String propertyName) {
		int index = propertyName.lastIndexOf('.');
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		return propertyName.substring(index + 1);
	}

}
