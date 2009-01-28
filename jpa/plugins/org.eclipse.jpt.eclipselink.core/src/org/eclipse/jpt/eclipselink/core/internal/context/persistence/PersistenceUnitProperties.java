/*******************************************************************************
* Copyright (c) 2007, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 * PersistenceUnitProperties
 */
public interface PersistenceUnitProperties extends Model, PropertyChangeListener
{
	/**
	 * Method used for identifying the given property.
	 */
	boolean itemIsProperty(PersistenceUnit.Property item);

	/**
	 * Returns the property name used for change notification of the given property.
	 */
	String propertyIdFor(PersistenceUnit.Property property);
	
	/**
	 * Return the PersistenceUnit of this Properties.
	 */
	PersistenceUnit getPersistenceUnit();
	
	/**
	 * Return the JPA project the PersistenceUnit belongs to.
	 */
	JpaProject getJpaProject();
}