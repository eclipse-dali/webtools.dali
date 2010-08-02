/*******************************************************************************
* Copyright (c) 2007, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.text.edits.ReplaceEdit;

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
public interface PersistenceUnitProperties extends Model
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


	// ************ refactoring **************

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createReplaceTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for renaming any reference to the originalPackage newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createReplacePackageEdits(IPackageFragment originalPackage, String newName);

}