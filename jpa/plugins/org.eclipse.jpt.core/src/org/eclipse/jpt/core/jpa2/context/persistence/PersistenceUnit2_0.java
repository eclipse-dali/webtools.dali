/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;

/**
 * JPA 2.0 <code>persistence-unit</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceUnit2_0
	extends PersistenceUnit, MetamodelSynchronizer
{
	// ********** shared cache mode **********

	/** 
	 * Return the persistence unit's shared cache mode,
	 * whether specified or defaulted.
	 */
	SharedCacheMode getSharedCacheMode();

	/**
	 * String constant associated with changes to the persistence unit's 
	 * specified shared cache mode
	 */
	String SPECIFIED_SHARED_CACHE_MODE_PROPERTY = "specifiedSharedCacheMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's specified shared cache mode.
	 */
	SharedCacheMode getSpecifiedSharedCacheMode();

	/** 
	 * Set the persistence unit's specified shared cache mode.
	 */
	void setSpecifiedSharedCacheMode(SharedCacheMode sharedCacheMode);

	/**
	 * String constant associated with changes to the persistence unit's 
	 * default shared cache mode (not typically changed).
	 */
	String DEFAULT_SHARED_CACHE_MODE_PROPERTY = "defaultSharedCacheMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's default shared cache mode.
	 */
	SharedCacheMode getDefaultSharedCacheMode();

	boolean calculateDefaultCacheable();

	// ********** validation mode **********

	/** 
	 * Return the persistence unit's validation mode,
	 * whether specified or defaulted.
	 */
	ValidationMode getValidationMode();

	/**
	 * String constant associated with changes to the persistence unit's 
	 * specified validation mode
	 */
	String SPECIFIED_VALIDATION_MODE_PROPERTY = "specifiedValidationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's specified validation mode.
	 */
	ValidationMode getSpecifiedValidationMode();

	/** 
	 * Set the persistence unit's specified validation mode.
	 */
	void setSpecifiedValidationMode(ValidationMode validationMode);

	/**
	 * String constant associated with changes to the persistence unit's 
	 * default validation mode (not typically changed).
	 */
	String DEFAULT_VALIDATION_MODE_PROPERTY = "defaultValidationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's default validation mode.
	 */
	ValidationMode getDefaultValidationMode();
	
	ValidationMode DEFAULT_VALIDATION_MODE = ValidationMode.AUTO;

	// ********** properties **********

	PersistenceUnitProperties getConnection();

	PersistenceUnitProperties getOptions();

	// ********** ORM persistence unit defaults **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * default "delimited identifiers" flag.
	 */
	String DEFAULT_DELIMITED_IDENTIFIERS_PROPERTY = "defaultDelimitedIdentifiers"; //$NON-NLS-1$

	/**
	 * Return the default "delimited identifiers" flag from the first persistence unit defaults
	 * found in the persistence unit's list of mapping files.
	 */
	boolean getDefaultDelimitedIdentifiers();

}
