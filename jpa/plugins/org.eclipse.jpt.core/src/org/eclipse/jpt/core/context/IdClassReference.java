/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface IdClassReference
	extends JpaNode, PersistentType.Owner
{
	/**
	 * Property string associated with changes to the {@link IdClassHolder}'s specified id class name
	 */
	String SPECIFIED_ID_CLASS_NAME_PROPERTY = "specifiedIdClassName"; //$NON-NLS-1$
	
	/**
	 * Return the specified name of the id class, null if none is specified in the resource model
	 */
	String getSpecifiedIdClassName();
	
	/**
	 * Set the specified name of the id class.
	 * Use null to remove the id class specification from the resource model
	 */
	void setSpecifiedIdClassName(String value);
	
	/**
	 * Property string associated with changes to the {@link IdClassHolder}'s default id class name
	 */
	String DEFAULT_ID_CLASS_NAME_PROPERTY = "defaultIdClassName"; //$NON-NLS-1$
	
	/**
	 * Return the default name of the id class, null if there is none
	 */
	String getDefaultIdClassName();
	
	/**
	 * Return the name of the id class, taking into consideration the default value if applicable
	 */
	String getIdClassName();
	
	/**
	 * Return whether the id class has been specified.
	 * Generally, this simply means that the id class name has been set, although if a default
	 * applies, this should also return true.
	 */
	boolean isSpecified();
	
	/**
	 * Property string associated with changes to the {@link IdClassHolder}'s id class.
	 * This will change (potentially) if the id class name changes, or if other changes result
	 * in changes in the id class' resolution.
	 */
	String ID_CLASS_PROPERTY = "idClass"; //$NON-NLS-1$
	
	/**
	 * Return the {@link JavaPersistentType} that is resolved from the id class name.
	 * This will be null if the id class name is null or if the class cannot be resolved from that 
	 * name.
	 */
	JavaPersistentType getIdClass();
	
	/**
	 * Return the char to be used for browsing or creating the id class IType.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getIdClassEnclosingTypeSeparator();
}
