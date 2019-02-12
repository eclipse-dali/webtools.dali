/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.3
 */
public interface PersistenceUnitProperties
	extends Model, TypeRefactoringParticipant
{
	/**
	 * Method used for identifying the given property.
	 */
	boolean itemIsProperty(PersistenceUnit.Property item);

	/**
	 * Returns the property name used for change notification of the given property.
	 */
	String propertyIdOf(PersistenceUnit.Property property);
	
	/**
	 * Return the PersistenceUnit of this Properties.
	 */
	PersistenceUnit getPersistenceUnit();
	
	/**
	 * Return the JPA project the PersistenceUnit belongs to.
	 */
	JpaProject getJpaProject();
	
	/**
	 * A Property with the given name had its value changed
	 */
	void propertyValueChanged(String propertyName, String newValue);
	
	/**
	 * A Property with the given name was removed
	 */
	void propertyRemoved(String propertyName);
}